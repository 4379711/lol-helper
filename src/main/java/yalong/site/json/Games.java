package yalong.site.json;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Games {
    private String gameBeginDate;
    private Integer gameCount;
    private String gameEndDate;
    private Integer gameIndexBegin;
    private Integer gameIndexEnd;
    private List<GameData> games;

}
