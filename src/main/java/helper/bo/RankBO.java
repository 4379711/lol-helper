package helper.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * 当前段位选择
 *
 * @author @_@
 * @date 2022/2/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankBO {
	@NotBlank
	@Schema(description = "段位游戏模式", requiredMode = Schema.RequiredMode.REQUIRED)
	private String firstRank;
	@NotBlank
	@Schema(description = "段位", requiredMode = Schema.RequiredMode.REQUIRED)
	private String secondRank;
	@NotBlank
	@Schema(description = "段位等级", requiredMode = Schema.RequiredMode.REQUIRED)
	private String thirdRank;

	@Schema(hidden = true)
	@JsonIgnore
	public boolean isNull() {
		return firstRank == null || secondRank == null || thirdRank == null;
	}
}
