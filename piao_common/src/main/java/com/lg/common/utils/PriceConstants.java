package com.lg.common.utils;

import java.math.BigDecimal;

/**
 * @author L
 * @version 1.0
 * @ClassName: PriceConstants
 * @date: 2020/1/12 14:03
 * @since JDK 1.8
 */
public class PriceConstants {
    public static final int STORE_SCALE = 4; //存储精度
    public static final int CAL_SCALE = 8; //运算精度
    public static final int DISPLAY_SCALE = 2; //显示精度
    public static final BigDecimal ZERO = new BigDecimal("0.0000"); //系统级别的0
}
