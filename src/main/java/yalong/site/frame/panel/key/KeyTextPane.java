package yalong.site.frame.panel.key;

import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.ui.MyTabbedPaneUI;
import yalong.site.frame.utils.DiyKeyUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author yaLong
 */
public class KeyTextPane extends JTextPane {

	private KeyTextPane() {
		FrameInnerCache.keyTextPane = this;
		this.setBackground(MyTabbedPaneUI.END_COLOR_SELECT);
		this.setBorder(null);
		loadKey();
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
