package yalong.site.frame.panel.key;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.utils.DiyKeyUtil;
import yalong.site.frame.utils.FrameTipUtil;
import yalong.site.services.hotkey.HotKeyConsumer;
import yalong.site.services.hotkey.HotKeyFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author yaLong
 */
@Slf4j
public class KeySaveBox extends JButton {

	public KeySaveBox() {
		this.setText("保存按键配置");
		this.addActionListener(listener());
	}

	public static KeySaveBox builder() {
		return new KeySaveBox();
	}

	private ActionListener listener() {
		return e -> {
			try {
				//检查文件合法性
				String trim = FrameInnerCache.keyTextPane.getText().strip();
				ArrayList<String> list = DiyKeyUtil.parseString(trim);
				Map<Integer, HotKeyConsumer> map = DiyKeyUtil.parseKey2Consumer(list);
				DiyKeyUtil.saveFile(trim);
				HotKeyFactory.applyDiyKey(map);
			} catch (Exception ex) {
				FrameTipUtil.errorOccur(ex.getMessage());
				log.error("快捷键设置失败", ex);
			}

		};
	}

}
