package helper.frame.panel.history;

import helper.frame.panel.base.BaseButton;

import java.awt.event.ActionListener;

/**
 * @author WuYi
 */
public class GameModeBox extends BaseButton {
	private final GameModeBox gameModeBox;

	public GameModeBox() {
		gameModeBox = this;
		this.setText("筛选需要的模式");
		this.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		return e -> {
			GameModeSelectFrame selectFrame = new GameModeSelectFrame("选择模式");
			selectFrame.setVisible(true);
		};
	}
}
