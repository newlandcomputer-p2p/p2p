package com.newland.financial.p2p.service;

import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 订单处理Service.
 *
 * @author Gregory, Mxia
 */
public interface IOrderService {
    /**
     * 生成订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单的编号
     */
    String createBlankOrder(String jsonStr);

    /**
     * 获取相应订单信息.
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderInfo findOrderInfo(String orderId);

    /**
     * 进行分期交易并更新订单.
     *
     * @param jsonStr 请求信息
     * @return 返回创建订单请求报文.
     */
    Object tradeUpdateOrder(String jsonStr);

    /**
     * 查询商户信息.
     *
     * @param orderInfo 包含商户id.
     * @return 商户信息
     */
    MerInfo findMerInfo(OrderInfo orderInfo);

    /**
     * 更新订单.
     *
     * @param or 订单信息
     * @return true or false
     */
    boolean updateOrderInfo(OrderInfo or);

    /**
     * pos端查询单个订单详细信息.
     *
     * @param orderId
     * @param merId
     * @return orderInfo
     */
    OrderInfo findOrderInfoPos(String orderId, String merId);

    /**
     * 微信顾客查询订单.
     *
     * @param jsonStr
     * @return orderInfo
     */
    PageInfo<OrderInfo> getOrderInfoListByCustomer(String jsonStr);

    /**
     * Pos端订单查询(列表)
     *
     * @param merchantId
     * @param jsonStr
     * @return orderInfo
     */
    Object getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, String jsonStr);


    /**
     * 商户订单查询
     *
     * @param jsonStr
     * @return orderInfo
     */
    Object getOrderInfoListByPlantManager(String jsonStr);

    /**
     * 微信端查询订单信息.
     *
     * @param openId 微信Id
     * @param orderId 订单id
     * @return orderInfo
     */
    OrderInfo findOrderInfoWeiXin(String openId, String orderId);
    /**
     * 商户查询订单信息.
     *
     * @param orderId 订单id
     * @return orderInfo
     */
    OrderInfo findOrderInfoManager(String orderId);
    /**
     * 商户查询订单详情.
     *
     * @param orderId 订单id
     * @return orderInfo
     */
    OrderInfo getOrderInfoByManager(String orderId);
    /**
     * 平台管理员查询退款订单.
     *
     * @param  jsonStr
     * @return orderInfo
     */
    PageInfo<OrderInfo> getOrderRundListByPlantManager(String jsonStr);
    /**
     * excel导出.
     *
     * @param  jsonStr
     *
     */
    void exportOrderInfoExcel(String jsonStr);
}
