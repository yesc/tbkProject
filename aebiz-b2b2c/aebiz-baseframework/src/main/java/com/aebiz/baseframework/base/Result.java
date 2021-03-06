package com.aebiz.baseframework.base;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;

/**
 * Created by wizzer on 2016/12/21.
 */
public class Result{

    private int code;
    private String msg;
    private Object data;

    public Result() {

    }

    public static Result NEW() {
        return new Result();
    }


    public Result addCode(int code) {
        this.code = code;
        return this;
    }

    public Result addMsg(String msg) {
        this.msg = Strings.isBlank(msg) ? "" : Message.getMessage(msg);
        return this;
    }

    public Result addData(Object data) {
        this.data = data;
        return this;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = Strings.isBlank(msg) ? "" : Message.getMessage(msg);
        this.data = data;
    }

    public static Result success(String content) {
        return new Result(0, content, null);
    }

    public static Result success(String content, Object data) {
        return new Result(0, content, data);
    }

    public static Result error(int code, String content) {
        return new Result(code, content, null);
    }

    public static Result error(String content) {
        return new Result(1, content, null);
    }

    public static Result success() {
        return new Result(0, "globals.result.success", null);
    }

    public static Result error() {
        return new Result(1, "globals.result.error", null);
    }

    @Override
    public String toString(){
        return Json.toJson(this,JsonFormat.compact());
    }

}
