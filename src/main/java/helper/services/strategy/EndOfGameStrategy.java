package helper.services.strategy;

import com.sun.jna.platform.win32.WinDef;
import helper.bo.SpgProductsMatchHistoryBO;
import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import helper.cache.GameDataCache;
import helper.frame.panel.history.BlackListAddPanel;
import helper.services.lcu.LinkLeagueClientApi;
import helper.utils.Win32Util;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @author @_@
 */
@Slf4j
public class EndOfGameStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;

	public EndOfGameStrategy(LinkLeagueClientApi api) {
		this.api = api;
	}

	private void showBlackListAddFrame() throws IOException {
		if (AppCache.settingPersistence.getBlackListAddVisible()) {
			List<SpgProductsMatchHistoryBO> productsMatchHistory = AppCache.sgpApi.getProductsMatchHistoryByPuuid(GameDataCache.leagueClient.getRegion(), GameDataCache.me.getPuuid(), 0, 1);
			SpgProductsMatchHistoryBO spgProductsMatchHistoryBO = productsMatchHistory.get(0);
			//上把玩家是否失败
			if (!productsMatchHistory.get(0).getJson().getParticipants().stream().filter(item -> item.getPuuid().equals(GameDataCache.me.getPuuid())).findFirst().get().isWin()) {
				WinDef.RECT lolWindows = Win32Util.findWindowsLocation(null, "League of Legends");
				int x = lolWindows.right;
				int y = lolWindows.top;
				//已找到但页面未弹出
				if (x == 0 && y == 0) {
					return;
				}
				JFrame jFrame = new JFrame();
				FrameInnerCache.blackListAddFrame = jFrame;
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// 禁用窗口的最大化按钮
				jFrame.setResizable(false);
				jFrame.setSize(280, 400);
				BlackListAddPanel blackListAddPanel = new BlackListAddPanel(spgProductsMatchHistoryBO, GameDataCache.me.getPuuid());
				jFrame.add(blackListAddPanel);

				Point p = new Point(x, y);
				jFrame.setLocation(p);
				jFrame.setVisible(true);
				GameDataCache.endOfGameFlag = true;
			}
		}
	}
	@Override
	public void doThis() {
		if (!GameDataCache.endOfGameFlag) {
			try {
				showBlackListAddFrame();
			} catch (IOException e) {
				log.error("添加黑名单生成页面错误", e);
			}
		}
		if (AppCache.settingPersistence.getAutoPlayAgain()) {
			//再来一局
			try {
				String s = api.playAgain();
				log.info(s);
			} catch (Exception e) {
				log.error("再来一局失败", e);
			}
		}
	}
}
