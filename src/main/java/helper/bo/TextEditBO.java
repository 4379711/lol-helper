package helper.bo;

import helper.enums.EditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author WuYi
 */
@Data
public class TextEditBO {
    @Schema(description = "需要操作的文本", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String text;
    @Schema(description = "操作类型",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private EditEnum editType;
    @Schema(description = "操作下标")
    private Integer index;


}
