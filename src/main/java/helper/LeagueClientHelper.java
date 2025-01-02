package helper;

import helper.cache.GameDataCache;
import helper.exception.NoProcessException;
import helper.frame.MainFrame;
import helper.frame.utils.FrameTipUtil;
import helper.services.hotkey.HotKeyService;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;

/**
 * @author @_@
 */
@Slf4j
public class LeagueClientHelper {

	public static void main(String[] args) {
		MainFrame.start();
		String msg = "";
			try {
				ClientStarter clientStarter = new ClientStarter();
				clientStarter.initLcu();
				clientStarter.initSgp();
				GameDataCache.cacheLcuAll();
				MainFrame.showFrame();
				HotKeyService.start();
				clientStarter.startWSS();
			} catch (NoProcessException e) {
				msg = "请先启动游戏";
			} catch (ConnectException e) {
				msg = "游戏客户端连接失败";
			} catch (Exception e) {
				msg = e.getMessage();
			}
		if (!msg.isEmpty()) {
			MainFrame.hiddenFrame();
			FrameTipUtil.errorOccur(msg);
		}
	}

}