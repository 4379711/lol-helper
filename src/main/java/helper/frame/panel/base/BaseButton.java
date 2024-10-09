package helper.frame.panel.base;

import javax.swing.*;
import java.awt.*;

/**
 * @author @_@
 * @date 2022/2/14
 */
public class BaseButton extends JButton {
	public BaseButton() {
		this.setFocusPainted(false);
		Dimension dimension = new Dimension(200, 25);
		this.setOpaque(false);
		this.setSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
	}

}
