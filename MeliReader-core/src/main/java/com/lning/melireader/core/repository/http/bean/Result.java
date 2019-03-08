package com.lning.melireader.core.repository.http.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lning.melireader.core.app.constant.ResponseCode;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.LogUtils;

/**
 * Meli咨询自定义响应结构
 */
public class Result {


    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;


    private Result() {

    }

    private Result(Object data) {
        this.status = 2000;
        this.msg = "OK";
        this.data = data;
    }

    private Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result success() {
        return new Result(null);
    }

    public static Result build(ResponseCode code) {
        return new Result(code.getStatus(), code.getMsg(), null);
    }

    public static Result build(ResponseCode code, Object data) {
        return new Result(code.getStatus(), code.getMsg(), data);
    }

    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为ResponseResult对象
     *
     * @param jsonData json数据
     * @param clazz    ResponseResult中的object类型
     * @return
     */
    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            Result result = JSON.parseObject(jsonData, new
                    TypeReference<Result>() {
                    });
            if (result.getData() != null && !TextUtils.isEmpty(result.getData().toString()) && !CommonUtils.isBaseType(clazz))
                result.setData(JSON.parseObject(result.getData().toString(), clazz));
            return result;
        } catch (Exception e) {
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return JSON.parseObject(json, Result.class);
        } catch (Exception e) {
            LogUtils.d(e.toString());
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            Result result = JSON.parseObject(jsonData, new
                    TypeReference<Result>() {
                    });
            if (result.getData() == null || !TextUtils.isEmpty(result.getData().toString())) {
                result.setData(JSON.parseArray(result.getData().toString(), clazz));
            }
            return result;
        } catch (Exception e) {
            LogUtils.d("类型转换错误：" + e.toString());
            return Result.build(0000, "类型转换错误：" + e.toString());
        }
    }

    public static String toJsonString(Result result) {
        return JSON.toJSONString(result);
    }

}
