package com.sm.pfprod.facade.monitor;

import com.sm.pfprod.model.vo.monitor.PfServerVo;

/**
 * @ClassName: PfMonitorFacade
 * @Description: 监控接口
 * @Author yangtongbin
 * @Date 2018/9/5 11:31
 */
public interface PfMonitorFacade {

    /**
     * 查询服务器信息
     *
     * @return
     */
    PfServerVo selectServerInfo();
}
