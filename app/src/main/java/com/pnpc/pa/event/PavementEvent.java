package com.pnpc.pa.event;

/**
 * Created by markusmcgee on 10/14/15.
 */
public class PavementEvent {

    public static final String STOP_SERVICE = "STOP_SERVICE";
    public static final String STOP_SERVICE_CALLED = "STOP_SERVICE_CALLED";

    protected String tagName = "";
    protected String message = "DEFAULT MESSAGE";
    protected String className = "";
    protected String data = null;

    public PavementEvent(String tagName) {
        this.tagName = tagName;
    }

    public PavementEvent(String className, String message) {
        this.message = message;
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public String getClassName() {
        return className;
    }

    public String getTagName() {
        return tagName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean dataIsNull() {
        return (data == null);
    }

    public void setMessage(String message) {
        this.message = message;

    }
}
