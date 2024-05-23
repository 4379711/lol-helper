package yalong.site.frame.panel.base;

import javax.swing.*;
import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/14
 */
public class BaseCheckBox extends JCheckBox {
	public BaseCheckBox() {
		this.setSelected(true);
		this.setFocusPainted(false);
		Dimension dimension = new Dimension(200, 25);
		this.setOpaque(false);
		this.setBorder(null);
		this.setSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
	}

}
