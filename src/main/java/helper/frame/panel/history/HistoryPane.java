package helper.frame.panel.history;

import helper.frame.panel.base.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author WuYi
 */
public class HistoryPane extends BasePanel {
	public HistoryPane() {
		this.setName("战绩");
	}

	private static ArrayList<Component> loadAllPanel() {
		ArrayList<Component> list = new ArrayList<>();

		list.add(new SendScoreCheckBox());
		list.add(new ShowTeamBox());

		list.add(new BlackListHistoryButton());
		list.add(new HistoryButton());

		list.add(new HistoryCountBox());
		list.add(new JPanel());

		list.add(new JPanel());
		list.add(new JPanel());

		list.add(new JPanel());
		list.add(new JPanel());

		list.add(new JPanel());
		list.add(new JPanel());

		list.add(new JPanel());
		list.add(new JPanel());

		return list;
	}

	public static HistoryPane builder() {
		HistoryPane historyPane = new HistoryPane();
		GridBagLayout layout = new GridBagLayout();
		historyPane.setLayout(layout);
		ArrayList<Component> list = loadAllPanel();
		for (int i = 0; i < list.size(); i++) {
			int y = i / 2;
			int x = i % 2;
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
			historyPane.add(list.get(i), grid);
		}
		return historyPane;
	}
}
