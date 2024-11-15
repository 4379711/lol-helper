package helper.bo;

import lombok.Data;

@Data
public class RerollPoints {
    /**
     * 当前点数
     */
    private int currentPoints;
    /**
     * 最大骰子数
     */
    private int maxRolls;
    private int numberOfRolls;
    private int pointsCostToRoll;
    private int pointsToReroll;
}
