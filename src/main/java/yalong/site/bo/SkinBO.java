package yalong.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yalong
 */
@Data
@AllArgsConstructor
public class SkinBO {

	/**
	 * 皮肤id
	 */
	private Integer id;
	/**
	 * 皮肤中文名
	 */
	private String name;

}
