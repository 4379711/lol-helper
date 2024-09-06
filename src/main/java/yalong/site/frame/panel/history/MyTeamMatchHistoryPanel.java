package yalong.site.frame.panel.history;

import cn.hutool.core.util.NumberUtil;
import com.sun.jna.platform.win32.WinDef;
import yalong.site.bo.SpgParticipants;
import yalong.site.bo.SpgProductsMatchHistoryBO;
import yalong.site.bo.TeamSummonerBO;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.GameDataCache;
import yalong.site.enums.ImageEnum;
import yalong.site.frame.bo.ChampionWin;
import yalong.site.frame.constant.ColorConstant;
import yalong.site.frame.panel.base.HorizontalDivider;
import yalong.site.frame.panel.base.RoundedVerticalPanel;
import yalong.site.frame.utils.MatchHistoryUtil;
import yalong.site.utils.Win32Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
public class MyTeamMatchHistoryPanel extends JWindow implements MouseListener, MouseMotionListener {
    private Point dragOffset; // 拖拽偏移量

    public MyTeamMatchHistoryPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setAlwaysOnTop(true);
        this.getContentPane().setBackground(ColorConstant.DARK_THREE);
        WinDef.RECT lolWindows = Win32Util.findWindowsLocation(null, "League of Legends");
        int height = 550;
        int width = 200;
        Dimension size = new Dimension(width, height);
        this.setSize(size);
        int x = lolWindows.left - width;
        int y = lolWindows.top;
        Point p = new Point(x, y);
        this.setLocation(p);
        // 创建一个圆角矩形形状
        Shape roundRect = new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 15, 15);
        this.setShape(roundRect);
        // 设置布局管理器为 BoxLayout（垂直排列）
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel jPanel = new RoundedVerticalPanel(10, ColorConstant.DARK_THREE);
        for (TeamSummonerBO data : GameDataCache.myTeamMatchHistory) {
            jPanel.add(new HorizontalDivider(this.getWidth() - 10));
            MyTeamMatchHistoryLine builder = MyTeamMatchHistoryLine.builder(buildTeamLineData(data), this.getWidth());
            jPanel.add(builder);
        }
        this.add(jPanel);
        this.setVisible(true);
    }

    public static void start() {
        FrameInnerCache.myTeamMatchHistoryPanel = new MyTeamMatchHistoryPanel();
    }

    private TeamSummonerBO buildTeamLineData(TeamSummonerBO data) {
        List<SpgParticipants> spgParticipantsList = new ArrayList<>();
        //获取自己的所有数据
        for (SpgProductsMatchHistoryBO item : data.getMatchHistory()) {
            Integer queueId = item.getJson().getQueueId();
            if (GameDataCache.allGameQueuesList.get(queueId).getIsVisible().equals("true") &&
                    GameDataCache.selectGameQueueList.get(queueId).isSelect()) {
                SpgParticipants spgParticipants = item.getJson().getParticipants()
                        .stream()
                        .filter(line -> line.getPuuid().equals(data.getPuuid()))
                        .findFirst()
                        .get();
                spgParticipantsList.add(spgParticipants);
            }
        }
        //计算平均数据
        Map<Integer, ChampionWin> winChampions = new HashMap<>();
        int win = 0;
        int kill = 0;
        int death = 0;
        int count = 0;

        for (SpgParticipants spgParticipants : spgParticipantsList) {
            count++;
            //获取总胜场
            if (spgParticipants.isWin()) {
                win++;
                //获取英雄胜场
                if (winChampions.containsKey(spgParticipants.getChampionId())) {
                    ChampionWin championWin = winChampions.get(spgParticipants.getChampionId());
                    championWin.setWins(championWin.getWins() + 1);
                } else {
                    ChampionWin championWin = new ChampionWin();
                    championWin.setChampionId(spgParticipants.getChampionId());
                    championWin.setWins(1);
                    winChampions.put(spgParticipants.getChampionId(), championWin);
                }
            } else {
                if (winChampions.containsKey(spgParticipants.getChampionId())) {
                    ChampionWin championWin = winChampions.get(spgParticipants.getChampionId());
                    championWin.setWins(championWin.getFails() + 1);
                } else {
                    ChampionWin championWin = new ChampionWin();
                    championWin.setChampionId(spgParticipants.getChampionId());
                    championWin.setFails(1);
                    winChampions.put(spgParticipants.getChampionId(), championWin);
                }
            }
            kill += spgParticipants.getKills();
            death += spgParticipants.getDeaths();
        }

        // 将 Map 转换为 List，并按 wins + fails 的总和从大到小排序
        List<Map.Entry<Integer, ChampionWin>> entryList = new ArrayList<>(winChampions.entrySet());
        entryList.sort((e1, e2) -> {
            int total1 = e1.getValue().getWins() + e1.getValue().getFails();
            int total2 = e2.getValue().getWins() + e2.getValue().getFails();
            return Integer.compare(total2, total1); // 按总数从大到小排序
        });

        // 创建一个数组集合来存储排序结果
        List<ChampionWin> sortedList = new ArrayList<>();
        for (Map.Entry<Integer, ChampionWin> entry : entryList) {
            ChampionWin value = entry.getValue();
            int sum = value.getWins() + value.getFails();
            if (value.getWins() != 0) {
                String championRate = NumberUtil.decimalFormat("#.##%", NumberUtil.div(value.getWins(), sum));
                value.setWinRate(championRate);
            } else {
                value.setWinRate("0.00%");
            }
            value.setIcon(MatchHistoryUtil.getChampionIcon(value.getChampionId(), 40, 40, ImageEnum.SQUARE));
            sortedList.add(value);
            if (sortedList.size() >= 5) {
                break;
            }
        }
        data.setChampionWinList(sortedList);
        data.setProfileIcon(MatchHistoryUtil.getSummonerIcon(data.getProfileIconId(), 50, 50));
        if (win == 0) {
            data.setWinRate("0%");
        } else {
            data.setWinRate(NumberUtil.decimalFormat("#.##%", NumberUtil.div(win, count, 2)));
        }
        return data;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragOffset = new Point(e.getX(), e.getY());
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = getLocation().x + e.getX() - dragOffset.x;
        int newY = getLocation().y + e.getY() - dragOffset.y;
        setLocation(newX, newY);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
