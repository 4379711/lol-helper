package yalong.site.frame.panel.match.listener;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.FrameSetting;
import yalong.site.frame.panel.match.SearchTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 搜索监听器
 *
 * @author WuYi
 */
@Slf4j
public class SearchListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof SearchTextField search) {
            String input = search.getText();
            SummonerInfoBO summonerInfo = null;
            if (input.isEmpty() && input.isBlank()) {
                JOptionPane.showMessageDialog(null, "输入为空", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    summonerInfo = AppCache.api.getV2InfoByNameList(new ArrayList<>() {{
                        add(input);
                    }}).get(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "未查询到召唤师：" + input, "提示", JOptionPane.INFORMATION_MESSAGE);
                    log.error("未查询到召唤师");
                }
                try {
                    if (summonerInfo != null) {
                        FrameInnerCache.matchPanel.setData(AppCache.api.getProductsMatchHistoryByPuuid(summonerInfo.getPuuid(), 0, FrameSetting.PAGE_SIZE - 1), summonerInfo.getPuuid());
                        FrameInnerCache.matchPanel.showAllComponent();
                        FrameInnerCache.matchPanel.resetIndex();

                    }
                } catch (IOException ex) {
                    log.error("查询战绩错误");
                }
            }

        }
    }
}
