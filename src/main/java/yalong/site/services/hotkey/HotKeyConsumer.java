package yalong.site.services.hotkey;

import java.util.function.Consumer;

/**
 * @author yalong
 */
public interface HotKeyConsumer {

	Consumer<Integer> build();
}
