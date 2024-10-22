package helper.bo;

import lombok.Data;

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
