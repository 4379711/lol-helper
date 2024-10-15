package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author @_@
 */
@Data
public class SkinBO {
	/**
	 * 皮肤id
	 */
	@NotNull
	@Schema(description = "皮肤id", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer skinId;
	/**
	 * 皮肤增强
	 */
	@Schema(description = "皮肤增强")
	private String contentId;


}
