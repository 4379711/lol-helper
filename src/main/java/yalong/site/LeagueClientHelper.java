package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.exception.NoProcessException;
import yalong.site.frame.MainFrame;
import yalong.site.services.hotkey.HotKeyService;

import java.net.ConnectException;

/**
 *
 * @author yaLong
 */
@Slf4j
public class LeagueClientHelper {

	public static void main(String[] args) {
		MainFrame.start();
		while (true) {
			String msg="";
			try {
				ClientStarter clientStarter = new ClientStarter();
				clientStarter.initLcu();
				clientStarter.cacheData();
				clientStarter.loadFrameData();
				MainFrame.showFrame();
				HotKeyService.start();
				clientStarter.listenGameStatus();
			}catch (NoProcessException ignored){
				msg="请先启动游戏";
			}catch (ConnectException ignored){
				msg="游戏客户端连接失败";
			}catch (Exception e) {
				msg=e.getMessage();
				log.error(msg,e);
			}
			MainFrame.hiddenFrame();
			int running = MainFrame.continueRun(msg);
			if (running != 0) {
				System.exit(0);
			}
		}
	}

}