package com.sm.pfprod.model.vo.monitor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfServerVo
 * @Description: 服务器信息
 * @Author yangtongbin
 * @Date 2018/9/5 11:33
 */
@Setter
@Getter
@ToString
public class PfServerVo implements Serializable {

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * java版本
     */
    private String javaVersion;

    /**
     * CPU个数
     */
    private int cpuNum;

    /**
     * JVM可以使用的总内存
     */
    private long totalMemory;

    /**
     * JVM可以使用的剩余内存
     */
    private long freeMemory;

    /**
     * 用户的当前工作目录
     */
    private String userDir;
}
