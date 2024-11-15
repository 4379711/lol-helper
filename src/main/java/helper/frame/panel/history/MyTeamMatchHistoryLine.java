package helper.frame.panel.history;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import helper.bo.*;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.enums.ImageEnum;
import helper.frame.constant.ColorConstant;
import helper.frame.constant.GameConstant;
import helper.frame.panel.base.BaseLabel;
import helper.frame.panel.base.RoundedVerticalPanel;
import helper.frame.utils.MatchHistoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
public class MyTeamMatchHistoryLine extends JPanel {

	private static final Logger log = LoggerFactory.getLogger(MyTeamMatchHistoryLine.class);
	private JPanel panelOne;
	private JPanel panelTwo;
	private JPanel panelThree;
	public MyTeamMatchHistoryLine(TeamSummonerBO data, int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBackground(ColorConstant.DARK_TWO);
		this.setSize(width, this.getHeight());
		//setOpaque(false); // 确保这个面板也透明
		panelOne = new RoundedVerticalPanel(10, ColorConstant.DARK_THREE);
		panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.X_AXIS));
		panelOne.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelThree = new RoundedVerticalPanel(10, ColorConstant.DARK_THREE);
		panelThree.setLayout(new BoxLayout(panelThree, BoxLayout.Y_AXIS));
		panelThree.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelTwo = new RoundedVerticalPanel(10, ColorConstant.DARK_THREE);
		panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.X_AXIS));
		panelTwo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelThree = new RoundedVerticalPanel(10, ColorConstant.DARK_THREE);

		JLabel iconLabel = new JLabel();
		JLabel nameLabel = new BaseLabel(data.getName(), ColorConstant.DARK_FOUR, 15, 15);
		nameLabel.setAlignmentX(LEFT_ALIGNMENT);
		JLabel rankLabel = new JLabel();
		JLabel winRateLabel = new BaseLabel(data.getWinRate(), ColorConstant.DARK_FOUR, 20, 20);
		JLabel blackListLabel = new JLabel();
		Map<String, String> map = AppCache.settingPersistence.getBlacklistPlayers();
		boolean isBlackListPlayer = map.containsKey(data.getPuuid());
		if (isBlackListPlayer) {
			blackListLabel.setText("[黑]");
			blackListLabel.setForeground(new Color(245, 108, 108));
			blackListLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// 鼠标移入时更改鼠标样式
					blackListLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// 鼠标移出时恢复默认样式
					blackListLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JFrame jFrame = new JFrame("黑名单查询");
					FrameInnerCache.blackListFrame = jFrame;
					Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
					jFrame.setIconImage(image);
					jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
					//窗口居中
					jFrame.setLocationRelativeTo(null);

					BlackListMatchPanel blackListMatchPanel = new BlackListMatchPanel(true);
					jFrame.add(blackListMatchPanel);
					jFrame.setVisible(true);
					String s = map.get(data.getPuuid());
					List<String> split = Arrays.stream(s.split(",")).sorted().limit(FrameSetting.PAGE_SIZE - 1).toList();
					List<BlackListBO> blackLists = new ArrayList<BlackListBO>();
					for (String gameId : split) {
						String jsonString = FileUtil.readUtf8String(new File(GameConstant.BLACK_LIST_FILE + gameId + ".json"));
						BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
						blackLists.add(blackListBO);
					}
					blackListMatchPanel.setData(blackLists);
					blackListMatchPanel.resetIndex();
				}
			});

		} else {
			blackListLabel.setText("[黑]");
			blackListLabel.setForeground(ColorConstant.DARK_THREE);
		}
		rankLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		iconLabel.setIcon(data.getProfileIcon());
		rankLabel.setText(getRank(data.getRank()));
		nameLabel.setText((data.getName()));
		// 添加鼠标事件监听器
		nameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// 鼠标移入时更改鼠标样式
				nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标移出时恢复默认样式
				nameLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// 点击时触发事件
				if (FrameInnerCache.sgpRecordPanel == null && FrameInnerCache.sgpRecordFrame == null) {
					JFrame jFrame = new JFrame("战绩查询");
					FrameInnerCache.sgpRecordFrame = jFrame;
					Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
					jFrame.setIconImage(image);
					jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
					//窗口居中
					jFrame.setLocationRelativeTo(null);
					SGPRecordPanel sgpRecordPanel = new SGPRecordPanel();
					List<SpgProductsMatchHistoryBO> sgpRecordList = null;
					Player playerDetail = null;
					try {
						sgpRecordList = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), data.getPuuid(), 0, FrameSetting.PAGE_SIZE);
						List<String> list = new ArrayList<String>();
						list.add(data.getName() + "#" + data.getTagLine());
						List<Player> summonerInfoBOList = AppCache.api.getV2InfoByNameList(list);
						playerDetail = summonerInfoBOList.get(0);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
					sgpRecordPanel.resetIndex();
					sgpRecordPanel.setData(sgpRecordList, data.getPuuid(), BeanUtil.toBean(playerDetail, MePlayer.class));
					jFrame.add(sgpRecordPanel);
					jFrame.setVisible(true);
				} else {
					FrameInnerCache.sgpRecordFrame.setVisible(true);
					List<SpgProductsMatchHistoryBO> sgpRecordList = null;
					Player playerDetail = null;
					try {
						sgpRecordList = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), data.getPuuid(), 0, FrameSetting.PAGE_SIZE);
						List<String> list = new ArrayList<String>();
						list.add(data.getName() + "#" + data.getTagLine());
						List<Player> summonerInfoBOList = AppCache.api.getV2InfoByNameList(list);
						playerDetail = summonerInfoBOList.get(0);
					} catch (IOException ex) {
						log.error("查询战绩错误" + ex.getMessage());
					}
					FrameInnerCache.sgpRecordPanel.resetIndex();
					FrameInnerCache.sgpRecordPanel.setData(sgpRecordList, data.getPuuid(), BeanUtil.toBean(playerDetail, MePlayer.class));

				}
			}
		});
		// 设置字体颜色
		rankLabel.setForeground(Color.WHITE);
		// 设置字体 (字体名称，样式，大小)
		rankLabel.setFont(new Font("SimHei", Font.BOLD, 12));
		// 设置字体颜色
		nameLabel.setForeground(Color.WHITE);
		// 设置字体 (字体名称，样式，大小)
		nameLabel.setFont(new Font("SimHei", Font.PLAIN, 13));

		// 设置字体颜色
		winRateLabel.setForeground(ColorConstant.GREEN);
		// 设置字体 (字体名称，样式，大小)
		winRateLabel.setFont(new Font("Nunito", Font.BOLD, 18));

		//姓名和黑名单
		JPanel namePanel = new JPanel();
		//namePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		namePanel.setBackground(ColorConstant.DARK_THREE);
		namePanel.setMaximumSize(new Dimension(130, 20));
		namePanel.setPreferredSize(new Dimension(130, 20));
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.add(nameLabel);
		namePanel.add(Box.createHorizontalGlue());
		namePanel.add(blackListLabel);

		//段位和连胜连败
		JPanel rankPanel = new JPanel();
		rankPanel.setBackground(ColorConstant.DARK_THREE);
		rankPanel.setLayout(new BoxLayout(rankPanel, BoxLayout.X_AXIS));
		rankPanel.add(rankLabel);
		if (data.getSuccessiveCount() >= 3) {
			JLabel fireLabel = new JLabel();
			JLabel textLabel = new JLabel();
			textLabel.setText(data.getSuccessiveCount() + "连" + (data.isSuccessiveWin() ? "胜" : "败"));
			// 设置字体颜色
			textLabel.setForeground(Color.WHITE);
			// 设置字体 (字体名称，样式，大小)
			textLabel.setFont(new Font("SimHei", Font.PLAIN, 12));
			if (data.isSuccessiveWin()) {
				fireLabel.setIcon(new ImageIcon(AppCache.fireImage));
			} else {
				fireLabel.setIcon(new ImageIcon(AppCache.cryImage));
			}

			rankPanel.add(fireLabel);
			rankPanel.add(textLabel);
		} else {
			JPanel jPanel = new JPanel();
			jPanel.setBackground(ColorConstant.DARK_THREE);
			rankPanel.add(jPanel);
		}


		panelThree.add(namePanel);
		panelThree.add(rankPanel);

		panelOne.add(iconLabel);
		panelOne.add(winRateLabel);
		panelOne.add(panelThree);

		if (data.getChampionWinList() != null) {
			int i = 0;
			for (; i < data.getChampionWinList().size(); i++) {
				JPanel jPanel = new JPanel();
				jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
				jPanel.setBackground(ColorConstant.DARK_THREE);

				JPanel championPanel = new JPanel();
				championPanel.setBackground(ColorConstant.DARK_THREE);
				JLabel championIcon = new JLabel();
				championPanel.add(championIcon);
				championIcon.setIcon(data.getChampionWinList().get(i).getIcon());
				championPanel.setMaximumSize(new Dimension(40, 40));
				championPanel.setPreferredSize(new Dimension(40, 40));
				JPanel winPanel = new JPanel();

				winPanel.setLayout(new BoxLayout(winPanel, BoxLayout.X_AXIS));
				winPanel.setMaximumSize(new Dimension(40, 14));
				winPanel.setPreferredSize(new Dimension(40, 14));
				winPanel.setBackground(ColorConstant.DARK_THREE);

				JLabel winLabel = new JLabel();
				JLabel failLabel = new JLabel();
				winLabel.setText(data.getChampionWinList().get(i).getWins() + "W");
				// 设置字体颜色
				winLabel.setForeground(ColorConstant.GREEN);
				// 设置字体 (字体名称，样式，大小)
				winLabel.setFont(new Font("Nunito", Font.BOLD, 13));
				failLabel.setText(data.getChampionWinList().get(i).getFails() + "L");
				// 设置字体颜色
				failLabel.setForeground(ColorConstant.RED);
				// 设置字体 (字体名称，样式，大小)
				failLabel.setFont(new Font("Nunito", Font.BOLD, 13));
				winPanel.add(winLabel);
				winPanel.add(failLabel);

				jPanel.add(championPanel);
				jPanel.add(winPanel);
				panelTwo.add(jPanel);
			}
			for (; i < 5; i++) {
				JPanel jPanel = new JPanel();
				JLabel championIcon = new JLabel();
				championIcon.setIcon(MatchHistoryUtil.getChampionIcon(0, 40, 40, ImageEnum.SQUARE));
				JLabel winLabel = new JLabel();
				winLabel.setText("0W0L");
				// 设置字体颜色
				winLabel.setForeground(ColorConstant.DARK_THREE);
				// 设置字体 (字体名称，样式，大小)
				winLabel.setFont(new Font("Nunito", Font.BOLD, 13));
				jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
				jPanel.setBackground(ColorConstant.DARK_THREE);
				jPanel.add(championIcon);
				jPanel.add(winLabel);
				panelTwo.add(jPanel);
			}
		} else {
			for (int i = 0; i <= 5; i++) {
				JPanel jPanel = new JPanel();
				JLabel championIcon = new JLabel();
				championIcon.setIcon(MatchHistoryUtil.getChampionIcon(0, 40, 40, ImageEnum.SQUARE));
				JLabel winLabel = new JLabel();
				winLabel.setText("0W0L");
				// 设置字体颜色
				winLabel.setForeground(ColorConstant.DARK_THREE);
				// 设置字体 (字体名称，样式，大小)
				winLabel.setFont(new Font("Nunito", Font.BOLD, 13));
				jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
				jPanel.setBackground(ColorConstant.DARK_THREE);
				jPanel.add(championIcon);
				jPanel.add(winLabel);
				panelTwo.add(jPanel);
			}

		}

		this.add(panelOne);
		this.add(panelTwo);
	}

	public static MyTeamMatchHistoryLine builder(TeamSummonerBO data, int width) {
		return new MyTeamMatchHistoryLine(data, width);
	}

	private String getRank(SGPRank rank) {
		if (rank == null) {
			return "暂无段位";
		} else {
			return rank.getTier() + "·" + rank.getRank();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(ColorConstant.DARK_THREE); // 背景颜色
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5); // 绘制圆角矩形背景
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(ColorConstant.DARK_THREE); // 边框颜色
		g2.setStroke(new BasicStroke(2)); // 边框宽度
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5); // 绘制圆角矩形边框
	}

}
