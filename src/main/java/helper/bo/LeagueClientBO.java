package helper.bo;

import lombok.Data;

/**
 * 客户端信息
 *
 * @author @_@
 */
@Data
public class LeagueClientBO {
	/**
	 * 端口
	 */
	private String port;

	/**
	 * 密钥
	 */
	private String token;

	/**
	 * 区域
	 */
	private String region;
}
