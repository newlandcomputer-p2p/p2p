package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.RepayALoan;

import java.math.BigDecimal;
import java.util.List;
/**
 * 定义还款单增删改查的接口.
 * @author Mxia
 * */
public interface IRepayALoanDao {
    /**
     * 增加还款单.
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true,失败false
     * */
    boolean insertRepayAloan(RepayALoan repayALoan);
    /**
     * 修改还款单.
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true,失败false
     * */
    boolean updateRepayAloan(RepayALoan repayALoan);
    /**
     * 查找还款单.
     * @param userId String用户Id
     * @return List返回该用户所有的还款单
     * */
    List<RepayALoan> findByUserId(String userId);
    /**
     * 根据还款单编号查询相应的还款单.
     * @param reId String还款单编号
     * @return RepayALoan返回相应编号的还款单信息
     * */
    RepayALoan findByReId(String reId);
    /**
     * 本月之前还未还款总额（包含本月）.
     * @param userId String用户id
     * @return BigDecimal返回用户还未还款的金额
     * */
    BigDecimal findUnPay(String userId);
    /**
     * 查询已还款总额.
     * @param userId String用户id
     * @return BigDecimal返回用户已还款总额
     * */
    BigDecimal findYetPay(String userId);
    /**
     *查询未还款总额.
     * @param userId String用户id
     * @return BigDecimal返回未还款总额
     * */
    BigDecimal findNeedPay(String userId);
    /**
     *删除还款单.
     * @param id String还款单编号
     * @return boolean删除成功返回true,失败false
     * */
    boolean deleteRepayAloan(String id);
    /**
     *更新还款状态.
     * @param userId String还款单编号
     * @return boolean更新成功返回true,失败false
     * */
    boolean updateStatus(String userId);
}
