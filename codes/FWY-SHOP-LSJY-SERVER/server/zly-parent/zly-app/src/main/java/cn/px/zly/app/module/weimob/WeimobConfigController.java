package cn.px.zly.app.module.weimob;

import com.aliyun.oss.ServiceException;
import cn.px.core.common.result.JsonResult;
import cn.px.core.util.DigestUtil;
import cn.px.core.util.HttpClient;
import cn.px.core.util.JsonUtil;
import cn.px.core.util.StringUtil;
import cn.px.zly.module.weimob.cache.WeimobCacheManager;
import cn.px.zly.module.weimob.constant.WeimobConstant;
import cn.px.zly.module.weimob.dto.TokenDTO;
import cn.px.zly.module.weimob.service.IWeimobTokenService;
import cn.px.zly.properties.ZlyProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@ApiIgnore
@Api(tags={"weimob_config"}, description="微盟域-微盟配置相关接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/weimob")
public class WeimobConfigController {

	@Autowired
	private ZlyProperties zlyProperties;

	@Autowired
	private WeimobCacheManager weimobTokenManager;

	@Autowired
	private IWeimobTokenService weimobTokenService;
	/**
	 * 微盟oauth2授权
	 * @param request
	 * @return
	 */
	@RequestMapping(path="/oauth2", method= RequestMethod.GET)
	public ModelAndView oauth2(HttpServletRequest request) {

		String oauth2Url = WeimobConstant.OAUTH2_URL
				.replace("{client_id}", zlyProperties.getWeimob().getClientId())
				.replace("{redirect_uri}", zlyProperties.getWeimob().getRedirectUri())
				.replace("{state}", "");
		log.warn("weimob : {}", oauth2Url);
		return new ModelAndView("redirect:"+oauth2Url);
	}

	/**
	 * 微盟token获取并保存
	 * @param code
	 * @return
	 */
	@RequestMapping(path="/token", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<TokenDTO> token(String code) throws Exception {
		String tokenUrl = WeimobConstant.TOKEN_URL
				.replace("{code}", code)
				.replace("{client_id}", zlyProperties.getWeimob().getClientId())
				.replace("{client_secret}", zlyProperties.getWeimob().getClientSecret())
				.replace("{redirect_uri}", zlyProperties.getWeimob().getRedirectUri());

		HttpClient.HttpResult<String> result = HttpClient.post(tokenUrl, "");
		if(result.getStatusCode() != 200) {
			throw new ServiceException("请求token网络异常");
		}
		String tokenStr = result.getContent();
		log.warn("weimob : {} => {}", tokenUrl, tokenStr);

		TokenDTO token = weimobTokenService.saveOrUpdateToken(tokenStr);
		return JsonResult.ok(token);
	}


	/**
	 * 设置商户配置
	 * 接口定位与使用场景
	 * 在商户使用H5通用免登前，需要商户进行初始化设置
	 * 根据设置的不同，主要分为两种: 1.通用设置 2.场景设置
	 * 通用设置：用户数量级，过期时间等
	 * 场景设置：登录回调等
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/set_config", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<String> setConfig() throws Exception {
		TokenDTO tokenDTO = weimobTokenManager.getWeimobAccessToken();
		if(tokenDTO == null){
			return JsonResult.fail("token不存在");
		}
		String accessToken = tokenDTO.getAccess_token();
		String loginCallbackUrl = zlyProperties.getWeimob().getLoginCallbackUrl();
		String setConfigUrl = WeimobConstant.SET_CONFIG_URL
				.replace("{accesstoken}", accessToken)
				+"&loginCallbackUrl="+ loginCallbackUrl;

		Map<String, String> requestParams = new HashMap<>(16);
		requestParams.put("loginCallbackUrl", loginCallbackUrl);  //登录回调
		requestParams.put("numberScale", String.valueOf(1000000));  //用户数量级（默认值1000000）
		requestParams.put("expires", String.valueOf(1296000));  //过期时间（s）（默认值1296000s 15天）
		String content = JsonUtil.toStr(requestParams);

		//微盟请求内容类型
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put(HttpClient.CONTENT_TYPE, HttpClient.APPLICATION_JSON);

		Charset charset = HttpClient.CHARSET_UTF8;

		HttpClient.HttpResult<String> result = HttpClient.post(setConfigUrl, headerMap, content, charset);
		if(result.getStatusCode() != 200) {
			throw new ServiceException("请求商户设置网络异常");
		}
		String setConfigStr = result.getContent();
		log.warn("weimob : {} {} => {}", setConfigUrl, content, setConfigStr);
		return JsonResult.ok(setConfigStr);
	}

	/**
	 * 查询商户配置
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/get_config", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<String> getConfig() throws Exception {
		TokenDTO tokenDTO = weimobTokenManager.getWeimobAccessToken();
		if(tokenDTO == null){
			return JsonResult.fail("token不存在");
		}
		String accessToken = tokenDTO.getAccess_token();
		String setConfigUrl = WeimobConstant.GET_CONFIG_URL
				.replace("{accesstoken}", accessToken);
		HttpClient.HttpResult<String> result = HttpClient.post(setConfigUrl, "");
		if(result.getStatusCode() != 200) {
			throw new ServiceException("请求商户设置网络异常");
		}
		String getConfigStr = result.getContent();
		return JsonResult.ok(getConfigStr);
	}

	/**
	 * 微盟登录过期回调地址
	 * @param nonceStr
	 * @param timestamp
	 * @param redirectUrl
	 * @param sign
	 * @return
	 */
	@RequestMapping(path="/login_callback", method={RequestMethod.GET, RequestMethod.POST})
	public Object loginCallback(HttpServletRequest request, String code,
			String nonceStr, String timestamp, String redirectUrl, String sign) throws Exception {
		//微盟故障恢复后,token回调地址配置成这个了, 兼容回调地址错误,重定向回原来地址
		if(StringUtil.isNotBlank(code)) {
			return "redirect:/weimob/token?code="+code;
		}
		//为保障回调请求的合法性
		//首先需要校验签名。
		TokenDTO tokenDTO = weimobTokenManager.getWeimobAccessToken();
		if(tokenDTO == null){
			return JsonResult.fail("token不存在");
		}
		String accessToken = tokenDTO.getAccess_token();
		String paramsStr = String.format("nonceStr=%s&timestamp=%s&redirectUrl=%s&token=%s",
				nonceStr, timestamp, redirectUrl, accessToken);
		String mySign = DigestUtil.md5Hex(paramsStr);
		if(!mySign.equalsIgnoreCase(sign)) {
			log.warn("weimob login_callback sign fail. {} {}", sign, paramsStr);
			return null;
		}
		//其次，对于redirect_uri中的域名进行校验。
		URI uri = URI.create(redirectUrl);
		if(!uri.getHost().endsWith("weimob.com")) {
			log.warn("weimob login_callback doamin fail. {}", redirectUrl);
			return null;
		}

		//将appTicket+appClientId拼接到redirectUrl并跳转。
		//格式redirectUrl?appTicket=appTicket&appClientId=appClientId
		return "redirect:/app/weimob/login?redirectUrl="+redirectUrl;
	}


}
