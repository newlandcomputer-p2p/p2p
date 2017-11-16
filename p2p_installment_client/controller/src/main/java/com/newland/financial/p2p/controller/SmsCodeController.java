package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IEgwService;
import com.newland.financial.p2p.service.ISmsCodeService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *发送短信验证码.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/SmsCodeController")
public class SmsCodeController {

    @Autowired
    private ISmsCodeService smsCodeService;

    /**
     * 外发接口.
     */
    @Autowired
    private IEgwService egwService;

    /**
     * 短信验证码接口.
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST)
    public Object sendSmsCode(@RequestBody String jsonStr) {
//        log.debug("------------------------------sendSmsCode----------------------------");
//        log.debug("jsonStr：" + jsonStr);
        log.info("------------------------------sendSmsCode----------------------------");
        log.info("jsonStr：" + jsonStr);

        Object msgCodeReqPram = smsCodeService.getMsgCodeReqPram(jsonStr);
        //请求参数未通过校验或没有对应的商户信息
        Map<String,Object> resp = new HashMap<String,Object>();
        if(msgCodeReqPram == null){
            resp.put("respCode","0220");
            resp.put("respMsg","商户不存在");
            return resp;
        }
        //请求乐百分短信接口
        log.info("-----------------------------------------请求乐百分短信接口：");
        return egwService.backSMSCodeRequest(msgCodeReqPram);
    }

}
