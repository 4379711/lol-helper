package helper.frame.panel.client;

import helper.bo.SkinBO;
import helper.bo.TencentChampion;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.constant.GameConstant;
import helper.enums.ImageEnum;
import helper.frame.bo.ItemBO;
import helper.frame.listener.MouseCursorListener;
import helper.frame.panel.base.BaseComboBox;
import helper.frame.utils.MatchHistoryUtil;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CareerBackSkinFrame extends JFrame {
    private JPanel centerPanel; // 面板（所有可选英雄）

    public CareerBackSkinFrame(String title) {
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

        BaseComboBox<ItemBO> positionSelect = GameConstant.CREATE_POSITION_SELECT();
        BaseComboBox<ItemBO> roleSelect = GameConstant.CREATE_ROLE_SELECT();
        JTextField searchTextField = new JTextField();

        JButton searchButton = new JButton("搜索");
        searchButton.addMouseListener(new MouseCursorListener());

        // 添加监听器
        positionSelect.addActionListener(e -> updateCenterPanel(positionSelect, roleSelect, searchTextField));
        roleSelect.addActionListener(e -> updateCenterPanel(positionSelect, roleSelect, searchTextField));
        searchTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateCenterPanel(positionSelect, roleSelect, searchTextField);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateCenterPanel(positionSelect, roleSelect, searchTextField);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateCenterPanel(positionSelect, roleSelect, searchTextField);
            }
        });

        // 添加组件（每个组件添加间隔）
        contentPanel.add(positionSelect, "w 150!, h 30!, gapleft 5");
        contentPanel.add(roleSelect, "w 150!, h 30!, gapleft 5");
        contentPanel.add(searchTextField, "w 150!, h 30!, gapleft 5");
        contentPanel.add(searchButton, "w 80!, h 30!, gapleft 5");

        northWrapper.add(contentPanel);
        add(northWrapper, BorderLayout.NORTH);
    }

    private void initCenterUI() {
        // 面板
        centerPanel = new JPanel();
        centerPanel.setLayout(new MigLayout(
                "wrap 7, align center", // wrap 7: 每行最多显示7个组件, align center: 居中对齐
                "[fill]",               // 列宽度自动填充
                "[]10[]"                // 行高，行间距为10像素
        ));
        centerPanel.setBorder(BorderFactory.createTitledBorder("可选英雄"));

        // 添加滚动条，并设置只垂直滚动
        JScrollPane rightScrollPane = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(rightScrollPane, BorderLayout.CENTER);

        // 初始化面板内容
        BaseComboBox<ItemBO> positionSelect = GameConstant.CREATE_POSITION_SELECT();
        BaseComboBox<ItemBO> roleSelect = GameConstant.CREATE_ROLE_SELECT();
        JTextField searchTextField = new JTextField();
        updateCenterPanel(positionSelect, roleSelect, searchTextField);
    }

    private void updateCenterPanel(BaseComboBox<ItemBO> positionSelect, BaseComboBox<ItemBO> roleSelect, JTextField searchTextField) {
        String selectedPosition = ((ItemBO) positionSelect.getSelectedItem()).getValue();
        String selectedRole = ((ItemBO) roleSelect.getSelectedItem()).getValue();
        String searchText = searchTextField.getText().trim().toLowerCase();

        centerPanel.removeAll();

        java.util.List<TencentChampion> champions = GameDataCache.allChampionName;
        for (TencentChampion champion : champions) {
            java.util.List<String> positions = champion.getPosition() == null ? Collections.emptyList() : champion.getPosition();
            List<String> roles = champion.getRoles() == null ? Collections.emptyList() : champion.getRoles();
            String keywords = champion.getKeywords();

            boolean matchesPosition = selectedPosition.equals("all") || positions.contains(selectedPosition);
            boolean matchesRole = selectedRole.equals("all") || roles.contains(selectedRole);
            boolean matchesSearch = searchText.isEmpty() || keywords.contains(searchText.toUpperCase());

            if (matchesPosition && matchesRole && matchesSearch) {
                JPanel champPanel = createChampionPanel(champion);
                centerPanel.add(champPanel, "grow");
            }
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel createChampionPanel(TencentChampion champion) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(90, 100)); // 调整面板大小以容纳按钮

        // 显示英雄图片
        JLabel imageLabel = new JLabel();
        ImageIcon icon = MatchHistoryUtil.getChampionIcon(champion.getHeroId(), 90, 90, ImageEnum.SQUARE);
        imageLabel.setIcon(icon);
        imageLabel.addMouseListener(new MouseCursorListener());
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        List<SkinBO> skinList = AppCache.api.getSkinByChampionId(champion.getHeroId());
                        CarouselFrame carouselFrame = new CarouselFrame(new ArrayList<>(skinList));
                        carouselFrame.setVisible(true);
                        carouselFrame.setAlwaysOnTop(true);
                    } catch (IOException ex) {
                        log.error(ex.getMessage());
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

}
