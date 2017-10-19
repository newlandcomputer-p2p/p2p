package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款单持久层.
 * @author Gregory
 *
 */
@Repository
public class DebitAndCreditDao extends
        MybatisBaseDao<DebitAndCredit> implements IDebitAndCreditDao {

    /**
     * 查看该人所有的贷款单.
     * @return List返回该用户所有的贷款信息
     */
    public List<DebitAndCredit> findAll() {
        return super.selectAll();
    }

}
