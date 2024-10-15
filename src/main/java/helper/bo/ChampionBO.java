package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author @_@
 */
@Data
@AllArgsConstructor
public class ChampionBO {

	/**
	 * 英雄id
	 */
	@Schema(description = "英雄id", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull
	private Integer championId;
}
