package helper.frame.panel.client;

import helper.bo.TencentChampion;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.constant.GameConstant;
import helper.enums.ImageEnum;
import helper.frame.bo.ItemBO;
import helper.frame.listener.MouseCursorListener;
import helper.frame.panel.base.BaseCheckBox;
import helper.frame.panel.base.BaseComboBox;
import helper.frame.panel.base.HoverImage;
import helper.frame.panel.base.RoundImageButton;
import helper.frame.utils.FrameConfigUtil;
import helper.frame.utils.FrameTipUtil;
import helper.frame.utils.MatchHistoryUtil;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;

/**
 * 选择英雄界面
 */
@Slf4j
public class ChampionBanFrame extends JFrame {
    private JPanel leftMainPanel;  // 左侧面板（已选择的英雄）
    private JPanel rightPanel; // 右侧面板（所有可选英雄）
    private Map<String, JPanel> categoryPanels;
    private String currentCategory; // 分类面板映射

    public ChampionBanFrame(String title) {

        initLeftJPane();
        initNorthUI();
        initCenterUI(); // 初始化中间的左右面板
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
        this.setIconImage(image);
        setTitle(title);
        setSize(900, 600);  // 设置窗口大小
        // 禁止用户调整窗口大小
        setResizable(false);
        setLocationRelativeTo(null); // 界面居中
    }

    private void initNorthUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // 主容器（保证内容整体居中）
        JPanel northWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5)); // 水平间隙增加到15
        northWrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));    // 增加容器边距

        // 内容面板（MigLayout管理组件）
        JPanel contentPanel = new JPanel(new MigLayout(
                "gap 15 10", // 水平间隙15px，垂直间隙10px
                "[][][grow][]", // 列定义（第三列自动扩展）
                "[]"
        ));

        RoundImageButton helpButton = new RoundImageButton(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/help.png"))).getImage(), new Dimension(15, 15));
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<html><font color='red'>无视队友预选</font>即使队友预选 禁用环节依然禁用\n<html>");
                    sb.append("<html><font color='red'>禁用英雄</font>从点击右边英雄头像鼠标左键\n<html>");
                    sb.append("<html><font color='red'>英雄置顶</font>从点击左边英雄头像鼠标左键可以置顶\n<html>");
                    sb.append("<html><font color='red'>英雄取消</font>从点击左边英雄头像鼠标右键可以取消禁用\n<html>");
                    sb.append("<html><font color='red'>匹配模式</font>如果匹配有ban环节则选择第一个ban(未测试 如无限火力先禁后选) \n<html>");
                    sb.append("<html><font color='red'>排位模式</font>排位根据每个位置的配置ban英雄<html>");
                    FrameTipUtil.infoMsg(sb.toString());
                }
            }
        });

        BaseComboBox<ItemBO> positionSelect = GameConstant.CREATE_POSITION_SELECT();
        BaseComboBox<ItemBO> roleSelect = GameConstant.CREATE_ROLE_SELECT();
        JTextField searchTextField = new JTextField();
        // 创建单选按钮
        BaseCheckBox teamSelectFlag = new BaseCheckBox();
        teamSelectFlag.setText("无视队友预选");
        teamSelectFlag.setSelected(AppCache.settingPersistence.getBanTeammateFlag());
        teamSelectFlag.addItemListener(e -> {
            AppCache.settingPersistence.setBanTeammateFlag(e.getStateChange() == ItemEvent.SELECTED);
            FrameConfigUtil.save();
        });
        JButton searchButton = new JButton("搜索");
        searchButton.addMouseListener(new MouseCursorListener());

        // 添加监听器
        positionSelect.addActionListener(e -> updateRightPanel(positionSelect, roleSelect, searchTextField));
        roleSelect.addActionListener(e -> updateRightPanel(positionSelect, roleSelect, searchTextField));
        searchTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateRightPanel(positionSelect, roleSelect, searchTextField);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateRightPanel(positionSelect, roleSelect, searchTextField);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateRightPanel(positionSelect, roleSelect, searchTextField);
            }
        });

        // 添加组件（每个组件添加间隔）
        contentPanel.add(helpButton, "w 20!, h 20!, gapleft 5");
        contentPanel.add(teamSelectFlag, "w 100!, h 40!, gapleft 5");
        contentPanel.add(positionSelect, "w 150!, h 30!, gapleft 5");
        contentPanel.add(roleSelect, "w 150!, h 30!, gapleft 5");
        contentPanel.add(searchTextField, "w 200!, h 30!, gapleft 5");
        contentPanel.add(searchButton, "w 80!, h 30!, gapleft 5");

        northWrapper.add(contentPanel);
        add(northWrapper, BorderLayout.NORTH);
    }

    private void initCenterUI() {
        // 左侧面板

        leftMainPanel = new JPanel();
        leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));
        leftMainPanel.setBorder(BorderFactory.createTitledBorder("已禁用的英雄"));

        // 创建下拉框并添加到左侧面板顶部
        BaseComboBox<ItemBO> categorySelect = createCategorySelect();
        categorySelect.addActionListener(e -> {
            ItemBO bo = (ItemBO) categorySelect.getSelectedItem(); // 更新当前选中的分类
            currentCategory = bo.getValue();
            updateLeftPanel(currentCategory);
        });
        categorySelect.setSelectedItem(new ItemBO("default", "默认"));// 设置默认选项
        currentCategory = "default";
        updateLeftPanel(currentCategory);

        categorySelect.setPreferredSize(new Dimension(70, 25));
        JPanel leftTopPanel = new JPanel(new BorderLayout());
        leftTopPanel.add(categorySelect, BorderLayout.CENTER);

        JPanel leftWrapperPanel = new JPanel(new BorderLayout());
        leftWrapperPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftWrapperPanel.add(leftMainPanel, BorderLayout.CENTER);

        JScrollPane leftScrollPane = new JScrollPane(leftWrapperPanel);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // 右侧面板
        rightPanel = new JPanel();
        rightPanel.setLayout(new MigLayout(
                "wrap 7, align center", // wrap 6: 每行最多显示6个组件, align center: 居中对齐
                "[fill]",               // 列宽度自动填充
                "[]10[]"                // 行高，行间距为10像素
        ));
        rightPanel.setBorder(BorderFactory.createTitledBorder("禁用英雄"));

        // 添加滚动条，并设置只垂直滚动
        JScrollPane rightScrollPane = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // 将左右面板添加到JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, rightScrollPane);
        splitPane.setDividerLocation(130); // 设置分割线位置

        add(splitPane, BorderLayout.CENTER);

        // 初始化右侧面板内容
        BaseComboBox<ItemBO> positionSelect = GameConstant.CREATE_POSITION_SELECT();
        BaseComboBox<ItemBO> roleSelect = GameConstant.CREATE_ROLE_SELECT();
        JTextField searchTextField = new JTextField();
        updateRightPanel(positionSelect, roleSelect, searchTextField);
    }

    private void updateRightPanel(BaseComboBox<ItemBO> positionSelect, BaseComboBox<ItemBO> roleSelect, JTextField searchTextField) {
        String selectedPosition = ((ItemBO) positionSelect.getSelectedItem()).getValue();
        String selectedRole = ((ItemBO) roleSelect.getSelectedItem()).getValue();
        String searchText = searchTextField.getText().trim().toLowerCase();

        rightPanel.removeAll();

        List<TencentChampion> champions = new ArrayList<>();
        TencentChampion nullChampion = new TencentChampion();
        nullChampion.setHeroId(-1);
        nullChampion.setName("空ban");
        champions.add(nullChampion);
        champions.addAll(GameDataCache.allChampionName);
        for (TencentChampion champion : champions) {
            List<String> positions = champion.getPosition() == null ? Collections.emptyList() : champion.getPosition();
            List<String> roles = champion.getRoles() == null ? Collections.emptyList() : champion.getRoles();
            String keywords = champion.getKeywords();

            boolean matchesPosition = selectedPosition.equals("all") || positions.contains(selectedPosition);
            boolean matchesRole = selectedRole.equals("all") || roles.contains(selectedRole);
            boolean matchesSearch = searchText.isEmpty() || keywords.contains(searchText.toUpperCase());

            if (matchesPosition && matchesRole && matchesSearch) {
                JPanel champPanel = createChampionPanel(champion);
                rightPanel.add(champPanel, "grow");
            }
        }

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void updateLeftPanel(String category) {
        leftMainPanel.removeAll();
        leftMainPanel.add(findCategoryPanel(category));
        leftMainPanel.revalidate();
        leftMainPanel.repaint();
    }

    private JPanel findCategoryPanel(String category) {
        if (!categoryPanels.containsKey(category)) {
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
            categoryPanels.put(category, categoryPanel);
        }
        return categoryPanels.get(category);
    }


    private JPanel createChampionPanel(TencentChampion champion, boolean isSelectedPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(90, 100)); // 调整面板大小以容纳按钮

        // 显示英雄图片

        ImageIcon icon = MatchHistoryUtil.getChampionIcon(champion.getHeroId(), 90, 90, ImageEnum.SQUARE);
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon overlayIcon;
        if (isSelectedPanel) {
            overlayIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/up.png"))); // 选中状态的覆盖图标路径
        } else {
            overlayIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/ban.png"))); // 默认覆盖图标路径
        }
        HoverImage imageLabel = new HoverImage(new ImageIcon(img), overlayIcon, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSelectedPanel) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        JPanel parentPanel = (JPanel) panel.getParent();
                        parentPanel.remove(panel);
                        parentPanel.add(panel, 0);
                        topChampion(currentCategory, champion.getHeroId());
                        parentPanel.revalidate();
                        parentPanel.repaint();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        JPanel parentPanel = (JPanel) panel.getParent();
                        parentPanel.remove(panel);
                        parentPanel.revalidate();
                        parentPanel.repaint();
                        delChampion(currentCategory, champion.getHeroId());
                    }
                } else {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        addChampionToLeft(champion, currentCategory);
                    }
                }
            }
        });
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 图片居中

        // 显示英雄姓名
        JLabel nameLabel = new JLabel(champion.getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 姓名居中

        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(5)); // 图片和姓名之间的空隙
        panel.add(nameLabel);

        return panel;
    }

    private JPanel createChampionPanel(TencentChampion champion) {
        return createChampionPanel(champion, false);
    }


    private void addChampionToLeft(TencentChampion champion, String category) {
        // 找到对应分类的面板
        JPanel targetPanel = findCategoryPanel(category);

        // 检查是否已经存在相同的英雄
        for (Component comp : targetPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JLabel) {
                        JLabel label = (JLabel) innerComp;
                        if (champion.getName().equals(label.getText())) {
                            // 英雄已存在，不重复添加
                            FrameTipUtil.errorMsg("该英雄禁用");
                            return;
                        }
                    }
                }
            }
        }

        // 如果不存在，添加英雄
        JPanel champPanel = createChampionPanel(champion, true);
        addChampion(category, champion.getHeroId());
        targetPanel.add(champPanel);
        targetPanel.revalidate();
        targetPanel.repaint();
    }


    protected BaseComboBox<ItemBO> createCategorySelect() {
        BaseComboBox<ItemBO> categorySelect = new BaseComboBox<ItemBO>();
        categorySelect.addItem(new ItemBO("default", "匹配模式"));
        categorySelect.addItem(new ItemBO("top", "排位上单"));
        categorySelect.addItem(new ItemBO("jungle", "排位打野"));
        categorySelect.addItem(new ItemBO("middle", "排位中单"));
        categorySelect.addItem(new ItemBO("bottom", "排位下路"));
        categorySelect.addItem(new ItemBO("utility", "排位辅助"));
        return categorySelect;
    }

    protected void addChampion(String category, Integer championId) {
        ArrayList<Integer> idList = AppCache.settingPersistence.getBanMap().get(category);
        if (!idList.contains(championId)) {
            idList.add(championId);
        }
        FrameConfigUtil.save();
    }

    protected void delChampion(String category, Integer championId) {
        ArrayList<Integer> idList = AppCache.settingPersistence.getBanMap().get(category);
        idList.remove(championId);
        FrameConfigUtil.save();
    }

    protected void topChampion(String category, Integer championId) {
        ArrayList<Integer> idList = AppCache.settingPersistence.getBanMap().get(category);
        // 找到指定元素的索引位置
        int index = idList.indexOf(championId);
        if (index != -1) { // 如果找到了元素
            // 移除该元素
            idList.remove(index);
            // 将该元素插入到列表的首位
            idList.add(0, championId);
        }
        FrameConfigUtil.save();
    }

    /**
     * 初始化加载保存过的英雄
     */
    protected void initLeftJPane() {
        categoryPanels = new HashMap<>();
        for (Map.Entry<String, ArrayList<Integer>> entry : AppCache.settingPersistence.getBanMap().entrySet()) {
            String key = entry.getKey();
            ArrayList<Integer> champions = entry.getValue();
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
            for (Integer championId : champions) {
                TencentChampion tencentChampion = GameDataCache.allChampionName.stream().filter(item -> Objects.equals(item.getHeroId(), championId)).findFirst().get();
                categoryPanel.add(createChampionPanel(tencentChampion, true));
            }
            categoryPanels.put(key, categoryPanel);
        }
    }
}
