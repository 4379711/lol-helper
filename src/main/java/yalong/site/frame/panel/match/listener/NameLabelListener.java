package yalong.site.frame.panel.match.listener;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ProductsMatchHistoryBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 姓名组件监听事件
 *
 * @author WuYi
 */
@Slf4j
public class NameLabelListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() instanceof JLabel jLabel) {
            // 创建弹出菜单
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item1 = new JMenuItem("复制");
            menu.add(item1);
            item1.addActionListener(e1 -> {
                StringSelection stringSelection = new StringSelection(jLabel.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            });
            menu.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.getClickCount() == 2 && e.getSource() instanceof JLabel jLabel) {
            try {
                List<String> list = new ArrayList<String>();
                list.add(jLabel.getText());
                List<SummonerInfoBO> summonerInfoBOList = AppCache.api.getV2InfoByNameList(list);
                String puuid = summonerInfoBOList.get(0).getPuuid();
                ProductsMatchHistoryBO pmh = AppCache.api.getProductsMatchHistoryByPuuid(puuid, 0, FrameSetting.PAGE_SIZE - 1);
                FrameInnerCache.matchPanel.clear();
                FrameInnerCache.matchPanel.setData(pmh, puuid);
                FrameInnerCache.matchPanel.showAllComponent();
            } catch (IOException ex) {
                log.error("查询召唤师:" + jLabel.getName() + "错误");
            }
        }
    }
}
