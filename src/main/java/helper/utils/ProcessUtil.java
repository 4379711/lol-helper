package helper.utils;

import helper.bo.LeagueClientBO;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * windows进程处理工具
 *
 * @author @_@
 */
@Slf4j
public class ProcessUtil {
	public static Pattern appPortPattern = Pattern.compile("--app-port=(\\d+)");
	public static Pattern tokenPattern = Pattern.compile("--remoting-auth-token=([\\w-]+)");
	public static Pattern reginPattern = Pattern.compile("--rso_platform_id=([\\w-]+)");

	/**
	 * 通过进程名查询出进程的启动命令,解析出需要的客户端token和端口
	 */
	public static LeagueClientBO getClientProcess() throws IOException {
		LeagueClientBO leagueClientBO = new LeagueClientBO();
		// 创建系统信息对象
		SystemInfo systemInfo = new SystemInfo();
		OperatingSystem os = systemInfo.getOperatingSystem();

		// 获取当前运行的进程
		List<OSProcess> processes = os.getProcesses();

		// 查找目标进程
		for (OSProcess process : processes) {
			if (process.getName().equalsIgnoreCase("LeagueClientUx")) {
				Matcher appPortMatcher = appPortPattern.matcher(process.getCommandLine());
				Matcher tokenPatternMatcher = tokenPattern.matcher(process.getCommandLine());
				Matcher reginPatternMatcher = reginPattern.matcher(process.getCommandLine());
				if (tokenPatternMatcher.find()) {
					leagueClientBO.setToken(tokenPatternMatcher.group(1));
				}
				if (appPortMatcher.find()) {
					leagueClientBO.setPort(appPortMatcher.group(1));
				}
				if (reginPatternMatcher.find()) {
					leagueClientBO.setRegion(reginPatternMatcher.group(1));
				}
			}
		}
		return leagueClientBO;
	}

}
