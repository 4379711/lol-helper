package helper.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author @_@
 */

public enum SystemEnum {

	/**
	 * 业务运营中心pc端
	 */
	@Schema(description = "业务运营中心pc端")
	BUSINESS_SYSTEM_PC,

	/**
	 * 业务运营中心移动端
	 */
	@Schema(description = "业务运营中心移动端")
	BUSINESS_SYSTEM_MOVE,

	/**
	 * 后台管理中心
	 */
	@Schema(description = "后台管理中心")
	BACKGROUND_MANAGER,

	/**
	 * 租户系统
	 */
	@Schema(description = "租户管理中心")
	TENANT_SYSTEM
}

