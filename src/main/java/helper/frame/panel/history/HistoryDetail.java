package helper.frame.panel.history;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.*;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.constant.ColorConstant;
import helper.enums.ImageEnum;
import helper.frame.bo.ScoreLevelBO;
import helper.constant.GameConstant;
import helper.frame.utils.FrameConfigUtil;
import helper.frame.utils.FrameTipUtil;
import helper.frame.utils.MatchHistoryUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 战绩详情
 */
@Slf4j
public class HistoryDetail extends JPanel {
	private HistoryDetail detail;
	private String currentRegion;
	private SpgGames currentSpgGame;

	public HistoryDetail(SpgGames bo) throws IOException {
		detail = this;
		currentRegion = bo.getPlatformId();
		currentSpgGame = bo;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(buildTeam1Title());
		Map<Integer, List<SpgParticipants>> collect = bo.getParticipants().stream().collect(Collectors.groupingBy(SpgParticipants::getTeamId));
		List<SpgParticipants> teamOne = collect.get(GameConstant.TEAM_ONE);
		List<SpgParticipants> teamTwo = collect.get(GameConstant.TEAM_TWO);
		Map<Integer, List<ScoreLevelBO>> scoreLevelMap = MatchHistoryUtil.getScoreLevelList(teamOne, teamTwo);
		for (int i = 0; i < teamOne.size(); i++) {
			if (teamOne.get(i) != null && bo.getParticipants().get(i) != null) {
				this.add(buildTeamData(teamOne.get(i), scoreLevelMap.get(GameConstant.TEAM_ONE).get(i), bo.getGameId()));
			}
		}
		//this.add(buildTeam2Title());
		for (int i = 0; i < teamTwo.size(); i++) {
			if (teamTwo.get(i) != null && bo.getParticipants().get(i) != null) {
				this.add(buildTeamData(teamTwo.get(i), scoreLevelMap.get(GameConstant.TEAM_TWO).get(i), bo.getGameId()));
			}
		}
	}

	public static HistoryDetail build(SpgGames bo) throws IOException {
		return new HistoryDetail(bo);
	}

	private JPanel buildTeam1Title() {
		JPanel jPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		jPanel.setLayout(layout);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		jPanel.setBackground(Color.GRAY);
		ArrayList<GridBagConstraints> grid = titleGridList();
		jPanel.add(new JLabel("红蓝方"), grid.get(0));
		jPanel.add(new JLabel("评分"), grid.get(1));
		jPanel.add(new JLabel("KDA"), grid.get(2));
		jPanel.add(new JLabel("伤害"), grid.get(3));
		jPanel.add(new JLabel("眼"), grid.get(4));
		jPanel.add(new JLabel("补兵数"), grid.get(5));
		jPanel.add(new JLabel("装备"), grid.get(6));
		return jPanel;
	}


	/**
	 * 管局玩家数据构建布局
	 *
	 * @return JPanel
	 */
	private JPanel buildTeamData(SpgParticipants data, ScoreLevelBO scoreLevel, Long gameId) throws IOException {
		JPanel jPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		jPanel.setLayout(layout);
		boolean isPlackListPlayer = AppCache.settingPersistence.getBlacklistPlayers().containsKey(data.getPuuid());
		Color bgColor = MatchHistoryUtil.getBackGroundColor(data.isWin());
		if (!isPlackListPlayer) {
			jPanel.setBackground(bgColor);
		} else {
			jPanel.setBackground(ColorConstant.DARK_THREE);
		}
		jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		ArrayList<GridBagConstraints> gridList = gridList();

		//英雄头像s
		JLabel ChampionIcon = new JLabel();
		ChampionIcon.setIcon(MatchHistoryUtil.getChampionIcon(data.getChampionId(), FrameSetting.DETAIL_CHAMPION_ICON_SIZE, FrameSetting.DETAIL_CHAMPION_ICON_SIZE, ImageEnum.SQUARE));

		jPanel.add(ChampionIcon, gridList.get(0));
		//召唤师技能
		JLabel spellIcon1 = new JLabel();
		spellIcon1.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(data.getSpell1Id(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
		jPanel.add(spellIcon1, gridList.get(1));

		JLabel spellIcon2 = new JLabel();
		spellIcon2.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(data.getSpell2Id(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
		jPanel.add(spellIcon2, gridList.get(2));

		//基石符文
		JLabel perkStyle1 = new JLabel();
		perkStyle1.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(data.getPerks().getStyles().get(0).getStyle(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
		jPanel.add(perkStyle1, gridList.get(3));
		JLabel perkStyle2 = new JLabel();
		perkStyle2.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(data.getPerks().getStyles().get(1).getStyle(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
		jPanel.add(perkStyle2, gridList.get(4));
		//姓名
		JLabel name = new JLabel(data.getRiotIdGameName() + "#" + data.getRiotIdTagline());
		name.setForeground(Color.WHITE);
		Player player = new Player();
		player.setPuuid(data.getPuuid());
		player.setGameName(data.getRiotIdGameName());
		player.setTagLine(data.getRiotIdTagline());
		name.addMouseListener(new NameLabelListener(player, currentSpgGame, isPlackListPlayer, name, currentRegion, this.detail));
		name.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 8, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(name, gridList.get(5));
		//段位
		JLabel rankLabel = new JLabel(MatchHistoryUtil.getRanked(data.getPuuid()));
		rankLabel.setForeground(Color.WHITE);
		jPanel.add(rankLabel, gridList.get(6));
		//评分
		DecimalFormat df = new DecimalFormat("0.0");
		JLabel score = new JLabel(df.format(scoreLevel.getScore()));
		score.setForeground(Color.WHITE);
		score.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 20, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(score, gridList.get(7));
		JLabel order = new JLabel("排名：" + (scoreLevel.getOrder()));
		order.setForeground(Color.WHITE);
		order.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 18, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(order, gridList.get(8));

		//KDA
		JLabel kda = new JLabel(data.getKills() + "/" + data.getDeaths() + "/" + data.getAssists());
		kda.setForeground(Color.WHITE);
		kda.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 20, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(kda, gridList.get(9));

		JLabel kdaScore = new JLabel(df.format(data.getDeaths() == 0 ? (data.getKills() + data.getAssists()) : (data.getKills() + data.getAssists()) / (double) data.getDeaths()));
		kdaScore.setForeground(Color.WHITE);
		kdaScore.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 20, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(kdaScore, gridList.get(10));

		//伤害
		JLabel damage = new JLabel("<html><body>造成伤害：" + data.getTotalDamageDealtToChampions() + "<br>" + "承受伤害：" + data.getTotalDamageTaken() + "<br>治疗血量：" + data.getTotalHeal() + "</body></html>");
		damage.setForeground(Color.WHITE);
		damage.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 10, FrameSetting.MATCH_HEIGHT / 15));
		jPanel.add(damage, gridList.get(11));

		//眼
		JLabel wardsPlaced = new JLabel("插眼数：" + data.getWardsPlaced());
		wardsPlaced.setForeground(Color.WHITE);
		wardsPlaced.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 15, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(wardsPlaced, gridList.get(12));
		//眼
		JLabel visionScore = new JLabel("视野得分：" + data.getVisionScore());
		visionScore.setForeground(Color.WHITE);
		wardsPlaced.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 15, FrameSetting.MATCH_HEIGHT / 50));
		jPanel.add(visionScore, gridList.get(13));

		//补兵
		JLabel minionsKilled = new JLabel("<html><body>获得金币：" + data.getGoldEarned() + "<br>补兵数：" + data.getTotalMinionsKilled() + "<br>控制时间" + data.getTimeCCingOthers() + "秒</body></html>");
		minionsKilled.setForeground(Color.WHITE);
		minionsKilled.setPreferredSize(new Dimension(FrameSetting.MATCH_WIDTH / 10, FrameSetting.MATCH_HEIGHT / 15));
		jPanel.add(minionsKilled, gridList.get(14));

		//物品
		JLabel item0 = new JLabel();
		item0.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem0(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item0, gridList.get(15));
		JLabel item1 = new JLabel();
		item1.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem1(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item1, gridList.get(16));
		JLabel item2 = new JLabel();
		item2.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem2(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item2, gridList.get(17));
		JLabel item3 = new JLabel();
		item3.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem3(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item3, gridList.get(18));
		JLabel item4 = new JLabel();
		item4.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem4(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item4, gridList.get(19));
		JLabel item5 = new JLabel();
		item5.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem5(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item5, gridList.get(20));
		JLabel item6 = new JLabel();
		item6.setIcon(MatchHistoryUtil.getItemImageIcon(data.getItem6(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
		jPanel.add(item6, gridList.get(21));
		return jPanel;
	}

	/**
	 * 玩家数据布局集合
	 */
	private ArrayList<GridBagConstraints> gridList() {
		ArrayList<GridBagConstraints> arrayList = new ArrayList<>();
		//第一行 英雄头像
		arrayList.add(0, new GridBagConstraints(
				// 第(0,0)个格子
				0, 0,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				0, 2,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//召唤师技能
		arrayList.add(1, new GridBagConstraints(
				// 第(0,0)个格子
				2, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(5, 0, 5, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(2, new GridBagConstraints(
				// 第(0,0)个格子
				2, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(5, 0, 5, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//基石符文
		arrayList.add(3, new GridBagConstraints(
				// 第(0,0)个格子
				3, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(4, new GridBagConstraints(
				// 第(0,0)个格子
				3, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//姓名
		arrayList.add(5, new GridBagConstraints(
				// 第(0,0)个格子
				4, 0,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 5),
				// 增加组件的首选宽度和高度
				0, 0));
		//段位
		arrayList.add(6, new GridBagConstraints(
				// 第(0,0)个格子
				4, 1,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//评分
		arrayList.add(7, new GridBagConstraints(
				// 第(0,0)个格子
				6, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));

		arrayList.add(8, new GridBagConstraints(
				// 第(0,0)个格子
				6, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//KDA
		arrayList.add(9, new GridBagConstraints(
				// 第(0,0)个格子
				7, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(10, new GridBagConstraints(
				// 第(0,0)个格子
				7, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//伤害
		arrayList.add(11, new GridBagConstraints(
				// 第(0,0)个格子
				8, 0,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//眼
		arrayList.add(12, new GridBagConstraints(
				// 第(0,0)个格子
				10, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(13, new GridBagConstraints(
				// 第(0,0)个格子
				10, 1,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//补兵
		arrayList.add(14, new GridBagConstraints(
				// 第(0,0)个格子
				12, 0,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//道具
		arrayList.add(15, new GridBagConstraints(
				// 第(0,0)个格子
				14, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(16, new GridBagConstraints(
				// 第(0,0)个格子
				15, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(17, new GridBagConstraints(
				// 第(0,0)个格子
				16, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(18, new GridBagConstraints(
				// 第(0,0)个格子
				17, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(19, new GridBagConstraints(
				// 第(0,0)个格子
				18, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(20, new GridBagConstraints(
				// 第(0,0)个格子
				19, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		arrayList.add(21, new GridBagConstraints(
				// 第(0,0)个格子
				20, 0,
				// 占2列,占2行
				1, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		return arrayList;
	}

	/**
	 * 标题布局集合
	 */
	private ArrayList<GridBagConstraints> titleGridList() {
		ArrayList<GridBagConstraints> arrayList = new ArrayList<>();
		//红蓝方
		arrayList.add(0, new GridBagConstraints(
				// 第(0,0)个格子
				0, 0,
				// 占2列,占2行
				4, 1,
				//横向占100%长度,纵向占100%长度
				4.6, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0));
		//评分
		arrayList.add(1, new GridBagConstraints(
				// 第(0,0)个格子
				4, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 25, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0));
		//KDA
		arrayList.add(2, new GridBagConstraints(
				// 第(0,0)个格子
				5, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 5, 0, 5),
				// 增加组件的首选宽度和高度
				0, 0));
		//伤害
		arrayList.add(3, new GridBagConstraints(
				// 第(0,0)个格子
				6, 0,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				2.2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0));
		//眼
		arrayList.add(4, new GridBagConstraints(
				// 第(0,0)个格子
				8, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				1.2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 10, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//补兵数
		arrayList.add(5, new GridBagConstraints(
				// 第(0,0)个格子
				9, 0,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				2.2, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 10, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		//装备
		arrayList.add(6, new GridBagConstraints(
				// 第(0,0)个格子
				11, 0,
				// 占2列,占2行
				6, 1,
				//横向占100%长度,纵向占100%长度
				6, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 0, 0, 0),
				// 增加组件的首选宽度和高度
				0, 0));
		return arrayList;
	}

	public void close() {
		FrameInnerCache.sgpRecordPanel.setPlayerPanelVisible(true);
		FrameInnerCache.sgpRecordPanel.panelContainer.remove(FrameInnerCache.sgpRecordPanel.historyDetail);
		FrameInnerCache.sgpRecordPanel.showAllComponent();
		FrameInnerCache.sgpRecordPanel.isShowDetail = false;
	}
}

@Slf4j
class NameLabelListener extends MouseAdapter {
	private Player player;
	private SpgGames spgGames;
	private boolean isPlackListPlayer;
	private JLabel label;
	private String currentRegion;
	private HistoryDetail detail;

	public NameLabelListener(Player player, SpgGames spgGames, boolean isPlackListPlayer, JLabel label, String currentRegion, HistoryDetail detail) {
		this.player = player;
		this.spgGames = spgGames;
		this.isPlackListPlayer = isPlackListPlayer;
		this.label = label;
		this.currentRegion = currentRegion;
		this.detail = detail;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 鼠标移入时更改鼠标样式
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 鼠标移出时恢复默认样式
		label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() instanceof JLabel jLabel) {
			// 创建弹出菜单
			JPopupMenu menu = new JPopupMenu();
			JMenuItem item1 = new JMenuItem("复制");
			item1.addActionListener(e1 -> {
				StringSelection stringSelection = new StringSelection(jLabel.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			});
			menu.add(item1);

			//如果是拉黑的用户
			if (isPlackListPlayer) {
				boolean isThisGame = AppCache.settingPersistence.getBlacklistPlayers().get(player.getPuuid()).contains(spgGames.getGameId().toString());
				//如果本场已拉黑
				if (isThisGame) {
					BlackListBO blackList = getBlackList();
					if (blackList != null) {
						JMenuItem item2 = new JMenuItem("查看黑名单备注");
						//同一局多个拉黑用户
						int index = 0;
						BlackListPlayer blackListPlayer = null;
						for (; index < blackList.getBlackListPlayers().size(); index++) {
							if (blackList.getBlackListPlayers().get(index).getPuuid().equals(player.getPuuid())) {
								blackListPlayer = blackList.getBlackListPlayers().get(index);
							}
						}
						BlackListPlayer finalBlackListPlayer = blackListPlayer;
						item2.addActionListener(e2 -> {
							if (finalBlackListPlayer.getRemark() == null || finalBlackListPlayer.getRemark().isEmpty()) {
								JOptionPane.showMessageDialog(null, "黑名单备注为空", "黑名单备注", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, finalBlackListPlayer.getRemark(), "黑名单备注", JOptionPane.INFORMATION_MESSAGE);
							}
						});
						menu.add(item2);

						JMenuItem item3 = new JMenuItem("修改黑名单备注");
						item3.addActionListener(e3 -> {
							String info = JOptionPane.showInputDialog(null, "请输入备注：", "黑名单备注", JOptionPane.PLAIN_MESSAGE);
							finalBlackListPlayer.setRemark(info);
							updateBlackList(blackList);
							detail.close();
						});
						menu.add(item3);

						JMenuItem item4 = new JMenuItem("刑满释放");
						item4.addActionListener(e4 -> {
							removeBlackList(finalBlackListPlayer, blackList);
							detail.close();
						});
						menu.add(item4);
					}
				}
			} else {
				JMenuItem item5 = new JMenuItem("加入黑名单");
				item5.addActionListener(e5 -> {
					if (player.getPuuid().equals(GameDataCache.me.getPuuid())) {
						JOptionPane.showMessageDialog(null, "不能将自己加入黑名单", "警告", JOptionPane.ERROR_MESSAGE);
					} else {
						String info = JOptionPane.showInputDialog(null, "请输入备注：", "黑名单备注", JOptionPane.INFORMATION_MESSAGE);
						BlackListPlayer blackListPlayer = BeanUtil.toBean(player, BlackListPlayer.class);
						blackListPlayer.setRemark(info);
						addBlackList(blackListPlayer);
						detail.close();
					}
				});
				menu.add(item5);
			}

			menu.show(e.getComponent(), e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof JLabel jLabel) {
			try {
				List<String> list = new ArrayList<String>();
				list.add(jLabel.getText());
				List<Player> summonerInfoBOList = AppCache.api.getV2InfoByNameList(list);
				Player playerDetail = summonerInfoBOList.get(0);
				List<SpgProductsMatchHistoryBO> data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(currentRegion, playerDetail.getPuuid(), 0, FrameSetting.PAGE_SIZE);
				FrameInnerCache.sgpRecordPanel.setPlayerPanelVisible(true);
				FrameInnerCache.sgpRecordPanel.resetIndex();
				FrameInnerCache.sgpRecordPanel.setData(data, playerDetail.getPuuid(), BeanUtil.toBean(playerDetail, MePlayer.class));
				FrameInnerCache.sgpRecordPanel.showAllComponent();
			} catch (IOException ex) {
				log.error("查询召唤师:" + jLabel.getName() + "错误");
			}
		}
	}

	private void removeBlackList(BlackListPlayer player, BlackListBO blackListBO) {
		AppCache.settingPersistence.getBlacklistPlayers().remove(player.getPuuid());
		FrameConfigUtil.save();
		List<BlackListPlayer> list = blackListBO.getBlackListPlayers().stream().filter(item -> !player.getPuuid().equals(item.getPuuid())).toList();
		if (list.isEmpty()) {
			FileUtil.del(new File(GameConstant.BLACK_LIST_FILE + spgGames.getGameId() + ".json"));
		} else {
			blackListBO.setBlackListPlayers(list);
			updateBlackList(blackListBO);
		}
	}

	private void addBlackList(BlackListPlayer player) {
		File file = new File(GameConstant.BLACK_LIST_FILE + spgGames.getGameId() + ".json");
		List<BlackListPlayer> players = new ArrayList<BlackListPlayer>();
		if (FileUtil.exist(file)) {
			String s = FileUtil.readUtf8String(file);
			BlackListBO blackListBO = JSON.parseObject(s, BlackListBO.class);
			if (!AppCache.settingPersistence.getBlacklistPlayers().containsKey(player.getPuuid())) {
				AppCache.settingPersistence.getBlacklistPlayers().put(player.getPuuid(), spgGames.getGameId().toString());
				blackListBO.getBlackListPlayers().add(player);
			} else {
				AppCache.settingPersistence.getBlacklistPlayers().compute(player.getPuuid(), (k, gameIdList) -> "," + gameIdList);
			}
			String jsonString = JSONObject.toJSONString(blackListBO);
			FileUtil.writeUtf8String(jsonString, file);
			FrameConfigUtil.save();
		} else {
			String mePuuid = GameDataCache.me.getPuuid();
			boolean flag = spgGames.getParticipants().stream().anyMatch(item -> item.getPuuid().equals(mePuuid));
			if (flag) {
				players.add(player);
				boolean win = spgGames.getParticipants().stream().filter(item -> item.getPuuid().equals(mePuuid)).findFirst().get().isWin();
				BlackListBO blackListBO = new BlackListBO(mePuuid, players, spgGames, win);
				String jsonString = JSONObject.toJSONString(blackListBO);
				AppCache.settingPersistence.getBlacklistPlayers().put(player.getPuuid(), spgGames.getGameId().toString());
				FrameConfigUtil.save();
				FileUtil.writeUtf8String(jsonString, file);
			} else {
				FrameTipUtil.errorMsg("您不在对局中");
			}
		}
	}

	private void updateBlackList(BlackListBO blackListBO) {
		String jsonString = JSONObject.toJSONString(blackListBO);
		FileUtil.writeUtf8String(jsonString, new File(GameConstant.BLACK_LIST_FILE + spgGames.getGameId() + ".json"));
	}

	private BlackListBO getBlackList() {
		File file = new File(GameConstant.BLACK_LIST_FILE + spgGames.getGameId() + ".json");
		if (FileUtil.exist(file)) {
			String jsonString = FileUtil.readUtf8String(file);
			return JSON.parseObject(jsonString, BlackListBO.class);
		} else {
			return null;
		}
	}
}