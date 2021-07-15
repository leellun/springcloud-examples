package com.newland.springcloudsentinel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int status;
    private String msg;
    private Object result;

    public Response(int status, String msg) {
        this.msg = msg;
        this.status = status;
    }
}
