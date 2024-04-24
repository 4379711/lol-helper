package yalong.site;

import yalong.site.frame.MainFrame;

/**
 * @author yaLong
 */
public class LeagueClientHelper {

	public static void main(String[] args) {
		MainFrame.start();
		while (true) {
			try {
				ClientStarter.start();
			} catch (Exception e) {
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