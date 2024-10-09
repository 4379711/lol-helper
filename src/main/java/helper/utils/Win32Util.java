package helper.utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;

/**
 * Win32api工具类
 *
 * @author WuYi
 */
@Slf4j
public class Win32Util {
	public static WinDef.RECT findWindowsLocation(String lpClassName, String lpWindowName) {
		WinDef.HWND hwnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);
		if (hwnd == null) {
			throw new RuntimeException("未找到窗口");
		}
		WinDef.RECT rect = new WinDef.RECT();
		User32.INSTANCE.GetWindowRect(hwnd, rect);
		return rect;
	}
}
