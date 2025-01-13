package cn.px.zly.app.module;

import cn.px.core.common.result.JsonResult;
import cn.px.core.spring.EnvironmentContext;
import cn.px.core.util.StringUtil;
import cn.px.zly.module.account.service.IAccountService;
import cn.px.zly.module.box.service.IBeerBoxOrderContext;
import cn.px.zly.module.user.entity.UserPO;
import cn.px.zly.module.user.service.IUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Api(tags="测试接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/test")
public class TestController {
//    @Autowired
//    private ISfService sfService;

    @Autowired
    private IBeerBoxOrderContext beerBoxOrderContext;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;


//    @RequestMapping("/sf/routes/search")
//    public void searchRoutes() {
//        SearchRoutesParam param = new SearchRoutesParam();
//        param.setTrackingType(1);
//        param.setCheckPhoneNo("9576");
//        List<String> list = new ArrayList<>();
//        list.add("SF1384480022647");
//        param.setTrackingNumber(list);
//        sfService.searchRoutes(param);
//    }

    @GetMapping("/box/order/handle")
    public void boxOrderHandle(long dateTime) {
        if(EnvironmentContext.isProdEnv()){
            return;
        }
        beerBoxOrderContext.cutOffDayBoxInfoHandle(new Date(dateTime));
    }

    @GetMapping("/sync/openid2user")
    public JsonResult<Object> syncOpenidToUser(){
        List<UserPO> list = userService.list();
        if(!list.isEmpty()){
            for (UserPO userPO : list) {
                if(StringUtil.isBlank(userPO.getWxOpenId())){
                    List<String> openids = accountService.queryOpenId(userPO.getUserId());
//                    userService.changeOpenid(userPO.getUserId(),openid);
                }
            }

        }
        return JsonResult.ok();
    }

    public static void main(String[] args) {
//        JSONObject jsonObject = JSONObject.parseObject("{\"success\":true,\"errorCode\":\"S0000\",\"errorMsg\":null,\"msgData\":{\"routeResps\":[{\"mailNo\":\"SF1699708548363\",\"routes\":[{\"acceptAddress\":\"深圳市\",\"acceptTime\":\"2023-07-04 10:32:40\",\"remark\":\"顺丰速运 已收取快件\",\"opCode\":\"50\"}]}]}}");
//        String s = "%5B%E5%95%A4%E9%85%92%5D%5B%E5%92%96%E5%95%A1%5D%E6%B5%8B%E8%AF%95%E7%9A%84%E8%AF%AD%E5%8F%A5";
//        try {
//            String decode = URLDecoder.decode(s, "UTF-8");
//            System.out.println(decode);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

}
