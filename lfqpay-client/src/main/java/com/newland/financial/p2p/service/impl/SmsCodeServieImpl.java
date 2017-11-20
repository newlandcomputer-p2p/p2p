package com.newland.financial.p2p.service.impl;

import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.newland.financial.p2p.Utils.IfqUtil;
import com.newland.financial.p2p.domain.CodeMsgReq;
import com.newland.financial.p2p.domain.CodeMsgResp;
import com.newland.financial.p2p.service.ISmsCodeServie;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *短信验证码ServiceImpl.
 * @author Gregory
 */
@Log4j
@Service
public class SmsCodeServieImpl implements ISmsCodeServie {

//    private static final String ADDRESS_TEST = "https://tt.lfqpay.com:343/lfq-pay/gateway/api/backSMSCodeRequest.do";

    /**
     * 短信接口测试地址.
     */
    @Value("${IFQ_SMSCODE_ADDRESS}")
    private String requestUrl;

    /**
     * 请求乐百通短信接口.
     * @param codeMsgReq
     * @return
     */
    public Object backSMSCodeRequest(CodeMsgReq codeMsgReq) {
//        String requestUrl = ADDRESS_TEST + "/lfq-pay/gateway/api/backSMSCodeRequest.do";

        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Map<String, String> data = new HashMap<String, String>();

        data.put("version", "1.0.0"); // 固定值：1.0.0
        data.put("encoding", "utf-8"); // 编码
        data.put("txnType", "13"); // 短信验证码：13
        data.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
        data.put("mobile", "15900543650"); // 手机号码
//        data.put("version", codeMsgReq.getVersion()); // 固定值：1.0.0
//        data.put("encoding", codeMsgReq.getEncoding()); // 编码
//        data.put("txnType", codeMsgReq.getTxnType()); // 短信验证码：13
//        data.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
//        data.put("mobile", codeMsgReq.getMobile()); // 手机号码

        data.put("merId", "GZW-001"); // 商户编号
        data.put("merName", "newlandTest"); // 商户名称
        data.put("merAbbr", "newland"); // 商户简称
        data.put("merPwd", SecureUtil.encryptWithDES(txnTime, "12345678")); // 商户密码
//        data.put("merId", codeMsgReq.getMerId()); // 商户编号
//        data.put("merName", codeMsgReq.getMerName()); // 商户名称
//        data.put("merAbbr", codeMsgReq.getMerAbbr()); // 商户简称
//        String merPwd = codeMsgReq.getMerPwd();
//        data.put("merPwd", SecureUtil.encryptWithDES(txnTime, merPwd)); // 商户密码

        // 签名
        boolean b = MpiUtil.sign(data, "utf-8");
        //发送请求
        Map<String, String> resp = IfqUtil.execute(requestUrl,data);

        //返回CodeMsgResp对象到client
        CodeMsgResp codeMsgResp = (CodeMsgResp) IfqUtil.convertMap(CodeMsgResp.class,resp);

        Map<String, String> respMap = new HashMap<String, String>();
        respMap.put("merId",resp.get("merId"));
        respMap.put("mobile",resp.get("mobile"));
        respMap.put("respCode",resp.get("respCode"));
        respMap.put("respMsg",resp.get("respMsg"));
        respMap.put("respTime",resp.get("respTime"));
        return respMap;
//        return new CodeMsgResp();
    }
}
