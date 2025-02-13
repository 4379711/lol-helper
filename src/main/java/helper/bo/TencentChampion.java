package helper.bo;

import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class TencentChampion {
    private Integer heroId;
    private String name;
    private String keywords;
    private List<String> roles;
    private List<String> position;
}
