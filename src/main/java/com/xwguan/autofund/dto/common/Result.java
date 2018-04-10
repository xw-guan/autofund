package com.xwguan.autofund.dto.common;

/**
 * 所有ajax请求通用返回类型, 封装json结果
 * 
 * @author XWGuan
 * @param <T>
 */
public class Result<T> {

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 请求返回的数据
     */
    private T data;

    /**
     * 请求返回的错误信息
     */
    private String error;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Result [success=" + success + ", data=" + data + ", error=" + error + "]";
    }

}
