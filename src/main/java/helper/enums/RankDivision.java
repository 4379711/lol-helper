package helper.enums;

import lombok.Getter;

/**
 * @author WuYi
 */
@Getter
public enum RankDivision {
    I("I"),
    II("II"),
    III("III"),
    IV("IV"),
    ;
    private final String division;

    RankDivision(String division) {
        this.division = division;
    }
}
