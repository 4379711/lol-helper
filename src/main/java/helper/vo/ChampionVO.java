package helper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author @_@
 */
@Data
@AllArgsConstructor
public class ChampionVO {

	/**
	 * 英雄id
	 */
	@Schema(description = "英雄id")
	private Integer id;
	/**
	 * 英雄中文名
	 */
	@Schema(description = "英雄中文名")
	private String name;

}
