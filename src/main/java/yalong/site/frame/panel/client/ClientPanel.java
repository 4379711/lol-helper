package yalong.site.frame.panel.client;

import yalong.site.frame.panel.base.BaseButton;
import yalong.site.frame.panel.base.BasePanel;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class ClientPanel extends BasePanel {

    public ClientPanel() {
        this.setName("个人设置");
    }

    public static ClientPanel builder() {
        // TODO: 2022/2/12 注入服务
        ClientPanel clientPanel = new ClientPanel();
        GameStatusBox gameStatusBox = new GameStatusBox(null);
        clientPanel.add(gameStatusBox);
        BaseButton button = new AutoEnsureButton();
        clientPanel.add(button);
        return clientPanel;

    }

}
