package com.newland.payment.domain;

public class Response {
    private int status;
    private String msg;
    private Object result;

    public Response(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Response(int status, Object result) {
        this.status = status;
        this.result = result;
    }

    public static Response ok(Object result) {
        return new Response(1, result);
    }

    public static Response ok(int status, String msg) {
        return new Response(status, msg);
    }

    public static Response fail(int status, String msg) {
        return new Response(status, msg);
    }
}
