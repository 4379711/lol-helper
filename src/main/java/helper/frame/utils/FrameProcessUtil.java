package helper.frame.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author @_@
 */
public class FrameProcessUtil {
	final static String LOCK_FILE = "lol-helper.lock";
	static RandomAccessFile raf = null;

	/**
	 * 查看文件是否有锁 用于判断软件是否重复打开
	 *
	 * @ Return 如果打开过,返回true
	 */
	public static boolean checkFileLock() {
		try {
			File file = new File(LOCK_FILE);
			raf = new RandomAccessFile(file, "rws");
			// 尝试获取独占锁
			FileLock lock = raf.getChannel().tryLock();
			return lock == null;
		} catch (Exception e) {
			return true;
		}
	}

}
