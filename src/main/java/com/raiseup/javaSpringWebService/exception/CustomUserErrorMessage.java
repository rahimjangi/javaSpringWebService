package com.raiseup.javaSpringWebService.exception;

import java.util.Date;

public class CustomUserErrorMessage {
    private String className;
    private String methodName;
    private Date timestamp;
    private String message;

    public CustomUserErrorMessage() {
    }

    public CustomUserErrorMessage(String className, String methodName, Date timestamp, String message) {
        this.className = className;
        this.methodName = methodName;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
