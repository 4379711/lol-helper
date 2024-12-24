package helper.frame.panel.client;

import helper.frame.panel.base.BasePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author @_@
 * @date 2022/2/11
 */
public class ClientPanel extends BasePanel {

	public ClientPanel() {
		this.setName("游戏设置");
	}

	private static ArrayList<Component> loadAllPanel() {
		ArrayList<Component> list = new ArrayList<>();
		list.add(new RankFirstBox());
		list.add(new RankSecondBox());
		list.add(new RankThirdBox());

		list.add(new AutoAcceptCheckBox());
		list.add(new AutoSearchCheckBox());
		list.add(new AutoPlayAgainCheckBox());

		list.add(new AutoReconnectCheckBox());
		list.add(new CommunicateCheckBox());
		list.add(new PickSkinCheckBox());

		list.add(new GameStatusBox());
		list.add(new BanBox());
		list.add(new PickBox());

		list.add(new CareerBackgroundBox());
		list.add(new CareerBackgroundSkinBox());

		return list;
	}

	public static ClientPanel builder() {
		ClientPanel clientPanel = new ClientPanel();
		GridBagLayout layout = new GridBagLayout();
		clientPanel.setLayout(layout);
		ArrayList<Component> list = loadAllPanel();
		for (int i = 0; i < list.size(); i++) {
			int y = i / 3;
			int x = i % 3;
			GridBagConstraints grid = new GridBagConstraints(
					// 第(0,0)个格子
					x, y,
					// 占1列,占1行
					1, 1,
					//横向占100%长度,纵向占100%长度
					1, 1,
					//居中,组件小的话就两边铺满窗格
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					// 窗格之间的距离
					new Insets(0, 0, 0, 0),
					// 增加组件的首选宽度和高度
					0, 0
			);
			clientPanel.add(list.get(i), grid);
		}
		return clientPanel;
	}
}
