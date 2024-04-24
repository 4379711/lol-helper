package yalong.site.services.hotkey;

import yalong.site.cache.FrameCache;
import yalong.site.services.word.RequestCaihongpiWord;
import yalong.site.utils.KeyEventUtil;

import java.util.function.Consumer;

/**
 * @author yalong
 */
public class CaiHongPiConsumer implements HotKeyConsumer {
	@Override
	public Consumer<Integer> build() {
		return i -> {
			if(FrameCache.communicate){
				String s = RequestCaihongpiWord.requestCaiHongPiText();
				if (s != null && !"".equals(s)) {
					KeyEventUtil.sendMsg(s);
				}
			}

		};
	}
}
