package com.shuogesha.api.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuogesha.api.version.ApiVersion;
import com.shuogesha.api.web.util.ApiUtils;
import com.shuogesha.platform.entity.SystemLog;
import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.service.SystemLogService;
import com.shuogesha.platform.service.UnifiedUserService;
import com.shuogesha.platform.service.UnifiedUserTokenService;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.exception.BadCredentialsException;
import com.shuogesha.platform.web.exception.UsernameNotFoundException;

@Controller
@RequestMapping("/{version}/")
public class ApiLoginAct{
	private static Logger log = LoggerFactory.getLogger(ApiLoginAct.class);
	
	@RequestMapping(value = "login")
	@ApiVersion(1)
	public @ResponseBody Object token_1(String username, String password,String appid, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", false);
		try{
			UnifiedUser unifieduser =  unifiedUserService.login(username,password,RequestUtils.getIpAddr(request));
			if(unifieduser==null){
				map.put("error_code", "1");
				map.put("error", "用户名或密码错误");
			}else {
				map.put("status", true);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("uid", unifieduser.getId());
				data.put("username", unifieduser.getUsername());
				data.put("token", unifiedUserTokenService.createToken(appid,unifieduser.getId()));
				map.put("data", data);
				systemLogService.log(unifieduser.getId(),"用户登录",RequestUtils.getIpAddr(request),SystemLog.APP);
			}
			} catch (BadCredentialsException e) {
				map.put("error_code", "1");
				map.put("error", "用户名或密码错误");
			} catch (UsernameNotFoundException e) {
				map.put("error_code", "2");
				map.put("error", "用户名不存在");
		}
		return map;
	}
	
	
	/***
	 * 退出登录
	 * @param request
	 * @param tokenStr
	 * @return
	 */
	@RequestMapping(value = "logout")
	@ApiVersion(1)
	public @ResponseBody Object logout_1(HttpServletRequest request,String appid){
		Map<String, Object> map = new HashMap<>();
		map.put("status", false);
		try{
			systemLogService.log(ApiUtils.getUnifiedUserId(request),"用户退出",RequestUtils.getIpAddr(request),SystemLog.APP);
			unifiedUserService.logout(ApiUtils.getAppId(request),ApiUtils.getUnifiedUserId(request));
			map.put("status", true);
			map.put("data", "退出成功");
		}catch(Exception e){
			map.put("error_code", "1");
			map.put("error", "服务器异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@Autowired
	private UnifiedUserService unifiedUserService;
	@Autowired
	private UnifiedUserTokenService unifiedUserTokenService;
	@Autowired
	public SystemLogService systemLogService;
}
