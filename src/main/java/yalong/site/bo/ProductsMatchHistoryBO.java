package yalong.site.bo;

import lombok.Data;
import yalong.site.json.entity.match.Games;

/**
 * 单个玩家的战绩
 *
 * @author WuYi
 */
@Data
public class ProductsMatchHistoryBO {
	private Long accountId;
	private Games games;
}
