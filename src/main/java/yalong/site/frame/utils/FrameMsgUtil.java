package yalong.site.frame.utils;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaLong
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
		list.add("如果想发送所有人消息,在游戏聊天框内自己下拉选择");
		list.add("按DELETE建,标记喷人语句为已屏蔽,方便对照修改");
		list.add("按END键,一键夸人");
		list.add("按HOME建,一键喷人");
		list.add("按F1发送队友战绩和得分,可在选英雄界面使用");
		list.add("按F2发送对方战绩和得分,只能在游戏开始后使用");
		list.add("盲仔按T,自动插眼+闪现+W,默认按键为4DW");
		list.add("秒选/禁用英雄选择英雄后自动生效");
		list.add("选择英雄并确认后,可选择炫彩皮肤(即使没有原皮肤)");
		list.add("双击鼠标左键清空当前显示内容");
		setLines(list);
	}

}
