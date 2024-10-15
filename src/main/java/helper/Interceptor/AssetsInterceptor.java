package helper.Interceptor;

import helper.services.web.AssetsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author WuYi
 */
@Component
@Slf4j
public class AssetsInterceptor implements HandlerInterceptor {
    @Resource
    private AssetsService service;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        // 检查请求URI是否以"/lol-game-data/"开头
        if (requestURI.startsWith("/lol-game-data/")) {
            Boolean flag = service.loadImage(requestURI);
            if(flag){
                log.info("首次缓存"+requestURI+"成功");
            }else{
                log.info("首次缓存"+requestURI+"失败");
            }
        }
        return true;
    }
}
