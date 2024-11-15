package helper.frame.panel.history;

import cn.hutool.core.date.DateUtil;
import helper.bo.*;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.enums.ImageEnum;
import helper.enums.RegionEnum;
import helper.exception.NoSummonerException;
import helper.frame.bo.ItemBO;
import helper.frame.constant.ColorConstant;
import helper.frame.constant.GameConstant;
import helper.frame.panel.base.SearchTextField;
import helper.frame.utils.FrameTipUtil;
import helper.frame.utils.MatchHistoryUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WuYi
 */
@Slf4j
public class SGPRecordPanel extends JPanel {
    private final JPanel mainPanel = new JPanel();
    private final JPanel playerPanel = new JPanel();
    /**
     * 筛选容器
     */
    private final JPanel choosePanel = new JPanel();
    private final JButton backButton = new JButton("回到自己");
    private final JLabel regionNameLabel = new JLabel();
    private final JComboBox<ItemBO> selectQueueBox = new JComboBox<>();
    private final PlayerDetail playerDetail = new PlayerDetail();
    /**
     * 翻页容器
     */
    private final JPanel panelIndex = new JPanel();
    private final Label labelIndex = new Label();
    private final JButton buttonPrevious = new JButton();
    private final JButton buttonNext = new JButton();
    /**
     * 搜索容器
     */
    private final JComboBox<ItemBO> selectRegionBox = new JComboBox<ItemBO>();
    private final JPanel searchPanel = new JPanel();
    private final SearchTextField searchText = new SearchTextField(40);
    private final JButton searchButton = new JButton("搜索");
    public HistoryDetail historyDetail = null;
    /**
     * 是否打开详情页
     */
    public boolean isShowDetail = false;
    /**
     * 战绩数据容器
     */
    public JPanel panelContainer = new JPanel();
    public String currentId;
    public String currentRegion = GameDataCache.leagueClient.getRegion();
    private int index = 1;

    public SGPRecordPanel() {
        this.setName("查询战绩");
        FrameInnerCache.sgpRecordPanel = this;
        this.setBgColor(this, ColorConstant.DARK_THREE);

        this.setBorder(null);
        this.setAutoscrolls(true);
        this.setSize(new Dimension(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT));

        this.mainPanel.setLayout((new BoxLayout(mainPanel, BoxLayout.X_AXIS)));
        this.mainPanel.setBackground(ColorConstant.DARK_THREE);

        this.choosePanel.setLayout((new BoxLayout(choosePanel, BoxLayout.X_AXIS)));
        this.regionNameLabel.setText(RegionEnum.regionTypeById(GameDataCache.leagueClient.getRegion()));
        this.regionNameLabel.setForeground(Color.WHITE);
        this.choosePanel.setBackground(ColorConstant.DARK_THREE);
        this.backButton.setText("回到自己");
        for (GameQueue gameQueue : GameDataCache.selectGameQueueList.values()) {
            selectQueueBox.addItem(new ItemBO(gameQueue.getId().toString(), gameQueue.getName()));
        }
        this.selectQueueBox.setPreferredSize(new Dimension(150, 25));
        this.selectQueueBox.setPreferredSize(new Dimension(150, 25));
        this.selectQueueBox.setMaximumSize(new Dimension(150, 25));
        this.selectQueueBox.setMinimumSize(new Dimension(150, 25));

        this.choosePanel.add(backButton);
        this.choosePanel.add(Box.createHorizontalGlue());
        this.choosePanel.add(regionNameLabel);
        this.choosePanel.add(Box.createHorizontalGlue());
        this.choosePanel.add(selectQueueBox);

        this.playerPanel.setBackground(ColorConstant.DARK_THREE);
        this.playerPanel.setAlignmentX(CENTER_ALIGNMENT);

        this.panelContainer.setLayout((new BoxLayout(panelContainer, BoxLayout.Y_AXIS)));
        this.panelContainer.setBackground(Color.GRAY);

        for (RegionEnum value : RegionEnum.values()) {
            selectRegionBox.addItem(new ItemBO(value.getRegionId(), value.getRegionName()));
        }

        this.selectRegionBox.setPreferredSize(new Dimension(100, 25));
        this.selectRegionBox.setPreferredSize(new Dimension(100, 25));
        this.selectRegionBox.setMaximumSize(new Dimension(100, 25));
        this.selectRegionBox.setMinimumSize(new Dimension(100, 25));

        this.searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        this.searchPanel.setAlignmentY(TOP_ALIGNMENT);
        this.searchPanel.setPreferredSize(new Dimension(320, 20));
        this.searchText.setPreferredSize(new Dimension(250, 20));
        this.searchText.setMaximumSize(new Dimension(250, 20));
        this.searchText.setMinimumSize(new Dimension(250, 20));
        this.searchPanel.add(this.selectRegionBox);
        this.searchPanel.add(this.searchText);
        this.searchPanel.add(this.searchButton);
        this.searchPanel.setBackground(ColorConstant.DARK_THREE);


        this.panelIndex.setLayout((new BoxLayout(panelIndex, BoxLayout.X_AXIS)));
        this.panelIndex.setBackground(ColorConstant.DARK_THREE);
        this.buttonPrevious.setPreferredSize(new Dimension(80, 50));
        this.buttonNext.setPreferredSize(new Dimension(80, 50));
        this.buttonPrevious.setText("上一页");
        this.labelIndex.setText(Integer.toString(index));
        this.labelIndex.setForeground(Color.white);
        this.buttonNext.setText("下一页");
        //上一页鼠标事件
        this.buttonPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index > 1) {
                    try {
                        ItemBO item = (ItemBO) selectQueueBox.getSelectedItem();
                        index--;
                        List<SpgProductsMatchHistoryBO> sgpRecord;
                        if ("-1".equals(item.getValue())) {
                            sgpRecord = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), currentId, (index - 1) * FrameSetting.PAGE_SIZE, FrameSetting.PAGE_SIZE);
                        } else {
                            sgpRecord = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), currentId, (index - 1) * FrameSetting.PAGE_SIZE, FrameSetting.PAGE_SIZE, "q_" + item.getValue());
                        }
                        setData(sgpRecord, currentId, null);
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
                        ItemBO item = (ItemBO) selectQueueBox.getSelectedItem();
                        index++;
                        List<SpgProductsMatchHistoryBO> sgpRecord;
                        if ("-1".equals(item.getValue())) {
                            sgpRecord = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), currentId, (index - 1) * FrameSetting.PAGE_SIZE, FrameSetting.PAGE_SIZE);
                        } else {
                            sgpRecord = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), currentId, (index - 1) * FrameSetting.PAGE_SIZE, FrameSetting.PAGE_SIZE, "q_" + item.getValue());
                        }
                        setData(sgpRecord, currentId, null);
                    } catch (IOException ex) {
                        resetIndex();
                        log.error("查询战绩错误" + ex.getMessage());
                    }
                }
            }
        });

        this.searchButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ItemBO selectedItem = (ItemBO) selectRegionBox.getSelectedItem();
                String region = selectedItem.getValue();
                String text = searchText.getText();
                String[] split = text.split("#");
                try {
                    SummonerAlias summonerAlias = AppCache.sgpApi.getSummerByName(split[0], split[1]);
                    SgpSummonerInfoBo sgpSummonerInfoBo = AppCache.sgpApi.getSummerInfoByPuuid(region, summonerAlias.getPuuid());
                    if (sgpSummonerInfoBo != null) {
                        currentRegion = region;
                        List<SpgProductsMatchHistoryBO> data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(region, summonerAlias.getPuuid(), 0, FrameSetting.PAGE_SIZE);
                        resetIndex();
                        MePlayer mePlayer = new MePlayer();
                        mePlayer.setGameName(summonerAlias.getGameName());
                        mePlayer.setTagLine(summonerAlias.getTagLine());
                        mePlayer.setPuuid(sgpSummonerInfoBo.getPuuid());
                        mePlayer.setPrivacy(sgpSummonerInfoBo.getPrivacy());
                        mePlayer.setProfileIconId(sgpSummonerInfoBo.getProfileIconId());
                        mePlayer.setSummonerLevel(sgpSummonerInfoBo.getLevel());
                        setData(data, summonerAlias.getPuuid(), mePlayer);
                    } else {
                        FrameTipUtil.errorOccur("未找到" + selectedItem.getDisplayValue() + "的" + text);
                    }
                } catch (Exception ex) {
                    FrameTipUtil.errorOccur("查询召唤师" + text + "错误");
                }
            }
        });
        this.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentRegion = GameDataCache.leagueClient.getRegion();
                try {
                    ItemBO item = (ItemBO) selectQueueBox.getSelectedItem();
                    List<SpgProductsMatchHistoryBO> data;
                    if ("-1".equals(item.getValue())) {
                        data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(currentRegion, GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE);
                    } else {
                        data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(currentRegion, GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE, "q_" + item.getDisplayValue());
                    }
                    setData(data, GameDataCache.me.getPuuid(), GameDataCache.me);
                    resetIndex();
                } catch (IOException ex) {
                    log.error("设置本人战绩错误" + ex.getMessage());
                }
            }
        });

        selectQueueBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    ItemBO item = (ItemBO) selectQueueBox.getSelectedItem();
                    e.getItem();
                    List<SpgProductsMatchHistoryBO> data;
                    if ("-1".equals(item.getValue())) {
                        data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(currentRegion, GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE);
                    } else {
                        data = AppCache.sgpApi.getProductsMatchHistoryByPuuid(currentRegion, GameDataCache.me.getPuuid(), 0, FrameSetting.PAGE_SIZE, "q_" + item.getValue());
                    }
                    resetIndex();
                    setData(data, currentId, currentId.equals(GameDataCache.me.getPuuid()) ? GameDataCache.me : null);
                } catch (IOException ex) {
                    log.error("筛选战绩错误" + ex.getMessage());
                }
            }
        });

        this.playerPanel.setLayout((new BoxLayout(playerPanel, BoxLayout.Y_AXIS)));
        this.playerPanel.add(choosePanel);
        this.playerPanel.add(Box.createVerticalGlue());
        this.playerPanel.add(searchPanel);
        this.playerPanel.add(Box.createVerticalGlue());
        this.playerPanel.add(playerDetail);
        this.playerPanel.add(Box.createVerticalGlue());

        this.panelIndex.add(buttonPrevious);
        this.panelIndex.add(labelIndex);
        this.panelIndex.add(buttonNext);

        this.mainPanel.add(playerPanel);
    }

    public void setData(List<SpgProductsMatchHistoryBO> data, String puuid, MePlayer player) {
        //设置左边战绩数据
        if (!puuid.equals(currentId) && currentRegion.equals(GameDataCache.leagueClient.getRegion())) {
            try {
                if (player == null) {
                    this.playerDetail.setData(GameDataCache.me);
                    this.currentRegion = GameDataCache.leagueClient.getRegion();
                } else {
                    this.playerDetail.setData(player);
                }
            } catch (NoSummonerException e) {
                log.error("未找到召唤师");
            } catch (IOException e) {
                log.error("召唤师详情错误");
            }
        }
        if (puuid.equals(GameDataCache.me.getPuuid())) {
            backButton.setEnabled(false);
        } else {
            backButton.setEnabled(true);
        }
        currentId = puuid;
        this.panelContainer.removeAll();
        //设置右边战绩
        if (data != null && !data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                SGPRecordLine linePanel = new SGPRecordLine();

                linePanel.setLayout((new BoxLayout(linePanel, BoxLayout.X_AXIS)));
                //获取游戏的对局信息
                SpgGames record = data.get(i).getJson();
                Map<Integer, List<SpgParticipants>> collect = record.getParticipants().stream().collect(Collectors.groupingBy(SpgParticipants::getTeamId));
                List<SpgParticipants> teamOne = collect.get(GameConstant.TEAM_ONE);
                List<SpgParticipants> teamTwo = collect.get(GameConstant.TEAM_TWO);
                SpgParticipants selfData = record.getParticipants().stream().filter(item -> item.getPuuid().equals(puuid)).findFirst().get();
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
                linePanel.addMouseListener(new SelectDetailListener(data.get(i).getJson()));
                for (Component component : linePanel.getComponents()) {
                    setBgColor(component, bgColor);
                }
                panelContainer.add(linePanel);
            }
            this.labelIndex.setText(Integer.toString(index));
            //this.add(searchPanel);
            panelContainer.add(this.panelIndex);
            panelContainer.revalidate();
            panelContainer.repaint();
            mainPanel.add(panelContainer);
            this.add(mainPanel);
        } else {
            mainPanel.revalidate();
            mainPanel.repaint();
            FrameTipUtil.errorOccur("未查询到数据");
        }
    }

    /**
     * 设置页数
     */
    public void resetIndex() {
        this.index = 1;
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
        if (teamOne != null) {
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

    public void setPlayerPanelVisible(boolean visible) {
        this.playerPanel.setVisible(visible);
    }
}

@Slf4j
class SelectDetailListener extends MouseAdapter {
    private final SpgGames data;


    public SelectDetailListener(SpgGames data) {
        this.data = data;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !FrameInnerCache.sgpRecordPanel.isShowDetail && e.getSource() instanceof SGPRecordLine line) {
            try {

                if (data != null && MatchHistoryUtil.isQueryHistoryDetail(data.getQueueId())) {
                    Component[] components = line.getParent().getComponents();
                    for (Component component : components) {
                        if (!component.equals(line)) {
                            component.setVisible(false);
                        }
                    }

                    FrameInnerCache.sgpRecordPanel.historyDetail = HistoryDetail.build(data);
                    FrameInnerCache.sgpRecordPanel.setPlayerPanelVisible(false);
                    FrameInnerCache.sgpRecordPanel.panelContainer.add(FrameInnerCache.sgpRecordPanel.historyDetail);
                    FrameInnerCache.sgpRecordPanel.isShowDetail = true;
                }
            } catch (IOException ex) {
                log.error("查询对局详情错误");
            }

        } else if (e.getButton() == MouseEvent.BUTTON1 && FrameInnerCache.sgpRecordPanel.isShowDetail && e.getSource() instanceof SGPRecordLine && FrameInnerCache.sgpRecordPanel.isShowDetail) {
            FrameInnerCache.sgpRecordPanel.setPlayerPanelVisible(true);
            FrameInnerCache.sgpRecordPanel.panelContainer.remove(FrameInnerCache.sgpRecordPanel.historyDetail);
            FrameInnerCache.sgpRecordPanel.showAllComponent();
            FrameInnerCache.sgpRecordPanel.isShowDetail = false;
        }
    }

    public MePlayer SgpBuildPlayer(SummonerAlias alias, SgpSummonerInfoBo sgpSummonerInfoBo) {
        MePlayer mePlayer = new MePlayer();
        mePlayer.setGameName(alias.getGameName());
        mePlayer.setTagLine(alias.getTagLine());
        mePlayer.setPuuid(sgpSummonerInfoBo.getPuuid());
        mePlayer.setPrivacy(sgpSummonerInfoBo.getPrivacy());
        mePlayer.setProfileIconId(sgpSummonerInfoBo.getProfileIconId());
        mePlayer.setSummonerLevel(sgpSummonerInfoBo.getLevel());
        return mePlayer;
    }

}
