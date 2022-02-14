package yalong.site.utils;

import com.qiyou.javaelf.operation.Keyboard;
import com.qiyou.javaelf.operation.Window;
import com.qiyou.javaelf.system.Elf;
import org.jawin.COMException;

import java.util.concurrent.TimeUnit;

/**
 * 驱动级别模拟发送消息
 *
 * @author yaLong
 */
public class DmUtil {
    private final Window window;
    private final Keyboard keyboard;

    public DmUtil() throws COMException {
//        GlobalSetting.copy_dlls();
        Elf.init();
        Elf elf = new Elf();
        window = new Window(elf);
        keyboard = new Keyboard(elf);
    }

    /**
     * 获取游戏窗口句柄
     */
    public int getHwnd() throws COMException {
        //游戏进程
        String pid = window.EnumProcess(new Object[]{"League of Legends.exe"});
        if ("".equals(pid)) {
            return 0;
        }
        //获取窗口句柄
        return window.FindWindowByProcessId(new Object[]{pid, "RiotWindowClass", "League of Legends"});
    }

    /**
     * 等待按键
     *
     * @param waitSecond 等待时间(秒),0代表死等
     * @return 0 超时,1 按键按下
     */
    public int waitKey(int keyCode, int waitSecond) throws COMException {
        //等待按键指令
        return keyboard.WaitKey(new Object[]{keyCode, waitSecond * 1000});
    }

    public void sendMessage(int hwnd, String message) throws COMException {
        if (message != null && !"".equals(message.trim())) {
            try {
                //发送回车
                keyboard.KeyPress(new Object[]{13});
                TimeUnit.MILLISECONDS.sleep(500);
                //发送消息
                window.SendString(new Object[]{hwnd, message+"\n"});
                TimeUnit.MILLISECONDS.sleep(500);
                //发送回车
                keyboard.KeyPress(new Object[]{13});
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
