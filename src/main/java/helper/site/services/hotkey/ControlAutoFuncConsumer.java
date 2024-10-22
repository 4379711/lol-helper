package helper.site.services.hotkey;

import helper.site.cache.AppCache;

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
