package helper.frame.panel.history;

import cn.hutool.core.util.NumberUtil;
import com.sun.jna.platform.win32.WinDef;
import helper.bo.SpgParticipants;
import helper.bo.SpgProductsMatchHistoryBO;
import helper.bo.TeamSummonerBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.enums.ImageEnum;
import helper.frame.bo.ChampionWin;
import helper.frame.constant.ColorConstant;
import helper.frame.panel.base.HorizontalDivider;
import helper.frame.panel.client.PickSkinBox;
import helper.frame.utils.MatchHistoryUtil;
import helper.utils.Win32Util;

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

		int height = 20;
		int width = 240;
		// 设置布局管理器为 BoxLayout（垂直排列）
		//this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setLayout(new CardLayout());
		JPanel jPanel = new JPanel();
		jPanel.setBackground(ColorConstant.DARK_THREE);
		if (AppCache.settingPersistence.getPickSkin()) {
			PickSkinBox pickSkinBox = new PickSkinBox();
			pickSkinBox.setBackground(ColorConstant.DARK_THREE);
			pickSkinBox.setForeground(Color.WHITE);
			height += 40;
			jPanel.add(pickSkinBox);
		}
		if (AppCache.settingPersistence.getShowMatchHistory()) {
			jPanel.add(getMapSide(GameDataCache.myTeamMatchHistory.get(0).getMapSide()));
			for (TeamSummonerBO data : GameDataCache.myTeamMatchHistory) {
				jPanel.add(new HorizontalDivider(this.getWidth() - 10));
				MyTeamMatchHistoryLine builder = MyTeamMatchHistoryLine.builder(buildTeamLineData(data), this.getWidth());
				jPanel.add(builder);
			}
			height += 580;
		}
		this.add(jPanel);

		Dimension size = new Dimension(width, height);
		this.setSize(size);
		int x = lolWindows.left - width;
		int y = lolWindows.top;
		Point p = new Point(x, y);
		this.setLocation(p);
		// 创建一个圆角矩形形状
		Shape roundRect = new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 15, 15);
		this.setShape(roundRect);
		this.setVisible(true);

	}

	public static void start() {
		FrameInnerCache.myTeamMatchHistoryPanel = new MyTeamMatchHistoryPanel();
	}

	private TeamSummonerBO buildTeamLineData(TeamSummonerBO data) {
		List<SpgParticipants> spgParticipantsList = new ArrayList<>();
		//获取自己的所有数据

		for (SpgProductsMatchHistoryBO item : data.getMatchHistory()) {
			SpgParticipants spgParticipants = item.getJson().getParticipants()
						.stream()
						.filter(line -> line.getPuuid().equals(data.getPuuid()))
						.findFirst()
						.get();
				spgParticipantsList.add(spgParticipants);
		}

		if (!spgParticipantsList.isEmpty()) {
			//计算平均数据
			Map<Integer, ChampionWin> winChampions = new HashMap<>();
			int win = 0;
			int fail = 0;
			int kill = 0;
			int death = 0;
			int count = 0;
			int successiveCount = 0;
			boolean successiveWin = spgParticipantsList.get(0).isWin();
			boolean successiveFlag = true;
			for (SpgParticipants spgParticipants : spgParticipantsList) {
				count++;
				if (successiveFlag && successiveWin == spgParticipants.isWin()) {
					successiveCount++;
				} else {
					successiveFlag = false;
				}
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
					fail++;
					if (winChampions.containsKey(spgParticipants.getChampionId())) {
						ChampionWin championWin = winChampions.get(spgParticipants.getChampionId());
						championWin.setFails(championWin.getFails() + 1);
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
			data.setSuccessiveWin(successiveWin);
			data.setSuccessiveCount(successiveCount);
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
		} else {
			data.setWinRate("无数据");
			data.setProfileIcon(MatchHistoryUtil.getSummonerIcon(data.getProfileIconId(), 50, 50));
		}

		return data;
	}

	private JLabel getMapSide(String mapSide) {
		JLabel mapSideLabel = new JLabel();
		if ("blue".equals(mapSide)) {
			mapSideLabel.setText("这把是蓝色方,泉水在左下角.");
			mapSideLabel.setForeground(ColorConstant.BLUE);
		} else if ("red".equals(mapSide)) {
			mapSideLabel.setText("这把是红色方,泉水在右上角.");
			mapSideLabel.setForeground(ColorConstant.RED);
		}
		return mapSideLabel;
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
