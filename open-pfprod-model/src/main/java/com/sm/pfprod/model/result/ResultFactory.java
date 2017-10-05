package com.sm.pfprod.model.result;

import java.util.List;

/**
 * @ClassName: ResultFactory
 * @Description: Result以及PageResult工厂生成类
 * @Author yangtongbin
 * @Date 2017/9/9 16:05
 */
public class ResultFactory {

    /**
     * 初始化一个成功的结果，返回普通业务数据实体集合
     *
     * @param count 总数
     * @param data  业务list集合
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> initPageResultWithSuccess(Long count, List<T> data) {
        PageResult<T> result = new PageResult<T>();
        result.setCode("0");
        result.setMsg("分页查询成功");
        result.setCount(count);
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> initPageResultWithError(String errorCode, String errorDesc) {
        PageResult<T> result = new PageResult<T>();
        result.setCode(errorCode);
        result.setMsg(errorDesc);
        return result;
    }

    /**
     * 初始化一个成功的结果，返回普通业务数据实体集合
     *
     * @param msg   信息
     * @param count 总数
     * @param data  业务list集合
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> initPageResultWithSuccess(String msg, Long count, List<T> data) {
        PageResult<T> result = new PageResult<T>();
        result.setCode("0");
        result.setMsg(msg);
        result.setCount(count);
        result.setData(data);
        return result;
    }

    /**
     * 初始化一个错误的消息
     *
     * @param errorCode
     * @param errorDesc
     * @param <T>
     * @return
     */
    public static <T> Result initResultWithError(String errorCode, String errorDesc) {
        Result result = new Result();
        result.setIsSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorDesc(errorDesc);
        return result;
    }
}
