package helper.exception;

/**
 * @author WuYi
 */
public class NoSummonerException extends Exception {
    public NoSummonerException() {
        super("该大区未找到该召唤师");
    }
}
