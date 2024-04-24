package yalong.site.cache;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * @author yalong
 */
public class FrameSetting {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 250;
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss]: ");
	public static MutableAttributeSet GREEN_ATTR = new SimpleAttributeSet();
	public static MutableAttributeSet BLACK_ATTR = new SimpleAttributeSet();

	static {
		StyleConstants.setForeground(GREEN_ATTR, Color.GREEN);
		StyleConstants.setForeground(BLACK_ATTR, Color.black);
	}
}
