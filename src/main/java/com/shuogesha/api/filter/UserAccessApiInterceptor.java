package com.shuogesha.api.filter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shuogesha.api.version.AccessToken;
import com.shuogesha.api.version.AccessTokenRequired;
import com.shuogesha.api.web.util.ApiUtils;
import com.shuogesha.api.web.util.Base64;
import com.shuogesha.platform.service.UnifiedUserService;
import com.shuogesha.platform.service.UnifiedUserTokenService;
import com.shuogesha.platform.service.UserService;
import com.shuogesha.platform.web.ResponseUtils;

import net.sf.json.JSONObject;
/**
 * token 拦截器
 * @author zhaohy
 *
 */
public class UserAccessApiInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AccessToken accessToken = method.getAnnotation(AccessToken.class);
        AccessTokenRequired accessTokenRequired = method.getAnnotation(AccessTokenRequired.class);
        if (accessTokenRequired != null||accessToken!=null) {
           String accessTokenStr = request.getParameter("token");
           if(accessTokenRequired!=null&&StringUtils.isBlank(accessTokenStr)){
        	   JSONObject json = new JSONObject();
	   		   json.put("error_code", "10001");
	   		   json.put("error", "授权参数错误");
	   		   ResponseUtils.renderJson(response, json.toString());
               return false;
           }
           if(StringUtils.isNotBlank(accessTokenStr)){
        	   String token =Base64.decode(accessTokenStr);
               if(accessTokenRequired!=null&&!unifiedUserTokenService.verifyMemberToken(token)){
            	   JSONObject json = new JSONObject();
    	   		   json.put("error_code", "10002");
    	   		   json.put("error", "授权认证失败");
    	   		   ResponseUtils.renderJson(response, json.toString());
               }
               String uid = unifiedUserTokenService.getUid(token);
               ApiUtils.setUnifiedUser(request, unifiedUserService.findById(uid));
           }
        }
        return true;
    }
    @Autowired
	private UnifiedUserService unifiedUserService;
	@Autowired
	private UnifiedUserTokenService unifiedUserTokenService;
}
