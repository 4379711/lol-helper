package yalong.site.frame.panel.match;

import yalong.site.bo.GameMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameSetting;
import yalong.site.frame.bo.ScoreLevelBO;
import yalong.site.frame.constant.GameConstant;
import yalong.site.frame.panel.match.listener.NameLabelListener;
import yalong.site.frame.utils.MatchHistoryUtil;
import yalong.site.json.entity.match.ParticipantIdentities;
import yalong.site.json.entity.match.Participants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 战绩详情
 */

public class HistoryDetail extends JPanel {
    private JPanel detailPanelTeam1 = new JPanel();
    private JPanel detailPanelTeam2 = new JPanel();

    public HistoryDetail(GameMatchHistoryBO bo) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buildTeam1Title());
        ArrayList<ScoreLevelBO> scoreLevelList = MatchHistoryUtil.getScoreLevelList(bo.getParticipants());
        int index = 0;
        for (int i = 0; i < bo.getParticipantIdentities().size(); i++) {
            if (bo.getParticipantIdentities().get(i) != null && bo.getParticipants().get(i) != null && bo.getParticipants().get(i).getTeamId().equals(GameConstant.TEAM_ONE)) {
                this.add(buildTeamData(bo.getParticipantIdentities().get(i), bo.getParticipants().get(i), scoreLevelList.get(i)));
                index++;
            }
        }
        this.add(buildTeam2Title());
        for (int i = index; i < bo.getParticipantIdentities().size(); i++) {
            if (bo.getParticipantIdentities().get(i) != null && bo.getParticipants().get(i) != null && bo.getParticipants().get(i).getTeamId().equals(GameConstant.TEAM_TWO)) {
                this.add(buildTeamData(bo.getParticipantIdentities().get(i), bo.getParticipants().get(i), scoreLevelList.get(i)));
            }
        }
    }

    public static HistoryDetail build(GameMatchHistoryBO bo) throws IOException {
        return new HistoryDetail(bo);
    }

    private JPanel buildTeam1Title() {
        JPanel jPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        jPanel.setBackground(Color.GRAY);
        ArrayList<GridBagConstraints> grid = titleGridList();
        jPanel.add(new JLabel("红色方"), grid.get(0));
        jPanel.add(new JLabel("评分"), grid.get(1));
        jPanel.add(new JLabel("KDA"), grid.get(2));
        jPanel.add(new JLabel("伤害"), grid.get(3));
        jPanel.add(new JLabel("眼"), grid.get(4));
        jPanel.add(new JLabel("补兵数"), grid.get(5));
        jPanel.add(new JLabel("装备"), grid.get(6));
        return jPanel;
    }

    private JPanel buildTeam2Title() {
        JPanel jPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        jPanel.setBackground(Color.GRAY);
        ArrayList<GridBagConstraints> grid = titleGridList();
        jPanel.add(new JLabel("蓝色方"), grid.get(0));
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
     * @param playInfo     玩家信息
     * @param playGameInfo 玩家游戏信息
     * @return JPanel
     */
    private JPanel buildTeamData(ParticipantIdentities playInfo, Participants playGameInfo, ScoreLevelBO scoreLevel) throws IOException {
        JPanel jPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        jPanel.setLayout(layout);
        jPanel.setBackground(MatchHistoryUtil.getBackGroundColor(playGameInfo.getStats().getWin()));
        jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        ArrayList<GridBagConstraints> gridList = gridList();

        //英雄头像s
        JLabel ChampionIcon = new JLabel();
        ChampionIcon.setIcon(MatchHistoryUtil.getChampionIcon(playGameInfo.getChampionId(), FrameSetting.DETAIL_CHAMPION_ICON_SIZE, FrameSetting.DETAIL_CHAMPION_ICON_SIZE));

        jPanel.add(ChampionIcon, gridList.get(0));
        //召唤师技能
        JLabel spellIcon1 = new JLabel();
        spellIcon1.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(playGameInfo.getSpell1Id(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
        jPanel.add(spellIcon1, gridList.get(1));

        JLabel spellIcon2 = new JLabel();
        spellIcon2.setIcon(MatchHistoryUtil.getSummonerSpellImageIcon(playGameInfo.getSpell2Id(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
        jPanel.add(spellIcon2, gridList.get(2));

        //基石符文
        JLabel perkStyle1 = new JLabel();
        perkStyle1.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(playGameInfo.getStats().getPerkPrimaryStyle(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
        jPanel.add(perkStyle1, gridList.get(3));
        JLabel perkStyle2 = new JLabel();
        perkStyle2.setIcon(MatchHistoryUtil.getPerkStyleImageIcon(playGameInfo.getStats().getPerkSubStyle(), FrameSetting.DETAIL_SPELL_ICON_SIZE, FrameSetting.DETAIL_SPELL_ICON_SIZE));
        jPanel.add(perkStyle2, gridList.get(4));
        //姓名
        JLabel name = new JLabel(playInfo.getPlayer().getGameName() + "#" + playInfo.getPlayer().getTagLine());
        name.addMouseListener(new NameLabelListener());
        name.setPreferredSize(new Dimension(FrameSetting.WIDTH / 8, FrameSetting.HEIGHT / 50));
        jPanel.add(name, gridList.get(5));
        //段位
        jPanel.add(new JLabel(MatchHistoryUtil.getRanked(playInfo.getPlayer().getPuuid())), gridList.get(6));
        System.out.println(AppCache.api.getRank(playInfo.getPlayer().getPuuid()));
        //评分
        DecimalFormat df = new DecimalFormat("#.0");
        JLabel score = new JLabel(df.format(scoreLevel.getScore()));
        score.setPreferredSize(new Dimension(FrameSetting.WIDTH / 20, FrameSetting.HEIGHT / 50));
        jPanel.add(score, gridList.get(7));
        JLabel order = new JLabel("排名：" + (scoreLevel.getOrder()));
        score.setPreferredSize(new Dimension(FrameSetting.WIDTH / 20, FrameSetting.HEIGHT / 50));
        jPanel.add(order, gridList.get(8));


        //KDA
        JLabel kda = new JLabel(playGameInfo.getStats().getKills() + "/" + playGameInfo.getStats().getDeaths() + "/" + playGameInfo.getStats().getAssists());
        kda.setPreferredSize(new Dimension(FrameSetting.WIDTH / 20, FrameSetting.HEIGHT / 50));
        jPanel.add(kda, gridList.get(9));

        JLabel kdaScore = new JLabel(df.format(playGameInfo.getStats().getDeaths() == 0 ? (playGameInfo.getStats().getKills() + playGameInfo.getStats().getAssists()) : (playGameInfo.getStats().getKills() + playGameInfo.getStats().getAssists()) / (double) playGameInfo.getStats().getDeaths()));
        kda.setPreferredSize(new Dimension(FrameSetting.WIDTH / 20, FrameSetting.HEIGHT / 50));
        jPanel.add(kdaScore, gridList.get(10));

        //伤害
        JLabel damage = new JLabel("<html><body>造成伤害：" + playGameInfo.getStats().getTotalDamageDealtToChampions() + "<br>" + "承受伤害：" + playGameInfo.getStats().getTotalDamageTaken() + "<br>治疗血量：" + playGameInfo.getStats().getTotalHeal() + "</body></html>");
        damage.setPreferredSize(new Dimension(FrameSetting.WIDTH / 10, FrameSetting.HEIGHT / 15));
        jPanel.add(damage, gridList.get(11));

        //眼
        JLabel wardsPlaced = new JLabel("插眼数：" + playGameInfo.getStats().getWardsPlaced());
        wardsPlaced.setPreferredSize(new Dimension(FrameSetting.WIDTH / 15, FrameSetting.HEIGHT / 50));
        jPanel.add(wardsPlaced, gridList.get(12));
        //眼
        JLabel visionScore = new JLabel("视野得分：" + playGameInfo.getStats().getVisionScore());
        wardsPlaced.setPreferredSize(new Dimension(FrameSetting.WIDTH / 15, FrameSetting.HEIGHT / 50));
        jPanel.add(visionScore, gridList.get(13));

        //补兵
        JLabel minionsKilled = new JLabel("<html><body>获得金币：" + playGameInfo.getStats().getGoldEarned() + "<br>补兵数：" + playGameInfo.getStats().getTotalMinionsKilled() + "<br>控制时间" + playGameInfo.getStats().getTimeCCingOthers() + "秒</body></html>");
        minionsKilled.setPreferredSize(new Dimension(FrameSetting.WIDTH / 10, FrameSetting.HEIGHT / 15));
        jPanel.add(minionsKilled, gridList.get(14));

        //物品
        JLabel item0 = new JLabel();
        item0.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem0(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item0, gridList.get(15));
        JLabel item1 = new JLabel();
        item1.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem1(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item1, gridList.get(16));
        JLabel item2 = new JLabel();
        item2.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem2(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item2, gridList.get(17));
        JLabel item3 = new JLabel();
        item3.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem3(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item3, gridList.get(18));
        JLabel item4 = new JLabel();
        item4.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem4(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item4, gridList.get(19));
        JLabel item5 = new JLabel();
        item5.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem5(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
        jPanel.add(item5, gridList.get(20));
        JLabel item6 = new JLabel();
        item6.setIcon(MatchHistoryUtil.getItemImageIcon(playGameInfo.getStats().getItem6(), FrameSetting.DETAIL_ITEM_ICON_SIZE, FrameSetting.DETAIL_ITEM_ICON_SIZE));
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
}
