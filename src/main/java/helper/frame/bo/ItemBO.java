package helper.frame.bo;

import lombok.Data;

/**
 * @author @_@
 */
@Data
public class ItemBO {
	/**
	 * 实际用到的值
	 */
	private String value;
	/**
	 * 显示的值
	 */
	private String displayValue;
	/**
	 * 序号
	 */
	private Integer index;

	/**
	 * 其他信息
	 */
	private Object other;

	public ItemBO() {
	}

	public ItemBO(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	@Override
	public String toString() {
		return displayValue;
	}
}
