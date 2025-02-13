package helper.bo;

import lombok.Data;

/**
 * @author @_@
 */
@Data
public class SkinBO {
	public SkinBO(Integer id, String name, String contentId) {
		this.id = id;
		this.name = name;
		this.contentId = contentId;
	}
	public SkinBO(String uncenteredSplashPath, Integer id, String name,String contentId) {
		this.uncenteredSplashPath = uncenteredSplashPath;
		this.id = id;
		this.name = name;
	}

	public SkinBO(String uncenteredSplashPath, Integer id, String name) {
		this.uncenteredSplashPath = uncenteredSplashPath;
		this.id = id;
		this.name = name;
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
	/**
	 * 皮肤地址
	 */
	private String uncenteredSplashPath;



}
