package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import javafx.scene.input.DataFormat;
import lombok.extern.java.Log;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
@Log
public class TransToSmallLoanScheduler {

    /***/
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /***/
    @Resource(name = "createTransToSmallLoanCompanyJob")
    Job job;

    @Scheduled(fixedRate=120000)
//    @Scheduled(cron = "0 0/2 9-21 * * ?")
    public void testTasks() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("每120秒执行一次。开始……");
        DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        dataFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String runDay = dataFormat.format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("runDay", runDay)
                .addString("queryId", "selectSendOrderList")
                .addString("statementId", "updateOrderSendStus")
                .toJobParameters();
        //jobExecution.getJobParameters().getParameters().putAll(jobParameters.getParameters());

        JobExecution execution = jobLauncher.run(job, jobParameters);
        log.info("每120秒执行一次。结束。" + execution.getStatus());
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void reportCurrentTime() {
        debitAndCreditDao.updateStusToThree(); //将数据库内申请时间超过15天的贷款贷状态改为拒绝
        log.info("The time is now：定时器执行更改超过15天申请中订单的状态为拒绝"+ dateFormat.format(new Date()));
    }
}
