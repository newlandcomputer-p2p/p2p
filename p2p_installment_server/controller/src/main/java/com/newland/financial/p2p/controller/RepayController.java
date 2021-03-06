package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.Repay;
import com.newland.financial.p2p.service.IRepayService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

/**
 * 还款推送.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/repay")
public class RepayController {
    /**
     * 还款处理Service.
     */
    @Autowired
    private IRepayService repayService;

    /**
     * 接收还款推送信息.
     *
     * @param jsonStr 乐百分请求参数:<BR>
     *                {<BR>
     *                &nbsp;&nbsp;"version":"版本号",<BR>
     *                &nbsp;&nbsp;"encoding":"编码方式",<BR>
     *                &nbsp;&nbsp;"certId":"证书ID",<BR>
     *                &nbsp;&nbsp;"signature":"签名",<BR>
     *                &nbsp;&nbsp;"txnType":"交易类型(1)",<BR>
     *                &nbsp;&nbsp;"txnTime":"发送时间",<BR>
     *                &nbsp;&nbsp;"merId":"商户代码",<BR>
     *                &nbsp;&nbsp;"merPwd":"商户密码",<BR>
     *                &nbsp;&nbsp;"merName":"商户名称",<BR>
     *                &nbsp;&nbsp;"merAbbr":"商户简称",<BR>
     *                &nbsp;&nbsp;"terms":"当期还款期数",<BR>
     *                &nbsp;&nbsp;"txnAmt":"订单总金额,单位：分",<BR>
     *                &nbsp;&nbsp;"amount":"当期还款金额，单位：分",<BR>
     *                &nbsp;&nbsp;"sumAmount":"已还款金额,单位：分",<BR>
     *                &nbsp;&nbsp;"instalmentDate":"还款时间,yyyyMMddHHmmss",<BR>
     *                &nbsp;&nbsp;"nextDate":"下期还款日期",<BR>
     *                &nbsp;&nbsp;"contractsCode":"合同号",<BR>
     *                &nbsp;&nbsp;"orderId":"订单号",<BR>
     *                &nbsp;&nbsp;"payType":"还款类型,0：正常还款,1：提前还款",<BR>
     *                &nbsp;&nbsp;"respCode":"还款状态码",<BR>
     *                &nbsp;&nbsp;"respMsg":"还款状态信息",<BR>
     *                &nbsp;&nbsp;"respTime":"响应时间",<BR>
     *                &nbsp;&nbsp;"queryId":"请求编号"<BR>
     *                }<BR>
     * @return 成功:true,失败：false
     */
    @RequestMapping(value = "/receiveRepaymentInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String updateRepayInfo02(@RequestBody String jsonStr) {
        log.info("--------------------------进入RepayController:" + jsonStr);
        Repay repay = new Repay();
        repay = JSON.parseObject(jsonStr, Repay.class);
        log.info("--------------------------还款时间：" + repay.getInstalmentDate());
        String resp = repayService.receiveRepayInfo(repay);
        return resp;
    }
}
