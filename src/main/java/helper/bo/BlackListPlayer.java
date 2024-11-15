package helper.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WuYi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlackListPlayer extends Player {
    private String remark;
}
