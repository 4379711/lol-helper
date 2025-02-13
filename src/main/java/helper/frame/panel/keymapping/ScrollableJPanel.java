package helper.frame.panel.keymapping;

import helper.frame.panel.base.BasePanel;
import helper.services.hotkey.HotKeyConsumerMapping;
import helper.services.hotkey.HotKeyFactory;
import helper.services.hotkey.HotKeyService;
import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author WuYi
 */
@Slf4j
public class ScrollableJPanel extends BasePanel {

    public ScrollableJPanel() {
        this.setName("按键自定义");
        setLayout(new MigLayout("wrap", "[grow]", "[grow]"));
        DefaultListModel<HotKeyConsumerMapping> listModel = new DefaultListModel<>();

        for (HotKeyConsumerMapping consumerMapping : HotKeyFactory.HOT_KEY_LIST) {
            listModel.addElement(consumerMapping);
        }

        JList<HotKeyConsumerMapping> list = new JList<>(listModel);
        list.setCellRenderer(new CustomListCellRenderer());
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(index);
                } else if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    HotKeyConsumerMapping item = listModel.getElementAt(index);
                    showPopupMenu(e, item);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, "grow");
    }

    public static ScrollableJPanel build() {
        return new ScrollableJPanel();
    }

    private void showPopupMenu(MouseEvent e, HotKeyConsumerMapping item) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("修改快捷键");
        menuItem1.addActionListener(actionEvent -> showKeyDialog(item));
        popupMenu.add(menuItem1);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void showKeyDialog(HotKeyConsumerMapping item) {
        JDialog dialog = new JDialog((Frame) null, "修改快捷键", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new MigLayout("wrap 1", "[grow, fill]"));

        JLabel label = new JLabel("请按下新的快捷键：");
        JTextField textField = new JTextField();
        textField.setEditable(false);
        HotKeyService.registerHook = true;
        HotKeyService.listenForKeyRegister(e -> {
            int keyCode = e.getKeyCode();
            String keyText = NativeKeyEvent.getKeyText(keyCode);
            textField.setText(keyText);
            // 在这里处理快捷键的逻辑
            item.setKeyCode(keyCode);
            System.out.println("捕获到按键: " + keyText);
        });

        dialog.add(label);
        dialog.add(textField, "grow");

        JButton okButton = new JButton("确定");
        okButton.addActionListener(actionEvent -> {
            // 在这里保存新的快捷键设置
            dialog.dispose();
            HotKeyFactory.saveFile();
            HotKeyService.registerHook = false;
        });
        dialog.add(okButton);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static class CustomListCellRenderer extends JPanel implements ListCellRenderer<HotKeyConsumerMapping> {
        private JLabel leftLabel;
        private JLabel rightLabel;

        public CustomListCellRenderer() {
            // 调整约束，使内容靠上，并去除行间填充
            setLayout(new MigLayout("insets 2 10 2 0", "[grow,fill][]", "[]0[]"));
            leftLabel = new JLabel();
            rightLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gradientPaint = new GradientPaint(0, 0, Color.ORANGE, 0, getHeight(), Color.YELLOW);
                    g2.setPaint(gradientPaint);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    super.paintComponent(g);
                }

                @Override
                protected void paintBorder(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.BLACK);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }
            };
            rightLabel.setOpaque(false);
            rightLabel.setBackground(new Color(216, 204, 3));
            rightLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 内边距
            leftLabel = new JLabel();
            add(leftLabel, "grow, wmin 0");
            add(rightLabel, "wrap, wmin 0");
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends HotKeyConsumerMapping> list, HotKeyConsumerMapping value, int index, boolean isSelected, boolean cellHasFocus) {

            leftLabel.setText(value.getHotKeyConsumer().getHotKeyName());
            rightLabel.setText(NativeKeyEvent.getKeyText(value.getKeyCode()));

            // 确保右边文字颜色为黑色
            rightLabel.setForeground(Color.BLACK);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                leftLabel.setForeground(list.getSelectionForeground());
                rightLabel.setForeground(Color.BLACK);
            } else {
                setBackground(list.getBackground());
                leftLabel.setForeground(list.getForeground());
                rightLabel.setForeground(Color.BLACK); // 始终设置为黑色
            }
            return this;
        }
    }
}
