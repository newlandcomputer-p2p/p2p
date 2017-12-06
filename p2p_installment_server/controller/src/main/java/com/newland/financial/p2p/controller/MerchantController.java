package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 商户信息处理Controller.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/merchant")
public class MerchantController {
    /**service注入.*/
    @Autowired
    private IMerchantService merchantService;

    /**
     * 管理平台商户列表查询，支持模糊查询.
     * @param pageModel 包含查询条件
     * @return 分页结果
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object getMerchantList(@RequestBody PageModel<MerInfo> pageModel) {
        log.info("*********---------***********");
        log.info("getMerchantList:" + pageModel.getModel().getMerId());
        log.info("getMerchantList:" + pageModel.getModel().getMerName());
        log.info("getMerchantList:" + pageModel.getPageSize());
        log.info("getMerchantList:" + pageModel.getPageNum());
        return merchantService.getMerchantList(pageModel);
    }

    /**
     * 商户详情查询.
     *
     * @return MerInfo
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MerInfo getMerchantDetail(@PathVariable(name = "merchantId") String merchantId) {
        return null;
    }

    /**
     * 商户同步（后台同步）.
     */
    @RequestMapping(value = "/synchMerchant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMerchantBySystem(MerInfo merInfo) {

    }

    /**
     * 商户附件同步（后台附件同步）.
     */
    @RequestMapping(value = "/synchMerchantFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMerchantBySystem(MerInfo merInfo) {

    }

    /**
     * 商户更新（前端输入）.
     */
    @RequestMapping(value = "/{merchantId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updateMerchant(MerInfo merInfo) {

    }
}
