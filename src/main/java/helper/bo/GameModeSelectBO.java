package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class GameModeSelectBO {
    @Schema(description = "筛选模式")
    private List<Integer> gameModeSelect;
}
