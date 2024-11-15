package helper.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author WuYi
 */
@Getter
public enum RegionEnum {
    HN1("HN1", "艾欧尼亚", "艾欧尼亚"),
    HN10("HN10", "黑色玫瑰", "黑色玫瑰"),
    BGP2("BGP2", "峡谷之巅", "峡谷之巅"),
    /**
     * 联盟一区
     */
    HN2("NJ100", "祖安", "联盟一区"),
    HN5("NJ100", "皮尔特沃夫", "联盟一区"),
    HN7("NJ100", "巨神峰", "联盟一区"),
    EDU1("NJ100", "教育网", "联盟一区"),
    BGP1("NJ100", "男爵领域", "联盟一区"),
    HN14("NJ100", "均衡教派", "联盟一区"),
    HN15("NJ100", "影流", "联盟一区"),
    HN16("NJ100", "守望之海", "联盟一区"),
    /**
     * 联盟二区
     */
    HN18("GZ100", "卡拉曼达", "联盟二区"),
    HN11("GZ100", "暗影岛", "联盟二区"),
    HN17("GZ100", "征服之海", "联盟二区"),
    HN3("GZ100", "洛克萨斯", "联盟二区"),
    HN6("GZ100", "战争学院", "联盟二区"),
    HN8("GZ100", "雷瑟守备", "联盟二区"),
    /**
     * 联盟三区
     */
    HN4("CQ100", "班德尔城", "联盟三区"),
    HN9("CQ100", "裁决之地", "联盟三区"),
    HN13("CQ100", "水晶之痕", "联盟三区"),
    HN12("CQ100", "钢铁烈阳", "联盟三区"),
    HN19("CQ100", "皮城警备", "联盟三区"),
    /**
     * 联盟四区
     */
    WT1("TJ100", "比尔吉沃特", "联盟四区"),
    WT3("TJ100", "弗雷尔卓德", "联盟四区"),
    WT6("TJ100", "扭曲丛林", "联盟四区"),
    /**
     * 联盟五区
     */
    WT2("TJ101", "德玛西亚", "联盟五区"),
    WT4("TJ101", "无畏先锋", "联盟五区"),
    WT5("TJ101", "恕瑞玛", "联盟五区"),
    WT7("TJ101", "巨龙之巢", "联盟五区"),
    ;

    private final String regionId;
    private final String regionName;
    private final String regionType;

    RegionEnum(String regionId, String regionName, String regionType) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionType = regionType;
    }

    public static String regionTypeById(String regionId) {
        Optional<RegionEnum> first = Arrays.stream(RegionEnum.values()).filter(item -> item.getRegionId().equals(regionId.toUpperCase())).findFirst();
        if (first.isPresent()) {
            return first.get().getRegionType();
        }
        return "";
    }
}
