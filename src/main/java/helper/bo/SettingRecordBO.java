package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class SettingRecordBO {
     @Schema(description = "发送战绩")
     private Boolean sendScore;
     @Schema(description = "显示队友战绩")
     private Boolean showTeamRecord;
     @Schema(description = "选择筛选模式")
     private List<Integer> gameModeSelect;
}
