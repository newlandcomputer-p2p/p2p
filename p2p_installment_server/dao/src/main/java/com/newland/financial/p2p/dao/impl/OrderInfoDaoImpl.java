package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import org.springframework.stereotype.Repository;

/**
 *订单处理DaoImpl.
 * @author Gregory
 */
@Repository
public class OrderInfoDaoImpl extends MybatisBaseDao<OrderInfo> implements IOrderInfoDao {
    /**
     * 插入订单信息.
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    public boolean insertOrder(OrderInfo orderInfo) {
        return false;
    }
}
