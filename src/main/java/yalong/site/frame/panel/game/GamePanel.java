package yalong.site.frame.panel.game;

import yalong.site.frame.panel.base.BaseButton;
import yalong.site.frame.panel.base.BasePanel;
import yalong.site.frame.panel.client.AutoEnsureButton;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class GamePanel extends BasePanel {

    public GamePanel() {
        this.setName("游戏配置");
    }

    public static GamePanel builder() {
        GamePanel gamePanel = new GamePanel();
        BaseButton button = new AutoEnsureButton();
        gamePanel.add(button);
        return gamePanel;
    }

}
