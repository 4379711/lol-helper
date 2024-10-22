package helper.site;

import helper.site.cache.GameDataCache;
import helper.site.exception.NoProcessException;
import helper.site.exception.RepeatProcessException;
import helper.site.frame.MainFrame;
import helper.site.frame.utils.FrameTipUtil;
import helper.site.services.hotkey.HotKeyService;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;

/**
 * @author @_@
 */
@Slf4j
public class LeagueClientHelper {

	public static void main(String[] args) {
		boolean checked = MainFrame.checkFileLock();
		if (checked) {
			System.exit(0);
		}
		MainFrame.start();
		while (true) {
			String msg = "";
			try {
				ClientStarter clientStarter = new ClientStarter();
				clientStarter.initLcu();
				clientStarter.initSgp();
				GameDataCache.cacheLcuAll();
				MainFrame.showFrame();
				HotKeyService.start();
				clientStarter.listenGameStatus();
			} catch (RepeatProcessException ignored) {
				msg = "工具已打开请查看任务栏或系统托盘";
			} catch (NoProcessException ignored) {
				msg = "请先启动游戏";
			} catch (ConnectException ignored) {
				msg = "游戏客户端连接失败";
			} catch (Exception e) {
				msg = e.getMessage();
				log.error(msg, e);
			}
			MainFrame.hiddenFrame();
			int running = FrameTipUtil.continueRun(msg);
			if (running != 0) {
				System.exit(0);
			}
		}
	}

}