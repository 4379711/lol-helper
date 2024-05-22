package yalong.site.frame.panel.match.listener;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.GameMatchHistoryBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.panel.match.HistoryDetail;
import yalong.site.frame.panel.match.HistoryLine;
import yalong.site.frame.utils.MatchHistoryUtil;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * 选择打开详情
 */
@Slf4j
public class SelectDetailListener extends MouseAdapter {
    private Long gameId;
    /**
     * 是否打开详情页
     */
    private boolean isShowDetail = false;
    HistoryDetail historyDetail = null;

    public SelectDetailListener(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && !isShowDetail && e.getSource() instanceof HistoryLine historyLine) {
            GameMatchHistoryBO matchHistoryBO = null;
            try {
                matchHistoryBO = AppCache.api.getGameMatchHistoryByGameId(gameId);
                if (matchHistoryBO != null && MatchHistoryUtil.isQueryHistoryDetail(matchHistoryBO.getQueueId())) {
                    Component[] components = historyLine.getParent().getComponents();
                    for (Component component : components) {
                        if (!component.equals(historyLine)) {
                            component.setVisible(false);
                        }
                    }

                    historyDetail = HistoryDetail.build(matchHistoryBO);
                    FrameInnerCache.matchPanel.panelContainer.add(historyDetail);
                    isShowDetail = true;
                }
            } catch (IOException ex) {
                log.error("查询对局详情错误");
            }

        } else if (e.getClickCount() == 2 && isShowDetail && e.getSource() instanceof HistoryLine && isShowDetail) {
            FrameInnerCache.matchPanel.panelContainer.remove(historyDetail);
            FrameInnerCache.matchPanel.showAllComponent();
            isShowDetail = false;
        }
    }


}
