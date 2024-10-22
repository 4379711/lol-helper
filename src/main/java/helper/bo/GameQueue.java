package helper.bo;

import lombok.Data;

/**
 * 游戏地图模式
 *
 * @author WuYi
 */
@Data
public class GameQueue {
	private Integer id;
	private String name;
	private String gameMode;
	private Integer mapId;
	private String isVisible;
	private boolean select;
}
