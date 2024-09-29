package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.GameDataCache;
import yalong.site.exception.NoProcessException;
import yalong.site.exception.RepeatProcessException;
import yalong.site.frame.MainFrame;
import yalong.site.services.hotkey.HotKeyService;

import java.net.ConnectException;

/**
 * @author yaLong
 */
@Slf4j
public class LeagueClientHelper {

	public static void main(String[] args) {
		MainFrame.start();
		while (true) {
			String msg = "";
			try {
				MainFrame.checkFileLock();
				ClientStarter clientStarter = new ClientStarter();
				clientStarter.initLcu();
				clientStarter.initSgp();
				GameDataCache.cacheLcuAll();
				MainFrame.showFrame();
				HotKeyService.start();
				clientStarter.listenGameStatus();
			} catch (NoProcessException ignored) {
				msg = "请先启动游戏";
			} catch (ConnectException ignored) {
				msg = "游戏客户端连接失败";
			} catch (RepeatProcessException ignored) {
				msg = "工具已打开请查看任务栏或系统托盘";
			} catch (Exception e) {
				msg = e.getMessage();
				log.error(msg, e);
			}
			MainFrame.hiddenFrame();
			int running = MainFrame.continueRun(msg);
			if (running != 0) {
				System.exit(0);
			}
		}
	}

}