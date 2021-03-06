package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * 利率信息实体类.
 * @author Mxia
 * */
@Getter
@Setter
public class Interest extends BaseEntity {
    /**利率编号.*/
    private Integer ittId;
    /**产品编号.*/
    private String iProId;
    /**产品名称.*/
    private String iProName;
    /**分期数.*/
    private Integer times;
    /**利率.*/
    private BigDecimal intRate;
}
