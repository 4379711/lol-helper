package yalong.site.frame.panel.key;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.ui.MyTabbedPaneUI;
import yalong.site.frame.utils.DiyKeyUtil;
import yalong.site.frame.utils.FrameTipUtil;
import yalong.site.services.hotkey.HotKeyConsumer;
import yalong.site.services.hotkey.HotKeyFactory;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yaLong
 */
@Slf4j
public class KeyTextPane extends JTextPane {

	private KeyTextPane() {
		FrameInnerCache.keyTextPane = this;
		this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
		this.setBorder(null);
		loadKey();
		this.addFocusListener(focusListener());
	}

	private FocusListener focusListener() {
		return new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
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
			}
		};
	}

	public static KeyTextPane builder() {
		return new KeyTextPane();
	}

	private void loadKey() {
		ArrayList<String> list = DiyKeyUtil.loadKey();
		String collect = list.stream().map(i -> i + System.lineSeparator()).collect(Collectors.joining());
		this.setText(collect);

	}

}
