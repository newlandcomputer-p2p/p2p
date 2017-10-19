package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.ICustomerFlowDebitDao;
import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.service.ILenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 对用户进行操作的service类.
 *
 * @author cendaijuan
 */
@Service
public class LenderServiceImpl implements ILenderService {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Dao层对象.
     */
    @Autowired
    private ILenderDao lenderDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IRepayALoanDao repayALoanDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private ICustomerFlowDebitDao customerFlowDebitDao;

    /**
     * 获取用户信息.
     *
     * @param userId String用户编号
     * @return Lender返回用户信息.
     */
    public Lender getLender(final String userId) {
        logger.info("LenderService:lenderId(userId)--:" + userId);
        return lenderDao.findById(userId);
    }

    /**
     * 清楚用户数据,目前禁止使用.
     *
     * @param userId String用户编号
     */
    public void clear(final String userId) {
        logger.info("LenderService:clear(userId)--:" + userId);
        debitAndCreditDao.deleteDebitAndCredit(userId);
        repayALoanDao.deleteRepayAloan(userId);
        lenderDao.getBack(userId);
    }

    /**
     * 申请面签产品，将信息插入贷款表中.
     *
     * @param customerFlowDebit 贷款实体
     * @return true or false
     */
    public boolean insertDebitInfo(CustomerFlowDebit customerFlowDebit) {
        //校验年龄是否符合
        String identityCard = customerFlowDebit.getIdentityCard();
        Calendar ca = Calendar.getInstance();
        int nowYear = ca.get(Calendar.YEAR);
        int nowMonth = ca.get(Calendar.MONTH) + 1;
        int IDYear = Integer.parseInt(identityCard.substring(6, 10));
        int IDMonth = Integer.parseInt(identityCard.substring(10, 12));
        if ((IDMonth - nowMonth) >= 0) {
            if (nowYear - IDYear - 1 < 18 || nowYear - IDYear - 1 > 60) {
                return false;
            }
        } else {
            if (nowYear - IDYear < 18 || nowYear - IDYear > 60) {
                return false;
            }
        }
        customerFlowDebit.setStus("1");
        customerFlowDebit.setDtId(UUID.randomUUID().toString().replaceAll(
                "-", ""));
        customerFlowDebit.setId(UUID.randomUUID().toString().replaceAll(
                "-", ""));
        customerFlowDebit.setDDate(new Date()); //申请日期
        //生成申请单号，年月日时分秒+4位随机数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        StringBuffer s = new StringBuffer(sdf.format(date));
        Random r = new Random();
        s.append(r.nextInt(10000)+"");
        customerFlowDebit.setOddNumbers(new String(s));
        customerFlowDebit.setDDate(date);
        logger.info("lenderService--insertDebitInfo--customerFlowDebit:"+customerFlowDebit.toString());
        return customerFlowDebitDao.insertDebitInfo(customerFlowDebit);
    }
}
