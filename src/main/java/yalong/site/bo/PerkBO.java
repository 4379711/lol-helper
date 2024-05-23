package yalong.site.bo;

import lombok.Data;

/**
 * 天府符文
 *
 * @author WuYi
 */
@Data
public class PerkBO {
    private Integer id;
    private String name;
    private String shortDesc;
    private String recommendationDescriptor;
    private String iconPath;
    private String[] endOfGameStatDescs;

}
