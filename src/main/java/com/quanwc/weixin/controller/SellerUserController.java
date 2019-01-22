package com.quanwc.weixin.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.quanwc.weixin.config.ProjectUrlConfig;
import com.quanwc.weixin.constant.CookieConstant;
import com.quanwc.weixin.constant.RedisConstant;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.model.entity.SellerInfo;
import com.quanwc.weixin.service.SellerInfoService;
import com.quanwc.weixin.util.CookieUtil;

/**
 * 卖家端用户controller
 *
 * @author quanwenchao
 * @date 2019/1/20 16:18:24
 */
@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

	@Autowired
	private SellerInfoService sellerInfoService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	/**
	 * 卖家端登录
	 * @param response
	 * @param openid
	 * @param model
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(HttpServletResponse response,
			@RequestParam("openid") String openid, Map<String, Object> model) {

		// step1：open和db中的数据进行匹配
		SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
		if (sellerInfo == null) {
			model.put("msg", ExceptionResultEnum.LOGIN_FAIL.getMsg());
			model.put("url", "/sell/seller/order/list");
			return new ModelAndView("/common/error");
		}

		// step2：设置token至redis
		String token = UUID.randomUUID().toString();
		Integer expireTime = RedisConstant.EXPIRE_TIME;
		stringRedisTemplate.opsForValue().set(
				String.format(RedisConstant.TOKEN_PREFIX, token), openid, expireTime,
				TimeUnit.SECONDS);

		// step3：设置token至cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expireTime);

		return new ModelAndView(
				"redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
	}

	/**
	 * 卖家端登出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		// step1：从cookie里查询
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie != null) {
			// step2：清除redis
			stringRedisTemplate.opsForValue().getOperations()
					.delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

			// step3：清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		}

		model.put("msg", ExceptionResultEnum.LOGOUT_SUCCESS.getMsg());
		model.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success", model);
	}

}
