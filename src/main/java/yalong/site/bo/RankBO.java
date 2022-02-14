package yalong.site.bo;

import lombok.Data;

/**
 * 当前段位选择
 *
 * @author yaLong
 * @date 2022/2/14
 */
@Data
public class RankBO {
    private String firstRank;
    private String secondRank;
    private String thirdRank;

    public boolean isNull() {
        return firstRank == null || secondRank == null || thirdRank == null;
    }
}
