package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author WuYi
 */
@Data
public class BooleanBO {
    @Schema(description = "状态开关", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean flag;
}
