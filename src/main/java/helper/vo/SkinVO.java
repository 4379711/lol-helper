package helper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author @_@
 */
@Data
public class SkinVO {
	/**
	 * 皮肤id
	 */
	@Schema(description = "皮肤id")
	private Integer skinId;
	/**
	 * 皮肤中文名
	 */
	@Schema(description = "皮肤中文名")
	private String name;
	/**
	 * 皮肤增强
	 */
	@Schema(description = "皮肤增强")
	private String contentId;

	public SkinVO(Integer skinId, String name, String contentId) {
		this.skinId = skinId;
		this.name = name;
		this.contentId = contentId;
	}

	public SkinVO(Integer skinId, String name) {
		this.skinId = skinId;
		this.name = name;
	}

}
