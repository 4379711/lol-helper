package yalong.site.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yalong
 */
@Data
public class SkinBO {
	public SkinBO(Integer id, String name, String contentId) {
		this.id = id;
		this.name = name;
		this.contentId = contentId;
	}

	public SkinBO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * 皮肤id
	 */
	private Integer id;
	/**
	 * 皮肤中文名
	 */
	private String name;
	/**
	 * 皮肤增强
	 */
	private String contentId;

}
