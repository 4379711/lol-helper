package yalong.site.services.lcu;

import lombok.Setter;

/**
 * @author yalong
 */
@Setter
public class GameStatusContext {
	private GameStatusStrategy strategy;

	public void executeStrategy() {
		this.strategy.doThis();
	}

}
