package cn.px.zly.sf.service.impl;

import com.alibaba.fastjson.JSONObject;
//import com.sf.csim.express.service.HttpClientUtil;
import cn.px.core.common.exeption.BusinessException;
import cn.px.core.util.DateUtil;
import cn.px.core.util.HttpClient;
import cn.px.core.util.StringUtil;
import cn.px.zly.properties.ZlyProperties;
import cn.px.zly.sf.SfApiUrl;
import cn.px.zly.sf.param.SearchRoutesParam;
import cn.px.zly.sf.respons.SfBaseRespons;
import cn.px.zly.sf.respons.SfRouteInfo;
import cn.px.zly.sf.service.ISfService;
import cn.px.zly.sf.utils.SfMsgDigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class SfService implements ISfService {

    public static final String SF_SUCCESS = "A1000";

    private ZlyProperties.SfProperties sfProperties;

    @Autowired
    public SfService(ZlyProperties zlyProperties) {
        this.sfProperties = zlyProperties.getSf();
    }


    @Override
    public String getAccessToken() {
        StringBuilder urlBuilder = new StringBuilder(sfProperties.getSfDomain() + SfApiUrl.OAUTH2);
        Map<String, String> map = new HashMap<String, String>();
        map.put("partnerID", sfProperties.getPartnerId());
        map.put("secret", sfProperties.getCheckWord());
        map.put("grantType", "password");
        appendParam(urlBuilder, map);

        HttpClient.HttpResult<String> post = HttpClient.post(urlBuilder.toString(), "");
        JSONObject jsonRsult = JSONObject.parseObject(post.getContent());
        String apiResultCode = jsonRsult.get("apiResultCode").toString();
        if (!StringUtil.isEqual(apiResultCode, SF_SUCCESS)) {
            log.error("获取顺丰accessToken失败,msg：{},apiResultCode：{}",
                    jsonRsult.get("apiErrorMsg").toString(), apiResultCode);
            throw new BusinessException("获取顺丰accessToken失败");
        }
        return String.valueOf(jsonRsult.get("accessToken"));
    }

    private void appendParam(StringBuilder urlBuilder, Map<String, String> map) {
        urlBuilder.append("?");
        StringBuilder paramBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (paramBuilder.length() > 0) {
                paramBuilder.append("&");
            }
            String value = entry.getValue();
            paramBuilder.append(entry.getKey());
            paramBuilder.append("=");
            paramBuilder.append(value);
        }
        urlBuilder.append(paramBuilder);
    }

    @Override
    public List<SfRouteInfo> searchRoutes(SearchRoutesParam searchRoutesParam) {
//        try {
        StringBuilder url = new StringBuilder(sfProperties.getSfDomain() + SfApiUrl.SEARCH_ROUTES);
        String accessToken = getAccessToken();
        Map<String, String> param = new HashMap<String, String>();
        param.put("partnerID", sfProperties.getPartnerId());
        param.put("requestID", StringUtil.uuid());
        param.put("serviceCode", "EXP_RECE_SEARCH_ROUTES");
        long timestamp = DateUtil.nowTimestamp().getTime();
        param.put("timestamp", String.valueOf(timestamp));
        param.put("accessToken", accessToken);
        String msgData = JSONObject.toJSONString(searchRoutesParam);
        param.put("msgData", msgData);
        String msgDigest = SfMsgDigestUtil.sign(msgData, String.valueOf(timestamp), sfProperties.getCheckWord());
        param.put("msgDigest", msgDigest);
//            String post = HttpClientUtil.post(url.toString(), param);
        //TODO 需要接入顺丰的SDK查单号
        String post = "";
//          HttpClient.HttpResult<String> postResult = HttpClient.post(url.toString(), "");
        SfBaseRespons sfBaseRespons = JSONObject.parseObject(post, SfBaseRespons.class);
        String apiResultData = sfBaseRespons.getApiResultData();
        if (!StringUtil.isEqual(sfBaseRespons.getApiResultCode(), SF_SUCCESS)) {
            log.error("查询顺丰运单路由失败 errorCode:{},errorMsg:{}",
                    sfBaseRespons.getApiResultCode(),
                    sfBaseRespons.getApiErrorMsg());
            throw new BusinessException("查询顺丰运单路由失败!");
        }
        JSONObject jsonObject = JSONObject.parseObject(apiResultData);
        String success = String.valueOf(jsonObject.get("success"));
        if (success.equals("false")) {
            log.error("查询顺丰运单路由失败 errorCode:{},errorMsg:{}",
                    jsonObject.get("errorCode"),
                    jsonObject.get("errorMsg"));
            throw new BusinessException("查询顺丰运单路由失败!");
        }
//            String success = String.valueOf(jsonResult.get("success"));
//            if(success.equals("false")){
//                log.error("查询顺丰运单路由失败 errorCode:{},errorMsg:{}",
//                        jsonResult.get("errorCode"),
//                        jsonResult.get("errorMsg"));
//                throw new BusinessException("查询顺丰运单路由失败!");
//            }
        String resultStr = jsonObject.getJSONObject("msgData").getJSONArray("routeResps").toJSONString();
        List<SfRouteInfo> sfRouteInfos = JSONObject.parseArray(resultStr, SfRouteInfo.class);
        return sfRouteInfos;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        return null;
    }
}
