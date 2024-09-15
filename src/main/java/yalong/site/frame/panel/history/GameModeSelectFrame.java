package yalong.site.frame.panel.history;

import yalong.site.bo.GameQueue;
import yalong.site.cache.FrameSetting;
import yalong.site.cache.FrameUserSettingPersistence;
import yalong.site.cache.GameDataCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author WuYi
 */
public class GameModeSelectFrame extends JFrame {
    private final JPanel gameModePanel = new JPanel();

    private final JPanel allPanel = new JPanel();

    private final JFrame jFrame;

    public GameModeSelectFrame(String topic) {
        jFrame = this;
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
        this.setIconImage(image);
        this.setSize(FrameSetting.CHAMPION_SELECT_WIDTH, FrameSetting.CHAMPION_SELECT_HEIGHT);
        //窗口居中
        this.setLocationRelativeTo(null);
        this.setName(topic);

        createButtons();

        gameModePanel.setPreferredSize(new Dimension(FrameSetting.CHAMPION_SELECT_WIDTH, FrameSetting.CHAMPION_SELECT_HEIGHT - 50));
        allPanel.add(gameModePanel);
        this.add(allPanel);
        gameModePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
    }


    public void createButtons() {
        gameModePanel.removeAll();
        for (Integer key : GameDataCache.selectGameQueueList.keySet()) {
            GameQueue gameQueue = GameDataCache.selectGameQueueList.get(key);

            JCheckBox button = new JCheckBox(gameQueue.getName());
            button.setSelected(FrameUserSettingPersistence.selectMode.contains(key));
            button.addActionListener(e -> {
                GameDataCache.selectGameQueueList.get(key).setSelect(button.isSelected());
                if (button.isSelected()) {
                    FrameUserSettingPersistence.selectMode.add(key);
                } else {
                    FrameUserSettingPersistence.selectMode.remove(key);
                }

            }
            );
            button.setPreferredSize(new Dimension(250, 30));
            gameModePanel.add(button);
        }
        gameModePanel.revalidate();
        gameModePanel.repaint();
    }
}
