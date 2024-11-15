package helper.bo;

import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class Styles {
    private String description;
    private List<Selections> selections;
    private Integer style;
}
