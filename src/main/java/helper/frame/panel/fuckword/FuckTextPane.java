package helper.frame.panel.fuckword;

import helper.cache.AppCache;
import helper.frame.ui.MyTabbedPaneUI;
import helper.services.word.GarbageWord;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * @author @_@
 */
public class FuckTextPane extends JTextPane {
	private final FuckTextPane fuckTextPane;

	private FuckTextPane() {
		fuckTextPane = this;
		this.setEditable(true);
		this.setOpaque(false);
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
				String text = fuckTextPane.getText();
				GarbageWord.saveFile(text);
				// 重新加载到内存
				AppCache.garbageWordList = GarbageWord.loadWord();
			}
		};
	}

	public static FuckTextPane builder() {
		return new FuckTextPane();
	}

	private void loadKey() {
		ArrayList<String> strings = GarbageWord.loadWord();
		String join = String.join(System.lineSeparator(), strings);
		this.setText(join);
	}

}
