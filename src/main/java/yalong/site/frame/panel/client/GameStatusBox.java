package yalong.site.frame.panel.client;

import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;
import yalong.site.services.LeagueClientService;

import java.awt.event.ItemListener;
import java.io.IOException;

/**
 * @author yaLong
 */
public class GameStatusBox extends BaseComboBox<ItemBO> {

    private final LeagueClientService service;

    public GameStatusBox(LeagueClientService service) {
        this.service = service;
        this.setItems();
        this.addItemListener(changeStatusListener());
    }

    public void setItems() {
        this.addItem(new ItemBO("chat", "在线"));
        this.addItem(new ItemBO("away", "离开"));
        this.addItem(new ItemBO("dnd", "游戏中"));
        this.addItem(new ItemBO("offline", "离线"));
        this.addItem(new ItemBO("mobile", "手机在线"));
    }

    private ItemListener changeStatusListener() {
        return e -> {
            int stateChange = e.getStateChange();
            //选中返回1
            if (stateChange == 1) {
                ItemBO item = (ItemBO) e.getItem();
                try {
                    service.api.changeStatus(item.getValue());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

    }

}
