package yalong.site.frame.panel.history;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.GameData;
import yalong.site.bo.Participants;
import yalong.site.bo.Player;
import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.cache.GameDataCache;
import yalong.site.enums.ImageEnum;
import yalong.site.frame.panel.base.SearchTextField;
import yalong.site.frame.utils.MatchHistoryUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 战绩查询的容器需要一条一条生成数据
 *
 * @author WuYi
 */
@Slf4j
public class MatchPanel extends JPanel {
	/**
	 * 战绩数据容器
	 */
	public JPanel panelContainer = new JPanel();
	/**
	 * 翻页容器
	 */
	private final JPanel panelIndex = new JPanel();
	private final Label labelIndex = new Label();
	private final JButton buttonPrevious = new JButton();
	private final JButton buttonNext = new JButton();
	private int index = 1;
	private static String currentId;
	/**
	 * 搜索容器
	 */
	private final JPanel searchPanel = new JPanel();
	private final SearchTextField searchText = new SearchTextField(40);

	public MatchPanel() {
		this.setName("查询战绩");
		FrameInnerCache.matchPanel = this;
		// 设置透明
		this.setOpaque(false);
		this.setBorder(null);
		this.setAutoscrolls(true);
		this.setMaximumSize(new Dimension(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT));
		this.searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		this.searchPanel.add(this.searchText);
		this.searchText.addActionListener(actionListener());
		this.add(searchPanel);
		this.panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
		this.panelIndex.setLayout(new BoxLayout(panelIndex, BoxLayout.X_AXIS));
		this.buttonPrevious.setText("上一页");
		this.labelIndex.setText(Integer.toString(index));
		this.buttonNext.setText("下一页");
		//上一页鼠标事件
		this.buttonPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (index > 1) {
					try {
						index--;
						ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(currentId, (index - 1) * FrameSetting.PAGE_SIZE, index * FrameSetting.PAGE_SIZE - 1);
						setData(pmh, currentId);
					} catch (IOException ex) {
						resetIndex();
						log.error("查询战绩错误" + ex.getMessage());
					}
				}
			}
		});
		//下一页鼠标事件
		this.buttonNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (FrameSetting.PAGE_SIZE * index < FrameSetting.PAGE_MAX) {
					try {
						index++;
						ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(currentId, (index - 1) * FrameSetting.PAGE_SIZE, index * FrameSetting.PAGE_SIZE - 1);
						setData(pmh, currentId);
					} catch (IOException ex) {
						resetIndex();
						log.error("查询战绩错误" + ex.getMessage());
					}
				}
			}
		});
		this.panelIndex.add(buttonPrevious);
		this.panelIndex.add(labelIndex);
		this.panelIndex.add(buttonNext);

		this.resetIndex();
		this.showAllComponent();

	}

	public static MatchPanel builder() {
		return new MatchPanel();
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
	 * @param bo 战绩
	 */
	public void setData(ProductsMatchHistoryBO bo, String puuid) {
		currentId = puuid;
		this.panelContainer.removeAll();
		if (bo.getGames() != null) {
			for (int i = 0; i < bo.getGames().getGameCount(); i++) {
				//获取游戏的对局信息
				GameData gameData = bo.getGames().getGames().get(i);
				ArrayList<Object> matchHistoryBO = createMatchHistoryBO(gameData);
				boolean win = gameData.getParticipants().get(0).getStats().getWin();
				HistoryLine panel = HistoryLine.builder(matchHistoryBO, MatchHistoryUtil.getBackGroundColor(win), gameData.getGameId());
				panelContainer.add(panel);
			}
			this.labelIndex.setText(Integer.toString(index));
			panelContainer.add(this.panelIndex);
			//this.setViewportView(panelContainer);
			panelContainer.revalidate();
			panelContainer.repaint();
			this.add(panelContainer);
		} else {
			log.error("玩家数据为空");
		}

	}

	/**
	 * 创建战绩显示对象
	 * 格式数量需要和{@link HistoryLine#getGridBagConstraints()}方法按顺序一一对应
	 *
	 * @param data 游戏数据
	 * @return ArrayList 战绩对象列表
	 */
	private ArrayList<Object> createMatchHistoryBO(GameData data) {
		Participants me = data.getParticipants().get(0);
		ArrayList<Object> list = new ArrayList<>();
 		list.add("<html><body>" + GameDataCache.allGameQueuesList.get(data.getQueueId()).getName() + "<hr>" + DateUtil.format(data.getGameCreationDate(), "MM-dd HH:mm") + "</body></html>");
		list.add(MatchHistoryUtil.getChampionIcon(me.getChampionId(), FrameSetting.CHAMPION_ICON_SIZE, FrameSetting.CHAMPION_ICON_SIZE, ImageEnum.ROUND));
		list.add(MatchHistoryUtil.getSummonerSpellImageIcon(me.getSpell1Id(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getPerkStyleImageIcon(me.getStats().getPerkPrimaryStyle(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		//召唤师名
		list.add("<html><body><b>" + data.getParticipantIdentities().get(0).getPlayer().getGameName() + "#" + data.getParticipantIdentities().get(0).getPlayer().getTagLine() + "</b></body></html>");
		list.add("KDA：" + me.getStats().getKills() + "/" + me.getStats().getDeaths() + "/" + me.getStats().getAssists());
		list.add("<html><body>对英雄造成伤害：" + me.getStats().getTotalDamageDealtToChampions() + "<hr>承受伤害：" + me.getStats().getTotalDamageTaken() + "<hr>治疗血量：" + me.getStats().getTotalHeal() + "</body></html>");
		list.add("<html><body>获得金币：" + me.getStats().getGoldEarned() + "<hr>补兵数：" + me.getStats().getTotalMinionsKilled() + "<hr>控制时间" + me.getStats().getTimeCCingOthers() + "秒</body></html>");
		//第二行
		list.add("<html><body>" + MatchHistoryUtil.getWin(me.getTeamId(), data.getTeams()) + "<hr>" + data.getGameDuration() / 60 + "分钟" + data.getGameDuration() % 60 + "秒</body></html>");
		list.add(MatchHistoryUtil.getSummonerSpellImageIcon(me.getSpell2Id(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getPerkStyleImageIcon(me.getStats().getPerkSubStyle(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem0(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem1(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem2(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem3(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem4(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem5(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		list.add(MatchHistoryUtil.getItemImageIcon(me.getStats().getItem6(), FrameSetting.ITEM_ICON_SIZE, FrameSetting.ITEM_ICON_SIZE));
		return list;
	}

	/**
	 * 显示所有隐藏的数据
	 */
	public void showAllComponent() {
		for (Component component : panelContainer.getComponents()) {
			component.setVisible(true);
		}
	}

	private ActionListener actionListener() {
		return e -> {
			if (e.getSource() instanceof SearchTextField search) {
				String input = search.getText();
				Player summonerInfo = null;
				if (input.isEmpty() && input.isBlank()) {
					input = GameDataCache.me.getGameName()+"#"+GameDataCache.me.getTagLine();
				}
				try {
					summonerInfo = AppCache.api.getV2InfoByNameList(Collections.singletonList(input)).get(0);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "未查询到召唤师：" + input, "提示", JOptionPane.INFORMATION_MESSAGE);
					log.error("未查询到召唤师");
				}
				try {
					if (summonerInfo != null) {
						FrameInnerCache.matchPanel.setData(AppCache.api.getProductsMatchHistoryByPuuid(summonerInfo.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1), summonerInfo.getPuuid());
						FrameInnerCache.matchPanel.showAllComponent();
						FrameInnerCache.matchPanel.resetIndex();

					}
				} catch (IOException ex) {
					log.error("查询战绩错误");
				}


			}
		};
	}

}
