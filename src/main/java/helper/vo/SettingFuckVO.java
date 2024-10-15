package helper.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class SettingFuckVO {
    @Schema(description = "喷人文本")
    private List<String> fuckText;
}
