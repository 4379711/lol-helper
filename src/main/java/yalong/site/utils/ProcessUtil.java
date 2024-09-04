package yalong.site.utils;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.LeagueClientBO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * windows进程处理工具
 *
 * @author yaLong
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
		String cmd = "WMIC PROCESS WHERE \"name='LeagueClientUx.exe'\" GET commandline";
		BufferedReader reader = null;
		Process process = null;
		LeagueClientBO leagueClientBO = new LeagueClientBO();
		try {
			process = Runtime.getRuntime().exec(cmd);
			// windows 命令必须gbk编码
			reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
			String line;

			while ((line = reader.readLine()) != null) {
				Matcher appPortMatcher = appPortPattern.matcher(line);
				Matcher tokenPatternMatcher = tokenPattern.matcher(line);
				Matcher reginPatternMatcher = reginPattern.matcher(line);
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
			return leagueClientBO;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					log.error("查询lol进程失败", e);
				}
			}
			if (process != null) {
				process.getErrorStream().close();
				process.getOutputStream().close();
			}
		}

	}
}
