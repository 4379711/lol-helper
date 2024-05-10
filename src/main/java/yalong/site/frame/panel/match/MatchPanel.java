package yalong.site.frame.panel.match;

import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.json.entity.match.Participants;
import yalong.site.json.entity.match.Stats;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MatchPanel extends JScrollPane {

    public MatchPanel() {
        this.setName("查询");
        // 设置透明
        this.setOpaque(false);
        this.setBorder(null);
        this.getViewport().setOpaque(false);
        this.setAutoscrolls(true);
        this.setMaximumSize(new Dimension(FrameSetting.BIG_WIDTH, FrameSetting.BIG_HEIGHT));
        FrameInnerCache.matchPanel = this;
    }

    public static MatchPanel builder() {
        return new MatchPanel();
    }

    public void setData(ProductsMatchHistoryBO bo) {
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        for (int i = bo.getGames().getGameIndexBegin(); i < bo.getGames().getGameIndexEnd(); i++) {
            HistoryLine panel = new HistoryLine();
            //获取本人的对局信息
            Participants me = bo.getGames().getGames().get(i).getParticipants().get(0);
            panel.setImageIcons(getChampionIcon(me), getSummonerSpellIcons(me));
            panel.setItemIcons(getItemImageIcons(me.getStats()));
            panel.setText("测试");
            panelContainer.add(panel);
        }
        this.setViewportView(panelContainer);
    }

    /**
     * 获取指定玩家的英雄图标
     *
     * @param participants 玩家对局信息
     * @return 图片
     */

    private ImageIcon getChampionIcon(Participants participants) {
        ImageIcon imageIcon;
        try {
            imageIcon = new ImageIcon(AppCache.api.getChampionIcons(participants.getChampionId()).getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            imageIcon = new ImageIcon();
        }
        return imageIcon;
    }

    /**
     * 获取召唤师技能图标
     *
     * @param participants 玩家对局信息
     * @return 召唤师技能图标
     */
    private ArrayList<ImageIcon> getSummonerSpellIcons(Participants participants) {
        ArrayList<ImageIcon> imageIcons = new ArrayList<>();
        imageIcons.add(getSummonerSpellImageIcon(participants.getSpell1Id()));
        imageIcons.add(getSummonerSpellImageIcon(participants.getSpell2Id()));

        return imageIcons;
    }

    private ArrayList<ImageIcon> getItemImageIcons(Stats stats) {
        ArrayList<ImageIcon> imageIcons = new ArrayList<>();
        imageIcons.add(getItemImageIcon(stats.getItem0()));
        imageIcons.add(getItemImageIcon(stats.getItem1()));
        imageIcons.add(getItemImageIcon(stats.getItem2()));
        imageIcons.add(getItemImageIcon(stats.getItem3()));
        imageIcons.add(getItemImageIcon(stats.getItem4()));
        imageIcons.add(getItemImageIcon(stats.getItem5()));
        imageIcons.add(getItemImageIcon(stats.getItem6()));
        return imageIcons;
    }

    private ImageIcon getItemImageIcon(Integer id) {
        if (id != 0) {
            try {
                String iconPath = AppCache.items.stream().filter(item -> item.getId().equals(id)).findFirst().get().getIconPath();
                return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                new ImageIcon(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
            }
        }
        return new ImageIcon(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
    }

    private ImageIcon getSummonerSpellImageIcon(Integer id) {
        if (id != 0) {
            try {
                String iconPath = AppCache.summonerSpellsList.stream().filter(item -> item.getId().equals(id)).findFirst().get().getIconPath();
                return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            } catch (IOException e) {
                new ImageIcon(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
            }
        }
        return new ImageIcon(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
    }
}
