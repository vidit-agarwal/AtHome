package com.devbrain.athome.rest;

/**
 * Created by Mukesh Jha on 11/29/2016.
 */
public enum STATUS
{
    FAIL(0),
    SUCCESS(1),
    USER_NOT_FOUND(2),
    LIST_EMPTY(3),
    DATA_MISSING(4);

    private final int code;

    STATUS(int coode)
    {
        this.code = coode;
    }

    public int getStatusCode()
    {
        return this.code;
    }
}
