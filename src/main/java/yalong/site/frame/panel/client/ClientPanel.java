package yalong.site.frame.panel.client;

import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.panel.base.BasePanel;

import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/11
 */
public class ClientPanel extends BasePanel {

    public ClientPanel() {
        this.setName("游戏设置");
    }

    public static ClientPanel builder() {
        ClientPanel clientPanel = new ClientPanel();
        //布局3*3
        GridBagLayout layout = new GridBagLayout();
        clientPanel.setLayout(layout);
        //第一行三个
        ComponentBO rankFirstBox = RankFirstBox.builder();
        clientPanel.add(rankFirstBox.getComp(), rankFirstBox.getConstraints());
        ComponentBO rankSecondBox = RankSecondBox.builder();
        clientPanel.add(rankSecondBox.getComp(), rankSecondBox.getConstraints());
        ComponentBO rankThirdBox = RankThirdBox.builder();
        clientPanel.add(rankThirdBox.getComp(), rankThirdBox.getConstraints());

        //第二行三个
        ComponentBO checkBox = AutoAcceptCheckBox.builder();
        clientPanel.add(checkBox.getComp(), checkBox.getConstraints());
        ComponentBO reconnectCheckBox = AutoReconnectCheckBox.builder();
        clientPanel.add(reconnectCheckBox.getComp(), reconnectCheckBox.getConstraints());
        ComponentBO sendCheckBox = AutoSendCheckBox.builder();
        clientPanel.add(sendCheckBox.getComp(), sendCheckBox.getConstraints());

        //第三行3个
        ComponentBO gameStatusBox = GameStatusBox.builder();
        clientPanel.add(gameStatusBox.getComp(), gameStatusBox.getConstraints());
        ComponentBO leaveBox = LeaveCheckBox.builder();
        clientPanel.add(leaveBox.getComp(), leaveBox.getConstraints());
        ComponentBO moyanBox = MoyanCheckBox.builder();
        clientPanel.add(moyanBox.getComp(), moyanBox.getConstraints());

        return clientPanel;

    }

}
