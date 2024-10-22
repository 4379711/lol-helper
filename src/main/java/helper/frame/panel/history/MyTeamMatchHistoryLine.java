package helper.frame.panel.history;

import helper.bo.ProductsMatchHistoryBO;
import helper.bo.SGPRank;
import helper.bo.TeamSummonerBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.frame.bo.ChampionWin;
import helper.frame.constant.ColorConstant;
import helper.frame.panel.base.BaseLabel;
import helper.frame.panel.base.RoundedVerticalPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author WuYi
 */
public class MyTeamMatchHistoryLine extends JPanel {

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
		JLabel rankLabel = new JLabel();
		JLabel winRateLabel = new BaseLabel(data.getWinRate(), ColorConstant.DARK_FOUR, 20, 20);

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
				if (FrameInnerCache.matchPanel == null && FrameInnerCache.matchFrame == null) {
					JFrame jFrame = new JFrame("战绩查询");
					FrameInnerCache.matchFrame = jFrame;
					Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
					jFrame.setIconImage(image);
					jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
					//窗口居中
					jFrame.setLocationRelativeTo(null);
					MatchPanel matchPanel = new MatchPanel();
					ProductsMatchHistoryBO pmh = null;
					try {
						pmh = AppCache.api.getProductsMatchHistoryByPuuid(data.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
					matchPanel.resetIndex();
					matchPanel.setData(pmh, data.getPuuid());
					jFrame.add(matchPanel);
					jFrame.setVisible(true);
				} else {
					FrameInnerCache.matchFrame.setVisible(true);
					ProductsMatchHistoryBO pmh = null;
					try {
						pmh = AppCache.api.getProductsMatchHistoryByPuuid(data.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1);
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
					FrameInnerCache.matchPanel.resetIndex();
					FrameInnerCache.matchPanel.setData(pmh, data.getPuuid());

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
		winRateLabel.setForeground(new Color(103, 194, 58));
		// 设置字体 (字体名称，样式，大小)
		winRateLabel.setFont(new Font("Nunito", Font.BOLD, 18));
		panelThree.add(nameLabel);
		panelThree.add(rankLabel);
		panelOne.add(iconLabel);
		panelOne.add(winRateLabel);
		panelOne.add(panelThree);

		for (ChampionWin championWin : data.getChampionWinList()) {
			JLabel jLabel = new JLabel();
			jLabel.setIcon(championWin.getIcon());
			panelTwo.add(jLabel);
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
