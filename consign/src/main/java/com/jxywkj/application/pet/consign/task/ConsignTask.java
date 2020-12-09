package com.jxywkj.application.pet.consign.task;

import com.jxywkj.application.pet.service.facade.consign.ConsignOrderTaskService;
import com.jxywkj.application.pet.service.spring.consign.ConsignOrderServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * 订单定时任务调度器
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignTask
 * @date 2019/11/21 15:11
 **/
@Component
public class ConsignTask {
    @Resource
    ConsignOrderTaskService consignOrderTaskService;

    /*
     * corn表达式                        特殊字符
     * 第一个参数：秒                    , - * /
     * 第二个参数：分钟                   , - * /
     * 第三个参数：小时                   , - * /
     * 第四个参数：日期（1 ~ 31）           , - * / L W C
     * 第五个参数：月份（1 ~ 12）           , - * /
     * 第六个参数：星期（1 ~ 7）            , - * /
     * 第七个参数：年（可选 空值 或者 1970 ~ 2099 ） , - * /
     *
     *
     *
     * * 表示每个时刻
     * ？ 表示占位符 也就是无意义
     * - 代表一个范围 如10-12 表示10点到12点之间
     * ，表示一个列表达式 比如 1,2,3,4 表示1,2,3,4
     * / 字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
     *         如“0/15”表示每隔15分钟执行一次,“0”表示为从“0”分开始, “3/20”表示表示
     *         每隔 20分钟执行一次，“3”表示从第3分钟开始执行
     *
     * */

    /**
     * <p>
     * 每天12点执行
     * 清除订单单号
     * </p>
     *
     * @param
     * @return void
     * @author LiuXiangLin
     * @date 18:12 2019/11/14
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanConsignIndex() {
        ConsignOrderServiceImpl.CITY_ORDER_INDEX.clear();
    }

    /**
     * <p>
     * 检查订单支付状态
     * 如果订单已经支付但是未回写状态
     * 则回写订单状态
     * 每次隔30分钟执行一次
     * </p>
     *
     * @author LiuXiangLin
     * @date 14:52 2019/11/21
     **/
    @Scheduled(cron = "0 0/30 * * * ?")
    public void checkConsignOrder() {
        consignOrderTaskService.checkConsignOrder();
    }

    /**
     * <p>
     * 自动确认订单
     * 每隔10分钟执行一次
     * 超过24小时自动确认收货
     * </p>
     *
     * @author LiuXiangLin
     * @date 16:59 2019/12/30
     **/
    @Scheduled(cron = "0 0/10 * * * ?")
    public void automaticConfirmConsignOrder() {
        consignOrderTaskService.automaticConfirmConsignOrder();
    }

    /**
     * <p>
     * 自动取消订单
     * 每隔18分钟执行一次
     * 下单之后超过24小时自动取消订单
     * </p>
     *
     * @author LiuXiangLin
     * @date 9:33 2020/1/2
     **/
    @Scheduled(cron = "0 0/18 * * * ?")
    public void cancelUnpaidOrder() {
        consignOrderTaskService.cancelUnpaidOrder();
    }


}
