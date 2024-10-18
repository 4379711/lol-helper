package helper.bo;

import helper.enums.RegionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
public class SgpMatchBO {
    @Schema(description = "开始下标", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer startIndex;
    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer count;
    @Schema(description = "召唤师puuid", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String puuid;
    @Schema(description = "所在大区",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private RegionEnum region;
}
