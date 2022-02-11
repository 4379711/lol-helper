package yalong.site.frame.bo;

/**
 * @author yaLong
 */
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

    public String getValue() {
        return value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public Integer getIndex() {
        return index;
    }

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
