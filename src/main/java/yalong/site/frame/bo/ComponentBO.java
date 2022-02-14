package yalong.site.frame.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

/**
 * @author yaLong
 * @date 2022/2/14
 */
@Data
@AllArgsConstructor
public class ComponentBO {

    /**
     * Component
     */
    private Component comp;
    /**
     * layout constraints
     */
    private Object constraints;
}
