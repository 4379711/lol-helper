package helper.frame.panel.history;

import helper.bo.MePlayer;
import helper.bo.RerollPoints;
import helper.bo.SGPRank;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.constant.ColorConstant;
import helper.enums.RankEnum;
import helper.enums.RankModeEnum;
import helper.exception.NoSummonerException;
import helper.frame.utils.MatchHistoryUtil;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @author WuYi
 */
public class PlayerDetail extends JPanel {

    public PlayerDetail() {
        this.setBackground(ColorConstant.DARK_THREE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


    public void setData(MePlayer player) throws IOException, NoSummonerException {
        build(player);
    }

    @SneakyThrows
    public void build(MePlayer player) {
        this.removeAll();
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(MatchHistoryUtil.getSummonerIcon(player.getProfileIconId(), 100, 100));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(iconLabel);

        JLabel nameLabel = new JLabel();
        nameLabel.setText(player.getGameName() + "#" + player.getTagLine());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(nameLabel);

        JLabel levelLabel = new JLabel();
        levelLabel.setText("Lv：" + player.getSummonerLevel());
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(levelLabel);
        if (player.getPrivacy().equalsIgnoreCase("private")) {
            JLabel privacyLabel = new JLabel();
            privacyLabel.setForeground(Color.RED);
            privacyLabel.setText("战绩未公开");
            privacyLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.add(privacyLabel);
        }
        //如果有骰子证明是本人
        if (player.getRerollPoints() != null) {
            RerollPoints rerollPoints = player.getRerollPoints();
            JLabel rerollLabel = new JLabel();
            JLabel rerollNeedLabel = new JLabel();
            rerollLabel.setText("大乱斗骰子个数：" + rerollPoints.getNumberOfRolls());
            rerollLabel.setForeground(Color.WHITE);
            rerollNeedLabel.setText("下个骰子所需点数：" + rerollPoints.getPointsToReroll());
            rerollNeedLabel.setForeground(Color.WHITE);
            rerollLabel.setAlignmentX(CENTER_ALIGNMENT);
            rerollNeedLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.add(rerollLabel);
            this.add(rerollNeedLabel);
        }
        List<SGPRank> ranks = AppCache.sgpApi.getRankedStatsListByPuuid(player.getPuuid(), FrameInnerCache.sgpRecordPanel.currentRegion);
        //跨区查不了段位不显示
        if (!ranks.isEmpty()) {
            Dimension titleSize = new Dimension(80, 40);
            JPanel rankTitle = new JPanel();
            rankTitle.setLayout(new BoxLayout(rankTitle, BoxLayout.X_AXIS));
            rankTitle.setBackground(ColorConstant.DARK_THREE);

            JLabel titleOne = new JLabel();
            titleOne.setText("模式");

            JLabel titleTwo = new JLabel();
            titleTwo.setText("段位");

            JLabel titleFour = new JLabel();
            titleFour.setText("胜场");

            JLabel titleFive = new JLabel();
            titleFive.setText("败场");

            JLabel titleSix = new JLabel();
            titleSix.setText("上赛季段位");
            rankTitle.add(titleOne);
            rankTitle.add(titleTwo);
            rankTitle.add(titleFour);
            rankTitle.add(titleFive);
            rankTitle.add(titleSix);
            for (Component component : rankTitle.getComponents()) {
                component.setSize(titleSize);
                component.setPreferredSize(titleSize);
                component.setMinimumSize(titleSize);
                component.setMaximumSize(titleSize);
                component.setBackground(ColorConstant.DARK_THREE);
                component.setForeground(Color.white);
            }
            this.add(rankTitle);


            for (SGPRank rank : ranks) {
            JPanel rankPanel = new JPanel();
            rankPanel.setBackground(ColorConstant.DARK_THREE);
            rankPanel.setLayout(new BoxLayout(rankPanel, BoxLayout.X_AXIS));

            JLabel rankTypeLabel = new JLabel();
            RankModeEnum rankModeEnum = RankModeEnum.valueOf(RankModeEnum.class, rank.getQueueType());
            rankTypeLabel.setText(rankModeEnum.getRankName());

            JLabel tierLabel = new JLabel();
            tierLabel.setText((RankEnum.valueOf(rank.getTier() == null ? "NONE" : rank.getTier()).getName())
                    + (rank.getRank() == null ? "" : rank.getRank()));

            JLabel rankWinsLabel = new JLabel();
            rankWinsLabel.setText(String.valueOf(rank.getWins()));

            JLabel rankLossesLabel = new JLabel();
            rankLossesLabel.setText(String.valueOf(rank.getLosses()));

            JLabel preTierLabel = new JLabel();
            preTierLabel.setText((RankEnum.valueOf(rank.getPreviousSeasonEndTier() == null ? "NONE" : rank.getPreviousSeasonEndTier()).getName())
                    + (rank.getPreviousSeasonEndRank() == null ? "" : rank.getPreviousSeasonEndRank()));

            rankPanel.add(rankTypeLabel);
            rankPanel.add(tierLabel);
            rankPanel.add(rankWinsLabel);
            rankPanel.add(rankLossesLabel);
            rankPanel.add(preTierLabel);
                for (Component component : rankPanel.getComponents()) {
                component.setSize(titleSize);
                component.setPreferredSize(titleSize);
                component.setMinimumSize(titleSize);
                component.setMaximumSize(titleSize);
                component.setForeground(Color.white);
            }
            this.add(rankPanel);
            }
            this.repaint();
        } else {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
            jPanel.setBackground(ColorConstant.DARK_THREE);

            JLabel rankLabel = new JLabel();
            rankLabel.setText("跨区无法查看段位信息");
            rankLabel.setForeground(Color.WHITE);
            jPanel.add(Box.createHorizontalGlue());
            jPanel.add(rankLabel);
            jPanel.add(Box.createHorizontalGlue());
            this.add(jPanel);
            this.add(Box.createVerticalGlue());
        }

    }
}
