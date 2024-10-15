package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 游戏地图模式
 *
 * @author WuYi
 */
@Data
public class GameQueue {
	@Schema(description = "模式ID")
	private Integer id;
	@Schema(description = "中文名")
	private String name;
	@Schema(description = "英文")
	private String gameMode;
	@Schema(description = "地图ID")
	private Integer mapId;
	@Schema(description = "模式是否激活")
	private String isVisible;
	@Schema(description = "是否筛选中选择", hidden = true)
	private boolean select;
}
