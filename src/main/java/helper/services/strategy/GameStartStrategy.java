package helper.services.strategy;

import helper.utils.StrategyUtil;

/**
 * @author WuYi
 */
public class GameStartStrategy implements GameStatusStrategy{
    @Override
    public void doThis() {
        StrategyUtil.hidePanel();
    }

}
