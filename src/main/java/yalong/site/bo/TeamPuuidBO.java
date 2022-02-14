package yalong.site.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaLong
 */
@Data
public class TeamPuuidBO {
    private List<String> teamPuuid1 = new ArrayList<>();
    private List<String> teamPuuid2 = new ArrayList<>();
}
