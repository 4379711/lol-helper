package helper.frame.panel.client;

import ch.qos.logback.core.util.StringUtil;
import helper.bo.ChampionBO;
import helper.cache.FrameSetting;
import helper.cache.GameDataCache;
import helper.frame.panel.base.SearchTextField;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.function.Consumer;

/**
 * 选择英雄界面
 */
@Slf4j
public class ChampionSelectFrame extends JFrame {

	private final JPanel championPanel = new JPanel();
	private final JPanel allPanel = new JPanel();

	private final JPanel searchPanel = new JPanel();
	private final Consumer<String> callBack;
	private final JFrame jFrame;

	public ChampionSelectFrame(String topic, Consumer<String> callBack) {
		jFrame = this;
		this.callBack = callBack;
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
		this.setIconImage(image);
		this.setSize(FrameSetting.CHAMPION_SELECT_WIDTH, FrameSetting.CHAMPION_SELECT_HEIGHT);
		//窗口居中
		this.setLocationRelativeTo(null);
		this.setName(topic);
		searchPanel.setPreferredSize(new Dimension(FrameSetting.CHAMPION_SELECT_WIDTH, 50));
		SearchTextField searchText = new SearchTextField(30);
		searchText.addActionListener(actionListener());
		searchText.setPreferredSize(new Dimension(FrameSetting.CHAMPION_SELECT_WIDTH / 3, 30));
		searchPanel.add(searchText);
		allPanel.add(searchPanel);

		championPanel.setPreferredSize(new Dimension(FrameSetting.CHAMPION_SELECT_WIDTH, FrameSetting.CHAMPION_SELECT_HEIGHT - 50));
		allPanel.add(championPanel);
		this.add(allPanel);
		championPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

		this.addWindowListener(listener());
	}

	public void createButtons(String filter) {
		GameDataCache.cacheLcuChampion();
		championPanel.removeAll();
		//创建按钮组
		ButtonGroup group = new ButtonGroup();

		for (ChampionBO championBO : GameDataCache.allChampion) {
			String name = championBO.getName();
			if (StringUtil.isNullOrEmpty(filter) || name.contains(filter)) {
				JRadioButton button = new JRadioButton(name);
				if (callBack != null) {
					button.addActionListener(e -> {
								//回调
								callBack.accept(e.getActionCommand());
								//销毁窗口
								jFrame.dispose();
							}
					);
				}
				button.setPreferredSize(new Dimension(100, 30));
				group.add(button);
				championPanel.add(button);
			}
		}
		championPanel.revalidate();
		championPanel.repaint();
	}

	private ActionListener actionListener() {
		return e -> {
			String string = e.getActionCommand();
			createButtons(string);
			championPanel.repaint();
		};
	}

	private WindowListener listener() {
		return new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				createButtons(null);
			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		};
	}
}
