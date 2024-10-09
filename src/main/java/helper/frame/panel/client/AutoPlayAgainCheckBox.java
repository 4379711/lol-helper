package helper.frame.panel.client;

import helper.cache.FrameUserSettingPersistence;
import helper.frame.panel.base.BaseCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author @_@
 * @date 2024/4/23
 */
public class AutoPlayAgainCheckBox extends BaseCheckBox {
	public AutoPlayAgainCheckBox() {
		this.setText("自动再来一局");
		this.setSelected(FrameUserSettingPersistence.autoPlayAgain);
		this.addItemListener(listener());
	}

	private ItemListener listener() {
		return e -> FrameUserSettingPersistence.autoPlayAgain = e.getStateChange() == ItemEvent.SELECTED;
	}

}
