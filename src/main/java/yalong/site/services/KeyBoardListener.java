package yalong.site.services;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.function.Consumer;

/**
 * @author yaLong
 * @date 2022/3/3 23:31
 */
public class KeyBoardListener implements NativeKeyListener {
    private final Consumer<Integer> function;

    public KeyBoardListener(Consumer<Integer> function) {
        this.function = function;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        this.function.accept(e.getKeyCode());
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
