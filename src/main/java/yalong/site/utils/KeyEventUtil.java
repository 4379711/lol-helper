package yalong.site.utils;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;

/**
 * @author yaLong
 */
@Slf4j
public class KeyEventUtil {
	private static final int KEY_DELAY = 100;

	public static void sendMsg(String s) {
		RobotUtil.ROBOT.keyPress(KeyEvent.VK_ENTER);
		RobotUtil.ROBOT.delay(KEY_DELAY);
		RobotUtil.ROBOT.keyRelease(KeyEvent.VK_ENTER);
		RobotUtil.ROBOT.delay(KEY_DELAY);
		KeyEventUtil.sendString(s);
		RobotUtil.ROBOT.keyPress(KeyEvent.VK_ENTER);
		RobotUtil.ROBOT.delay(KEY_DELAY);
		RobotUtil.ROBOT.keyRelease(KeyEvent.VK_ENTER);
		RobotUtil.ROBOT.delay(KEY_DELAY);
	}

	/**
	 * 找进程的pid
	 *
	 * @param lpClassName  窗口类名
	 * @param lpWindowName 窗口名
	 * @return 进程的pid
	 */
	public static int findPid(String lpClassName, String lpWindowName) {
		WinDef.HWND hwnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);
		if (hwnd == null) {
			throw new RuntimeException("未找到窗口");
		}
		IntByReference pidRef = new IntByReference();
		User32.INSTANCE.GetWindowThreadProcessId(hwnd, pidRef);
		return pidRef.getValue();
	}

	private static WinUser.INPUT buildBaseInput() {
		WinUser.INPUT ip = new WinUser.INPUT();
		ip.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		ip.input.setType("ki");
		ip.input.ki.time = new WinDef.DWORD(Kernel32.INSTANCE.GetTickCount());
		ip.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
		return ip;
	}

	/**
	 * 键按下
	 */
	public static void keyPressed(int keyCode) {
		WinUser.INPUT ip = buildBaseInput();
		ip.input.ki.wScan = new WinDef.WORD(0);
		ip.input.ki.wVk = new WinDef.WORD(keyCode);
		ip.input.ki.dwFlags = new WinDef.DWORD(0);
		User32.INSTANCE.SendInput(new WinDef.DWORD(1), new WinUser.INPUT[]{ip}, ip.size());
	}

	/**
	 * 键抬起
	 */
	public static void keyReleased(int keyCode) {
		WinUser.INPUT ip = buildBaseInput();
		ip.input.ki.wScan = new WinDef.WORD(0);
		ip.input.ki.wVk = new WinDef.WORD(keyCode);
		ip.input.ki.dwFlags = new WinDef.DWORD(2);
		User32.INSTANCE.SendInput(new WinDef.DWORD(1), new WinUser.INPUT[]{ip}, ip.size());
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		StringBuilder unicode = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append(String.format("\\u%4s", Integer.toHexString(c)).replaceAll("\\s", "0"));
		}
		return unicode.toString();
	}

	/**
	 * unicode转字符串
	 */
	public static String unicode2String(String unicode) {
		StringBuilder string = new StringBuilder();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			string.append((char) data);
		}
		return string.toString();
	}

	public static void sendMsg(WinDef.HWND hWnd, String message) {
		byte[] bytes = message.getBytes();
		Pointer memory = new Memory(bytes.length);
		long meer = Pointer.nativeValue(memory);
		memory.setString(0, message);

		//定义一个消息结构体
		WinUser.COPYDATASTRUCT copyDataStruct = new WinUser.COPYDATASTRUCT();
		// 任意值
		copyDataStruct.dwData = new BaseTSD.ULONG_PTR(0);
		// 指定lpData内存区域的字节数
		copyDataStruct.cbData = bytes.length;
		// 发送给目标窗口所在进程的数据
		copyDataStruct.lpData = memory;
		copyDataStruct.write();

		Pointer pointer = copyDataStruct.getPointer();
		long peer = Pointer.nativeValue(pointer);
		User32.INSTANCE.SendMessage(hWnd, WinUser.WM_COPYDATA, new WinDef.WPARAM(0), new WinDef.LPARAM(peer));
		// 手动释放内存
		Native.free(meer);
		Native.free(peer);
		// 避免Memory对象被GC时重复执行Nativ.free()方法
		Pointer.nativeValue(pointer, 0);
		Pointer.nativeValue(memory, 0);
	}

	public static void sendString(String string) {
		for (char aChar : string.toCharArray()) {
			// 这里不可以直接new一个java数组,必须使用INPUT().toArray
			WinUser.INPUT[] inputs = (WinUser.INPUT[]) new WinUser.INPUT().toArray(2);
			inputs[0].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
			inputs[0].input.setType("ki");
			inputs[0].input.ki.time = new WinDef.DWORD(Kernel32.INSTANCE.GetTickCount());
			inputs[0].input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
			inputs[0].input.ki.wScan = new WinDef.WORD(aChar);
			//注意这里,当输入为KEYEVENTF_UNICODE时,wVk必须为0
			inputs[0].input.ki.wVk = new WinDef.WORD(0);
			inputs[0].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_UNICODE);

			inputs[1].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
			inputs[1].input.setType("ki");
			inputs[1].input.ki.time = new WinDef.DWORD(Kernel32.INSTANCE.GetTickCount());
			inputs[1].input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
			inputs[1].input.ki.wScan = new WinDef.WORD(aChar);
			inputs[1].input.ki.wVk = new WinDef.WORD(0);
			inputs[1].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP | WinUser.KEYBDINPUT.KEYEVENTF_UNICODE);
			User32.INSTANCE.SendInput(new WinDef.DWORD(2), inputs, inputs[0].size());
		}

	}
}
