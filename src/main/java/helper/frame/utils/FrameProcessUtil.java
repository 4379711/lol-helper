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

	/**
	 * 查看文件是否有锁 用于判断软件是否重复打开
	 *
	 * @ Return 如果打开过,返回true
	 */
	public static boolean checkFileLock() {
		try {
			File file = new File(LOCK_FILE);
			//用于检测软件是否重复打开
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			FileChannel channel = raf.getChannel();
			// 尝试获取独占锁
			FileLock lock = channel.tryLock();
			if (lock == null) {
				raf.close();
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return true;
		}
	}

}
