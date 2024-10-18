package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
public class SummonerBO {
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String puuid;
}
