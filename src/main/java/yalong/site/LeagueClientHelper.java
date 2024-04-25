package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.frame.MainFrame;
import yalong.site.services.hotkey.HotKeyService;

/**
 *
 * @author yaLong
 */
@Slf4j
public class LeagueClientHelper {

	public static void main(String[] args) {
		MainFrame.start();
		HotKeyService.start();
		while (true) {
			try {
				ClientStarter.start();
			} catch (Exception e) {
				log.error("主进程错误",e);
				MainFrame.hiddenFrame();
				int running = MainFrame.continueRun(e.getMessage());
				if (running == 1) {
					System.exit(0);
				} else {
					MainFrame.showFrame();
				}
			}
		}
	}

}