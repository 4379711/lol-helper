package helper.frame.panel.history;

import helper.bo.BlackListPlayer;
import helper.bo.SpgGames;
import helper.bo.SpgParticipants;
import helper.bo.SpgProductsMatchHistoryBO;
import helper.constant.GameConstant;
import helper.enums.ImageEnum;
import helper.frame.utils.BlackListUtil;
import helper.frame.utils.MatchHistoryUtil;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author WuYi
 */
public class BlackListAddPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(BlackListAddPanel.class);
    private static String mePuuid;
    private static SpgGames spgGames;

    public BlackListAddPanel(SpgProductsMatchHistoryBO bo, String puuid) {
        mePuuid = puuid;
        spgGames = bo.getJson();
        ArrayList<SpgParticipants> teamOne = new ArrayList<>();
        ArrayList<SpgParticipants> teamTwo = new ArrayList<>();
        boolean isTeamOne = false;
        for (SpgParticipants user : bo.getJson().getParticipants()) {
            if (user.getPuuid().equals(puuid)) {
                isTeamOne = user.getTeamId() == GameConstant.TEAM_ONE;
            } else if (user.getTeamId() == GameConstant.TEAM_ONE) {
                teamOne.add(user);
            } else if (user.getTeamId() == GameConstant.TEAM_TWO) {
                teamTwo.add(user);
            }
        }

        // 创建一个包含两个用户组的面板
        JPanel panel = createCombinedUserPanel(teamOne, teamTwo, isTeamOne);

        this.add(panel);
    }

    private static JPanel createCombinedUserPanel(ArrayList<SpgParticipants> teamOne, ArrayList<SpgParticipants> teamTwo, boolean isTeamOne) {

        // 使用比例来定义列宽，例如，头像占 1 份，名字占 2 份，拉黑输入框占 3 份，拉黑按钮占 1 份
        JPanel panel = new JPanel(new MigLayout("wrap 4",
                "[grow 1][grow 2][grow 3][grow 1]", "[]"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加第一组用户组的标题
        JLabel groupOneTitle = new JLabel();
        if (isTeamOne) {
            groupOneTitle.setText("我方");
        } else {
            groupOneTitle.setText("敌方");
        }
        panel.add(groupOneTitle, "span 4, align center, wrap");

        // 添加第一组用户
        for (SpgParticipants user : teamOne) {
            addUserComponents(panel, user);
        }

        // 添加第二组用户组的标题
        JLabel groupTwoTitle = new JLabel();
        if (isTeamOne) {
            groupTwoTitle.setText("敌方");
        } else {
            groupTwoTitle.setText("我方");
        }
        panel.add(groupTwoTitle, "span 4, align center, wrap");

        // 添加第二组用户
        for (SpgParticipants user : teamTwo) {
            addUserComponents(panel, user);
        }

        return panel;
    }

    private static void addUserComponents(JPanel panel, SpgParticipants user) {
        // 头像，使用一个空的 JLabel 并设置边框，模拟一个方框头像
        JLabel avatarLabel = new JLabel();
        ImageIcon championIcon = MatchHistoryUtil.getChampionIcon(user.getChampionId(), 25, 25, ImageEnum.SQUARE);
        avatarLabel.setIcon(championIcon);
        avatarLabel.setPreferredSize(new Dimension(25, 25));
        avatarLabel.setMinimumSize(new Dimension(25, 25));
        avatarLabel.setMaximumSize(new Dimension(25, 25));
        // 姓名
        JLabel nameLabel = new JLabel(user.getRiotIdGameName());
        JButton blockButton = new JButton("拉黑");
        blockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String info = JOptionPane.showInputDialog(null, "请输入备注：(可为空)", "黑名单备注", JOptionPane.INFORMATION_MESSAGE);
                BlackListPlayer blackListPlayer = new BlackListPlayer();
                blackListPlayer.setPuuid(user.getPuuid());
                blackListPlayer.setGameName(user.getRiotIdGameName());
                blackListPlayer.setTagLine(user.getRiotIdTagline());
                blackListPlayer.setRemark(info);
                try {
                    BlackListUtil.addBlackListPlayer(blackListPlayer, spgGames);
                } catch (RuntimeException ex) {
                    log.error("操作失败：{}", ex.getMessage());
                }

            }
        });

        // 将组件添加到用户组面板
        panel.add(avatarLabel);
        panel.add(nameLabel);
        panel.add(blockButton, "wrap");
    }
}