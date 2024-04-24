package yalong.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yalong
 */
@Data
@AllArgsConstructor
public class ChampionBO {

	/**
	 * 英雄id
	 */
	private int id;
	/**
	 * 英雄中文名
	 */
	private String name;

}
