package cn.px.zly.module.account.service.impl;

import cn.px.core.common.exeption.BusinessException;
import cn.px.core.common.exeption.SilentBusinessException;
import cn.px.core.spring.EnvironmentContext;
import cn.px.core.util.DigestUtil;
import cn.px.core.util.RandomUtil;
import cn.px.core.util.StringUtil;
import cn.px.manager.sms.SmsManager;
import cn.px.module.account.AccountProperties;
import cn.px.module.account.cache.SmsCacheManager;
import cn.px.zly.module.account.service.ISmsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Log4j2
@Service
public class SmsService implements ISmsService {

    @Autowired
    private SmsManager smsManager;
    @Autowired
    private SmsCacheManager smsCacheManager;
    @Autowired
    private AccountProperties accountProperties;

    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex("zly"+"13177979576"+"zly"));
    }

    @Override
    public String generateTicket(String phone){
        if(StringUtil.isBlank(phone)){
            throw new BusinessException("手机号码不能为空!");
        }
        String ticketPrefix = accountProperties.getTicketPrefix();
        String ticketSuffix = accountProperties.getTicketSuffix();
        String ticket = DigestUtil.md5Hex(ticketPrefix+phone+ticketSuffix);
        return ticket;
    }

    @Override
    public String sendSmsCode(String phone, String ip, String ticket) {
        //检查短信票据
        String realTicket = generateTicket(phone);
        if(!realTicket.equalsIgnoreCase(ticket)) {
            throw new BusinessException("短信接口禁止调用");
        }
        //检查上次发送时间间隔
        long lastTime = smsCacheManager.getTime(phone);
        if(lastTime > 0) {
            throw new SilentBusinessException("时间间隔未到");
        }
        //检查发送次数限制
        int phoneCount = smsCacheManager.getCount(phone);
        int ipCount = smsCacheManager.getCount(ip);
        if(phoneCount>5 || ipCount>10) {
            log.warn("sms limit phone:{} {}  ip:{} {} ", phone, phoneCount, ip, ipCount);
            throw new SilentBusinessException("操作太频繁，请10分钟后再发");
        }
        //发送短信
        String smsCode = RandomUtil.randomNumeric(4);
        while (smsCode.startsWith("0")) {
            smsCode = RandomUtil.randomNumeric(4);
        }
        //苹果检验, 弄个验证码
        if(Objects.equals(phone, "18666507203")) {
            smsCode = "4321";
        }

        if(EnvironmentContext.isProdEnv()) {
            Map<String, String> templateParamMap = new HashMap<>(4);
            templateParamMap.put("smsCode", smsCode);
            boolean ret = smsManager.send(phone, null,
                    "【zly】验证码：${smsCode}，5分钟内有效。为了保证安全，请勿向他人泄漏。谢谢您使用'zly'", templateParamMap);
            if(!ret) {
                throw new BusinessException("短信验证码发送失败");
            }
        }

        if(!EnvironmentContext.isProdEnv()) {
            log.warn("sms phone {} {}", phone, smsCode);
        }

        smsCacheManager.putCode(phone, smsCode);
        smsCacheManager.putTime(phone);
        smsCacheManager.incCount(phone);
        smsCacheManager.incCount(ip);

        if(!EnvironmentContext.isProdEnv()) {
            return smsCode;
        }
        return null;
    }

    @Override
    public boolean checkSmsCode(String phone, String smsCode) {
        //检查用户试错次数
        int count = smsCacheManager.getCheck(phone);
        if(count > 10) {
            throw new SilentBusinessException("多次错误，30分钟后重试");
        }
        smsCacheManager.incCheck(phone);
        //检查短信验证码
        String realSmsCode = smsCacheManager.getCode(phone);
        if(realSmsCode==null || !realSmsCode.equalsIgnoreCase(smsCode)) {
            return false;
        }
        smsCacheManager.deleteCode(phone);
        return true;
    }
}
