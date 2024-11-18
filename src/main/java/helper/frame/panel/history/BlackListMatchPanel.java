package helper.frame.panel.history;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import helper.bo.BlackListBO;
import helper.bo.BlackListPlayer;
import helper.bo.SpgGames;
import helper.bo.SpgParticipants;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.enums.ImageEnum;
import helper.frame.constant.ColorConstant;
import helper.frame.constant.GameConstant;
import helper.frame.utils.FrameTipUtil;
import helper.frame.utils.MatchHistoryUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 战绩查询的容器需要一条一条生成数据
 *
 * @author WuYi
 */
@Slf4j
public class BlackListMatchPanel extends JPanel {
    /**
     * 翻页容器
     */
    private final JPanel indexPanel = new JPanel();
    private final Label labelIndex = new Label();
    private final JButton buttonPrevious = new JButton();
    private final JButton buttonNext = new JButton();
    public BlackListHistoryDetail blackListHistoryDetail = null;
    /**
     * 是否打开详情页
     */
    public boolean isShowDetail = false;
    /**
     * 战绩数据容器
     */
    public JPanel panelContainer = new JPanel();
    private int index = 1;


    public BlackListMatchPanel(boolean pageButtonVisible) {
        FrameInnerCache.blackListMatchPanel = this;
        this.setName("黑名单查询");
        this.setBackground(ColorConstant.DARK_THREE);
        this.setBorder(null);
        this.setAutoscrolls(true);
        this.setMaximumSize(new Dimension(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT));
        this.panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        this.indexPanel.setLayout(new BoxLayout(indexPanel, BoxLayout.X_AXIS));
        indexPanel.setBackground(ColorConstant.DARK_THREE);
        labelIndex.setForeground(Color.WHITE);
        if (pageButtonVisible) {
            this.buttonPrevious.setText("上一页");
            this.labelIndex.setText(Integer.toString(index));
            this.buttonNext.setText("下一页");
            List<String> blacklistFiles = FileUtil.listFileNames(new File(GameConstant.BLACK_LIST_FILE).getAbsolutePath());
            //上一页鼠标事件
            this.buttonPrevious.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (index > 1) {
                        index--;
                        List<String> page = ListUtil.page((index - 1) * FrameSetting.PAGE_SIZE, index * FrameSetting.PAGE_SIZE, blacklistFiles);
                        if (!page.isEmpty()) {
                            List<BlackListBO> blackLists = new ArrayList<BlackListBO>();
                            for (String s : page) {
                                String jsonString = FileUtil.readUtf8String(GameConstant.BLACK_LIST_FILE + s);
                                BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
                                blackLists.add(blackListBO);
                            }
                            setData(blackLists);
                        } else {
                            index++;
                        }

                    }
                }
            });
            //下一页鼠标事件
            this.buttonNext.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (FrameSetting.PAGE_SIZE * index < FrameSetting.PAGE_MAX) {
                        index++;
                        List<String> page = ListUtil.page((index - 1) * FrameSetting.PAGE_SIZE, index * FrameSetting.PAGE_SIZE, blacklistFiles);
                        if (!page.isEmpty()) {
                            List<BlackListBO> blackLists = new ArrayList<BlackListBO>();
                            for (String s : page) {
                                String jsonString = FileUtil.readUtf8String(new File(GameConstant.BLACK_LIST_FILE + s));
                                BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
                                blackLists.add(blackListBO);
                            }
                            setData(blackLists);
                        } else {
                            index--;
                        }

                    }
                }
            });
            this.indexPanel.add(buttonPrevious);
            this.indexPanel.add(labelIndex);
            this.indexPanel.add(buttonNext);
            this.resetIndex();
        }
        this.showAllComponent();

    }

    public static BlackListMatchPanel builder(boolean pageButtonVisible) {
        return new BlackListMatchPanel(pageButtonVisible);
    }

    /**
     * 设置页数
     */
    public void resetIndex() {
        this.index = 1;
    }

    /**
     * 设置战绩的数据
     *
     * @param data 战绩
     */
    public void setData(List<BlackListBO> data) {
        this.panelContainer.removeAll();
        //设置右边战绩
        if (data != null && !data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                BlackListHistoryLine linePanel = new BlackListHistoryLine();
                String mePuuid = data.get(i).getMePuuid();
                linePanel.setLayout((new BoxLayout(linePanel, BoxLayout.X_AXIS)));
                //获取游戏的对局信息
                SpgGames record = data.get(i).getSpgGames();
                Map<Integer, List<SpgParticipants>> collect = record.getParticipants().stream().collect(Collectors.groupingBy(SpgParticipants::getTeamId));
                List<SpgParticipants> teamOne = collect.get(GameConstant.TEAM_ONE);
                List<SpgParticipants> teamTwo = collect.get(GameConstant.TEAM_TWO);
                SpgParticipants selfData = record.getParticipants().stream().filter(item -> item.getPuuid().equals(mePuuid)).findFirst().get();
                boolean isWin = selfData.isWin();
                Color bgColor = MatchHistoryUtil.getBackGroundColor(isWin);
                linePanel.setBackground(MatchHistoryUtil.getBackGroundColor(isWin));
                linePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
                linePanel.add(buildLineOne(record, selfData, isWin));
                linePanel.add(buildLineTwo(selfData));
                linePanel.add(Box.createRigidArea(new Dimension(15, 0)));
                linePanel.add(buildLineThree(selfData));
                linePanel.add(Box.createRigidArea(new Dimension(15, 0)));
                linePanel.add(buildLineFour(selfData));
                linePanel.add(Box.createRigidArea(new Dimension(15, 0)));
                linePanel.add(buildLineFive(selfData, bgColor));
                linePanel.add(Box.createRigidArea(new Dimension(15, 0)));
                linePanel.add(buildLineSix(teamOne, teamTwo, bgColor));
                linePanel.addMouseListener(new BlackListSelectDetailListener(data.get(i)));
                for (Component component : linePanel.getComponents()) {
                    setBgColor(component, bgColor);
                }
                panelContainer.add(linePanel);
            }
            this.labelIndex.setText(Integer.toString(index));
            //this.add(searchPanel);
            panelContainer.add(this.indexPanel);
            panelContainer.revalidate();
            panelContainer.repaint();
            this.add(panelContainer);
        } else {
            FrameTipUtil.errorOccur("请到战绩查询点开详情后召唤师姓名右键 羁押新犯人");
        }
    }

    private JPanel buildLineOne(SpgGames record, SpgParticipants selfData, boolean isWin) {
        JLabel queueLabel = new JLabel("<html><body>" + GameDataCache.allGameQueuesList.get(record.getQueueId()).getName() + "<hr>" + DateUtil.format(DateUtil.date(record.getGameCreation()), "MM-dd HH:mm") + "</body></html>");
        queueLabel.setPreferredSize(new Dimension(80, 30));
        queueLabel.setBackground(MatchHistoryUtil.getBackGroundColor(isWin));
        queueLabel.setForeground(new Color(62, 132, 255));
        JLabel winLabel = new JLabel("<html><body>" + (isWin ? "胜利" : "失败") + "<hr>" + record.getGameDuration() / 60 + "分钟" + record.getGameDuration() % 60 + "秒</body></html>");
        winLabel.setPreferredSize(new Dimension(80, 30));
        winLabel.setBackground(MatchHistoryUtil.getBackGroundColor(isWin));
        winLabel.setForeground(Color.WHITE);
        JPanel lineOnePanel = new JPanel();
        lineOnePanel.setBackground(MatchHistoryUtil.getBackGroundColor(isWin));
        lineOnePanel.setLayout((new BoxLayout(lineOnePanel, BoxLayout.Y_AXIS)));
        lineOnePanel.add(queueLabel);
        lineOnePanel.add(winLabel);
        return lineOnePanel;
    }

    private JPanel buildLineTwo(SpgParticipants selfData) {
        JPanel lineTwoPanel = new JPanel();
        lineTwoPanel.setLayout((new BoxLayout(lineTwoPanel, BoxLayout.Y_AXIS)));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(selfData.getChampionId(), FrameSetting.CHAMPION_ICON_SIZE, FrameSetting.CHAMPION_ICON_SIZE, ImageEnum.SQUARE));
        lineTwoPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        lineTwoPanel.add(iconLabel);
        return lineTwoPanel;
    }

    private JPanel buildLineThree(SpgParticipants selfData) {
        JPanel lineThreePanel = new JPanel();
        lineThreePanel.setLayout((new BoxLayout(lineThreePanel, BoxLayout.Y_AXIS)));
        JLabel summonerSpellLabel1 = new JLabel();
        JLabel summonerSpellLabel2 = new JLabel();
        summonerSpellLabel1.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(selfData.getSpell1Id(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        summonerSpellLabel2.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(selfData.getSpell2Id(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        lineThreePanel.add(summonerSpellLabel1);
        lineThreePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lineThreePanel.add(summonerSpellLabel2);
        return lineThreePanel;
    }

    private JPanel buildLineFour(SpgParticipants selfData) {
        JPanel lineFourPanel = new JPanel();
        lineFourPanel.setLayout((new BoxLayout(lineFourPanel, BoxLayout.Y_AXIS)));
        JLabel perkStyleLabel1 = new JLabel();
        JLabel perkStyleLabel2 = new JLabel();
        perkStyleLabel1.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(selfData.getPerks().getStyles().get(0).getStyle(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        perkStyleLabel2.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(selfData.getPerks().getStyles().get(1).getStyle(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        lineFourPanel.add(perkStyleLabel1);
        lineFourPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        lineFourPanel.add(perkStyleLabel2);
        return lineFourPanel;
    }

    private JPanel buildLineFive(SpgParticipants selfData, Color color) {
        JPanel lineFivePanel = new JPanel();
        lineFivePanel.setLayout((new BoxLayout(lineFivePanel, BoxLayout.Y_AXIS)));
        lineFivePanel.setBackground(color);
        JPanel namePanel = new JPanel();
        namePanel.setLayout((new BoxLayout(namePanel, BoxLayout.X_AXIS)));
        namePanel.setBackground(color);

        JLabel nameLabel = new JLabel("<html><body><b>" + selfData.getRiotIdGameName() + "#" + selfData.getRiotIdTagline() + "</b></body></html>");
        nameLabel.setBackground(color);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        namePanel.add(nameLabel);


        JLabel kdaLabel = new JLabel("<html><body><b>KDA：" + selfData.getKills() + "/" + selfData.getDeaths() + "/" + selfData.getAssists() + "</b></body></html>");
        kdaLabel.setBackground(color);
        kdaLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        namePanel.add(kdaLabel);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(color);
        JLabel item1 = new JLabel();
        JLabel item2 = new JLabel();
        JLabel item3 = new JLabel();
        JLabel item4 = new JLabel();
        JLabel item5 = new JLabel();
        JLabel item6 = new JLabel();
        item1.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem0(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        item2.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem1(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        item3.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem2(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        item4.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem3(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        item5.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem4(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        item6.setIcon(MatchHistoryUtil.getItemImageIcon(selfData.getItem5(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
        itemsPanel.add(item1);
        itemsPanel.add(item2);
        itemsPanel.add(item3);
        itemsPanel.add(item4);
        itemsPanel.add(item5);
        itemsPanel.add(item6);

        lineFivePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        lineFivePanel.add(namePanel);
        lineFivePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        lineFivePanel.add(itemsPanel);
        return lineFivePanel;
    }

    private JPanel buildLineSix(List<SpgParticipants> teamOne, List<SpgParticipants> teamTwo, Color color) {
        JPanel lineSixPanel = new JPanel();
        lineSixPanel.setBackground(color);
        lineSixPanel.setLayout((new BoxLayout(lineSixPanel, BoxLayout.Y_AXIS)));
        JPanel horizontalPanelOne = new JPanel();
        horizontalPanelOne.setBackground(color);
        horizontalPanelOne.setLayout((new BoxLayout(horizontalPanelOne, BoxLayout.X_AXIS)));
        JPanel horizontalPanelTwo = new JPanel();
        horizontalPanelTwo.setBackground(color);
        horizontalPanelTwo.setLayout((new BoxLayout(horizontalPanelTwo, BoxLayout.X_AXIS)));
        //队伍1不为空
        if (teamTwo != null) {
            for (int i = 0; i < 5; i++) {
                JLabel iconLabel = new JLabel();
                if (i < teamOne.size()) {
                    iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(teamOne.get(i).getChampionId(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                } else {
                    iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(0, FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                }
                horizontalPanelOne.add(iconLabel);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                JLabel iconLabel = new JLabel();
                iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(0, FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                horizontalPanelOne.add(iconLabel);
            }
        }
        //队伍2不为空
        if (teamTwo != null) {
            for (int i = 0; i < 5; i++) {
                JLabel iconLabel = new JLabel();
                if (i < teamTwo.size()) {
                    iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(teamTwo.get(i).getChampionId(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                } else {
                    iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(0, FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                }
                horizontalPanelTwo.add(iconLabel);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                JLabel iconLabel = new JLabel();
                iconLabel.setIcon(MatchHistoryUtil.getChampionIcon(0, FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE, ImageEnum.SQUARE));
                horizontalPanelTwo.add(iconLabel);
            }
        }

        lineSixPanel.add(horizontalPanelOne);
        lineSixPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        lineSixPanel.add(horizontalPanelTwo);
        return lineSixPanel;
    }

    private void setBgColor(Component target, Color color) {
        target.setBackground(color);
    }

    /**
     * 显示所有隐藏的数据
     */
    public void showAllComponent() {
        for (Component component : panelContainer.getComponents()) {
            component.setVisible(true);
        }
    }
}

@Slf4j
class BlackListSelectDetailListener extends MouseAdapter {
    private final BlackListBO blackListBO;


    public BlackListSelectDetailListener(BlackListBO blackListBO) {
        this.blackListBO = blackListBO;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !FrameInnerCache.blackListMatchPanel.isShowDetail && e.getSource() instanceof BlackListHistoryLine blackListHistoryLine) {
            if (blackListBO != null) {
                Component[] components = blackListHistoryLine.getParent().getComponents();
                for (Component component : components) {
                    if (!component.equals(blackListHistoryLine)) {
                        component.setVisible(false);
                    }
                }
                try {
                    FrameInnerCache.blackListMatchPanel.blackListHistoryDetail = BlackListHistoryDetail.build(blackListBO);
                } catch (IOException ex) {
                    log.error("查询黑名单错误" + ex.getMessage());
                }
                FrameInnerCache.blackListMatchPanel.panelContainer.add(FrameInnerCache.blackListMatchPanel.blackListHistoryDetail);
                FrameInnerCache.blackListMatchPanel.isShowDetail = true;
            }
            if (blackListBO != null && blackListBO.getBlackListPlayers().stream().anyMatch(item -> item.getRemark() != null)) {
                StringBuilder builder = new StringBuilder();
                for (BlackListPlayer player : blackListBO.getBlackListPlayers()) {
                    builder.append(player.getGameName());
                    builder.append(":");
                    builder.append(player.getRemark());
                    builder.append("\n");
                }
                JOptionPane.showMessageDialog(null, builder.toString(), "黑名单备注", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (e.getButton() == MouseEvent.BUTTON1 && FrameInnerCache.blackListMatchPanel.isShowDetail && e.getSource() instanceof BlackListHistoryLine && FrameInnerCache.blackListMatchPanel.isShowDetail) {
            FrameInnerCache.blackListMatchPanel.panelContainer.remove(FrameInnerCache.blackListMatchPanel.blackListHistoryDetail);
            FrameInnerCache.blackListMatchPanel.showAllComponent();
            FrameInnerCache.blackListMatchPanel.isShowDetail = false;
        }
    }

}
