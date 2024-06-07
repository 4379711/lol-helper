package yalong.site.services.hotkey;

import yalong.site.cache.AppCache;
import java.util.function.Consumer;

/**
 * @author yalong
 */
public class ControlAutoFuncConsumer implements HotKeyConsumer {

	@Override
	public Consumer<Integer> build() {
		return i -> AppCache.stopAuto=!AppCache.stopAuto;
	}
}
