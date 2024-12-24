package helper.frame.utils;

import helper.cache.AppCache;
import helper.cache.FrameInnerCache;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author @_@
 * @date 2022/2/12
 */
@Slf4j
public class FrameMsgUtil {
	public static void sendLine(String msg) {
		if ("".equals(msg) || msg == null) {
			return;
		}
		if (FrameInnerCache.resultTextPane != null) {
			AppCache.resultPanelMsgList.add(msg);
			setLines(AppCache.resultPanelMsgList);
		}
	}

	public static void setLines(List<String> lines) {
		AppCache.resultPanelMsgList = new ArrayList<>();
		AppCache.resultPanelMsgList.addAll(lines);
		String timeStr = new SimpleDateFormat("[HH:mm:ss]  ").format(new Date());
		if (FrameInnerCache.resultTextPane != null) {
			String string = AppCache.resultPanelMsgList.stream().map(i -> timeStr + i + System.lineSeparator()).collect(Collectors.joining());
			FrameInnerCache.resultTextPane.setText(string);
		}

	}

	public static void clear() {
		if (FrameInnerCache.resultTextPane != null) {
			AppCache.resultPanelMsgList = new ArrayList<>();
			FrameInnerCache.resultTextPane.setText(null);
		}
	}

	public static void helpMsg() {
		ArrayList<String> list = new ArrayList<>();
		list.add("喷人语录,游戏内会每次发送一行");
		list.add("如果想发送所有人消息,在游戏聊天框内自己下拉选择,或在喷人语录每行开头加/all ");
		list.add("按DELETE建,标记喷人语句为已屏蔽,方便对照修改");
		list.add("按END键,一键夸人");
		list.add("按HOME建,一键喷人");
		list.add("按F1发送队友战绩和得分,可在选英雄界面使用");
		list.add("按F2发送对方战绩和得分,只能在游戏开始后使用");
		list.add("秒选/禁用英雄选择英雄后自动生效");
		list.add("选择英雄并确认后,可选择炫彩皮肤(即使没有原皮肤)");
		list.add("每个键位用空格分隔,负数代表延迟多少毫秒执行(一秒等于1000毫秒)");
		list.add("鼠标点击软件界面以外时,自动保存修改");
		list.add("战绩查询,输入名字后按回车键开始查询,不输入直接回车查自己的战绩");
		list.add("双击战绩可以展开该局对局详情,再次双击可以收起对局详情");
		list.add("对局详情对着召唤师名右键可以复制召唤师名字");
		list.add("也可以直接双击召唤师名可直接查询此人战绩");
		list.add("双击鼠标左键清空当前显示内容");
		setLines(list);
	}

}
