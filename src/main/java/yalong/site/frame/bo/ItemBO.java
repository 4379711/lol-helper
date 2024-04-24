package yalong.site.frame.bo;

import lombok.Getter;

/**
 * @author yaLong
 */
@Getter
public class ItemBO {
	/**
	 * 实际用到的值
	 */
	private final String value;
	/**
	 * 显示的值
	 */
	private final String displayValue;
	/**
	 * 序号
	 */
	private final Integer index;

	public ItemBO(String value, String displayValue) {
		this(value, displayValue, null);
	}

	public ItemBO(String value, String displayValue, Integer index) {
		this.value = value;
		this.displayValue = displayValue;
		this.index = index;
	}

	@Override
	public String toString() {
		return displayValue;
	}
}
