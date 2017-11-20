package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;

import java.io.IOException;

/**
 * @author Mxia
 */
public interface IOrderService {
    Object sendOrderMsg(OrderMsgReq orm) throws IOException;

    Object findOrderInfo(OrderQueryReq oqr) throws IOException;
}