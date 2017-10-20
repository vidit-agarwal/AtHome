package com.devbrain.athome.rest;

/**
 * Created by Mukesh Jha on 11/29/2016.
 */
public enum TransactionType
{
    GET(0), POST(1), PUT(2), DELETE(3), HEAD(4), OPTIONS(5), TRACE(6), PATCH(7);
    private final int code;

    TransactionType(int coode) {
        this.code = coode;
    }

    public int getStatusCode() {
        return this.code;
    }
}
