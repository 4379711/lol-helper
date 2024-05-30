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
		//网格布局
		GridBagLayout layout = new GridBagLayout();
		clientPanel.setLayout(layout);
		ComponentBO rankFirstBox = RankFirstBox.builder();
		clientPanel.add(rankFirstBox.getComp(), rankFirstBox.getConstraints());
		ComponentBO rankSecondBox = RankSecondBox.builder();
		clientPanel.add(rankSecondBox.getComp(), rankSecondBox.getConstraints());
		ComponentBO rankThirdBox = RankThirdBox.builder();
		clientPanel.add(rankThirdBox.getComp(), rankThirdBox.getConstraints());
		ComponentBO checkBox = AutoAcceptCheckBox.builder();
		clientPanel.add(checkBox.getComp(), checkBox.getConstraints());
		ComponentBO reconnectCheckBox = AutoReconnectCheckBox.builder();
		clientPanel.add(reconnectCheckBox.getComp(), reconnectCheckBox.getConstraints());
		ComponentBO sendCheckBox = SendScoreCheckBox.builder();
		clientPanel.add(sendCheckBox.getComp(), sendCheckBox.getConstraints());
		ComponentBO gameStatusBox = GameStatusBox.builder();
		clientPanel.add(gameStatusBox.getComp(), gameStatusBox.getConstraints());
		ComponentBO leaveBox = CommunicateCheckBox.builder();
		clientPanel.add(leaveBox.getComp(), leaveBox.getConstraints());
		ComponentBO autoKeyCheckBox = AutoKeyCheckBox.builder();
		clientPanel.add(autoKeyCheckBox.getComp(), autoKeyCheckBox.getConstraints());
		ComponentBO playAgainBox = AutoPlayAgainCheckBox.builder();
		clientPanel.add(playAgainBox.getComp(), playAgainBox.getConstraints());
		ComponentBO banBox = BanBox.builder();
		clientPanel.add(banBox.getComp(), banBox.getConstraints());
		ComponentBO pickBox = PickBox.builder();
		clientPanel.add(pickBox.getComp(), pickBox.getConstraints());
		ComponentBO pickSkinBox = PickSkinBox.builder();
		clientPanel.add(pickSkinBox.getComp(), pickSkinBox.getConstraints());
		ComponentBO careerBox = CareerBackgroundBox.builder();
		clientPanel.add(careerBox.getComp(), careerBox.getConstraints());
		ComponentBO careerSkinBox = CareerBackgroundSkinBox.builder();
		clientPanel.add(careerSkinBox.getComp(), careerSkinBox.getConstraints());
		ComponentBO historyButton = HistoryButton.builder();
		clientPanel.add(historyButton.getComp(), historyButton.getConstraints());
		return clientPanel;

	}

}
