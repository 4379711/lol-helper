package helper.services.hotkey;

import java.util.function.Consumer;

/**
 * @author @_@
 */
public interface HotKeyConsumer {
	String getHotKeyName();
	Consumer<Integer> build();
}
