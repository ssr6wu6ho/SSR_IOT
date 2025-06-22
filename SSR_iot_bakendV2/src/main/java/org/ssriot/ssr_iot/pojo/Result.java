package org.ssriot.ssr_iot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用的API返回结果封装类
 * 用于统一API接口返回结果的格式
 *
 * @param <T> 泛型参数，表示返回的数据类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;   //业务状态码 0-成功 1-失败
    private String msg;     //状态码对应的描述信息
    private T data;//返回的具体数据，类型由泛型参数<T>确定

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
