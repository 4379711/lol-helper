package yalong.site;

import yalong.site.frame.MainFrame;
import yalong.site.frame.button.AutoEnsureButton;
import yalong.site.frame.button.BaseButton;
import yalong.site.frame.combox.GameStatusBox;
import yalong.site.frame.panel.*;

import java.util.concurrent.TimeUnit;

/**
 * @author yaLong
 * @date 2022/2/10
 */
public class Test {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        TabPane tab = new TabPane();
        ClientPanel clientPanel = new ClientPanel();
        GamePanel gamePanel = new GamePanel();

        BaseButton button1 = new AutoEnsureButton();
        BaseButton button2 = new AutoEnsureButton();

        clientPanel.add(button2);
        gamePanel.add(button1);
        GameStatusBox gameStatusBox = new GameStatusBox(null);
        clientPanel.add(gameStatusBox);
        MessagePanel messagePanel = new MessagePanel();
        ResultPanel resultPanel = new ResultPanel();
        tab.add(clientPanel);
        tab.add(gamePanel);
        tab.add(messagePanel);
        tab.add(resultPanel);

        frame.add(tab);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);

        for (int i = 0; i < 50; i++) {
            ResultPanel.resultArea.append("aaa" + i + "\n");
            // TODO: 2022/2/11 消息滚动到光标位置
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
