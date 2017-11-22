package com.newland.financial.p2p.service.impl;

import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.newland.financial.p2p.Utils.IfqUtil;
import com.newland.financial.p2p.domain.MethodFactory;
import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mxia
 */
@Log4j
public class OrderService implements IOrderService {
    /**
     * 外网测试地址.
     */
    public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
    /**
     * 本地开发地址.
     */
    public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";

    /**
     * 创建订单.
     *
     * @param orm 请求参数
     * @return 订单信息
     * @throws IOException an error
     */
    public OrderInfo sendOrderMsg(OrderMsgReq orm) throws IOException {
        log.info("====come in ybf service====");
        String requestUrlA = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backTransRequest.do"; // 创建订单地址.
        String requestUrlB = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/singleQueryRequest.do"; // 查询订单地址.
        // 首次创建订单.
        long start1 = System.currentTimeMillis();
        Map<String, String> map1 = MethodFactory.initOrderData(orm);
        MpiUtil.sign(map1, "utf-8"); // 签名
        long end1 = System.currentTimeMillis();
        log.info("====p1====:" + (end1 - start1));

        long start2 = System.currentTimeMillis();
        Map<String, String> mapA = IfqUtil.execute(requestUrlA, map1);
        long end2 = System.currentTimeMillis();
        log.info("====p2====:" + (end2 - start2));

        // 根据创建订单返回的报文，查询订单，获得合同状态.
        long start3 = System.currentTimeMillis();
        Map<String, String> map2 = MethodFactory.initQueryOrderDate(mapA, orm.getMerPwd());
        MpiUtil.sign(map2, "utf-8"); // 签名
        long end3 = System.currentTimeMillis();
        log.info("====p3====:" + (end3 - start3));

        long start4 = System.currentTimeMillis();
        Map<String, String> mapB = IfqUtil.execute(requestUrlB, map2);
        OrderInfo od = MethodFactory.installOrderInfo(mapA, mapB, orm);
        long end4 = System.currentTimeMillis();
        log.info("====p4====:" + (end4 - start4));
        return od;
    }

    /**
     * 查询单个订单.
     *
     * @param oqr 请求参数
     * @return 订单信息
     * @throws IOException an error
     */
    public Object findOrderInfo(OrderQueryReq oqr) throws IOException {
        String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/singleQueryRequest.do";
        Map<String, String> map = new HashMap<String, String>();
        map.put("version", "1.0.0"); // 固定值：1.0.0
        map.put("encoding", "utf-8"); // 编码
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        map.put("txnTime", txnTime); // 发送时间：yyyyMMddHHmmss
        map.put("txnType", "73"); // 查询:73
        map.put("merId", oqr.getMerId());
        map.put("merPwd", SecureUtil.encryptWithDES(txnTime, oqr.getMerPwd()));
        map.put("merName", oqr.getMerName());
        map.put("merAbbr", oqr.getMerAbbr());
        map.put("orderId", oqr.getOrderId());
        //map.put("contractsCode", oqr.getContractsCode());
        log.info("=============6:查询报文内容 begin================");
        for (String key : map.keySet()) {
            log.info("key= " + key + " and value= " + map.get(key));
        }
        log.info("=============7:查询报文内容 end================");
        MpiUtil.sign(map, "utf-8"); // 签名
        Map<String, String> mapA = IfqUtil.execute(requestUrl, map);
        OrderInfo or = MethodFactory.installOrderInfoA(mapA);
        return or;
    }
}
