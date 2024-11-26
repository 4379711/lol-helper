package helper.frame.panel.history;

import helper.cache.AppCache;
import helper.frame.bo.ItemBO;
import helper.frame.panel.base.BaseComboBox;
import helper.frame.utils.FrameConfigUtil;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author WuYi
 */
@Slf4j
public class HistoryCountBox extends BaseComboBox<ItemBO> {

    public HistoryCountBox() {
        this.setItems();
        this.addItemListener(listener());
    }

    public void setItems() {
        this.addItem(new ItemBO("-1", "评分胜率战绩数量(默认20)"));
        this.addItem(new ItemBO("5", "5"));
        this.addItem(new ItemBO("10", "10"));
        this.addItem(new ItemBO("20", "20"));
        this.addItem(new ItemBO("30", "30"));
        this.addItem(new ItemBO("40", "40"));
        this.addItem(new ItemBO("50", "50"));
        this.addItem(new ItemBO("100", "100"));
        this.addItem(new ItemBO("200", "200"));
        Integer historyCount = AppCache.settingPersistence.getHistoryCount();
        if (!historyCount.equals(20)) {
            this.setSelectedItem(new ItemBO(historyCount.toString(), historyCount.toString()));
        }
    }

    private ItemListener listener() {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ItemBO item = (ItemBO) e.getItem();
                int count = Integer.parseInt(item.getValue());
                if (count >= 5) {
                    AppCache.settingPersistence.setHistoryCount(count);
                } else {
                    AppCache.settingPersistence.setHistoryCount(20);
                }
                FrameConfigUtil.save();
            }
        };

    }

}
