package com.devbrain.athome.rest;

/**
 * Created by Mukesh Jha on 11/29/2016.
 */
public interface NetworkTransactionListener<T>
{
    public void onSuccess(T object, Object obj);
    public void onFail(T object, Object obj);
    public void onExist(T object, Object obj);
    public void onNetworkError(T object, Object obj);
}
