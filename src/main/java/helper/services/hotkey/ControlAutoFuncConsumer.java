package helper.services.hotkey;

import helper.cache.AppCache;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public class ControlAutoFuncConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> AppCache.stopAuto = !AppCache.stopAuto;
	}
}
