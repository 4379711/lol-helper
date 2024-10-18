package helper.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author WuYi
 */
public enum EditEnum {
    @Schema(description = "增加")
    ADD,
    @Schema(description = "删除")
    DELETE,
    @Schema(description = "更新")
    UPDATE
}
