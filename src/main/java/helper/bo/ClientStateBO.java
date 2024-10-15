package helper.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author WuYi
 */
@Data
public class ClientStateBO {
    @Schema(description = "客户端状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull("state为空")
    private String state;
}
