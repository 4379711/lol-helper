package helper.site.frame.panel.base;

import javax.swing.*;
import java.awt.*;

/**
 * @author @_@
 */
public class BaseComboBox<E> extends JComboBox<E> {

	public BaseComboBox() {
		Dimension dimension = new Dimension(200, 25);
		this.setOpaque(false);
		this.setBorder(null);
		this.setSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.setMaximumRowCount(15);
	}

}
