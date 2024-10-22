package helper.site.frame.panel.key;

import helper.site.cache.AppCache;
import helper.site.cache.FrameInnerCache;
import helper.site.frame.bo.ItemBO;
import helper.site.frame.panel.base.BaseComboBox;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author @_@
 */
@Slf4j
public class KeySelectBox extends BaseComboBox<ItemBO> {

	public KeySelectBox() {
		this.setItems();
		this.addItemListener(listener());
	}

	public static KeySelectBox builder() {
		return new KeySelectBox();
	}

	public void setItems() {
		this.addItem(new ItemBO(null, "连招技能键"));
		Field[] fields = KeyEvent.class.getFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
				try {
					int code = field.getInt(null);
					AppCache.commonKeyCode.add(code);
					this.addItem(new ItemBO(String.valueOf(code), field.getName()));
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private ItemListener listener() {
		return e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				ItemBO item = (ItemBO) e.getItem();
				if (item.getValue() == null) {
					return;
				}
				String oldText = FrameInnerCache.keyTextPane.getText();
				String s = oldText + " " + item.getValue();
				FrameInnerCache.keyTextPane.setText(s.strip());
				this.setSelectedIndex(0);
			}
		};
	}

}
