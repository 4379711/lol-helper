package helper.services.lcu;

import lombok.Setter;

/**
 * @author @_@
 */
@Setter
public class GameStatusContext {
	private GameStatusStrategy strategy;

	public void executeStrategy() {
		this.strategy.doThis();
	}

}
