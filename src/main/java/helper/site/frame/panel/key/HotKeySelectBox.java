package helper.site.frame.panel.key;

import helper.site.cache.AppCache;
import helper.site.cache.FrameInnerCache;
import helper.site.frame.bo.ItemBO;
import helper.site.frame.panel.base.BaseComboBox;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author @_@
 */
@Slf4j
public class HotKeySelectBox extends BaseComboBox<ItemBO> {

	public HotKeySelectBox() {
		this.setItems();
		this.addItemListener(listener());
	}

	public static HotKeySelectBox builder() {
		return new HotKeySelectBox();
	}

	public void setItems() {
		this.addItem(new ItemBO(null, "快捷键"));
		Field[] fields = NativeKeyEvent.class.getFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
				try {
					int code = field.getInt(null);
					AppCache.hotKeyCode.add(code);
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
				String s = oldText + System.lineSeparator() + item.getValue();
				FrameInnerCache.keyTextPane.setText(s.strip());
				this.setSelectedIndex(0);
			}
		};
	}

}
