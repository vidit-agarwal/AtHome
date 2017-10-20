package com.devbrain.athome.rest;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.parser.JSONParser;

import org.json.JSONObject;

/**
 * Created by Mukesh Jha on 11/29/2016.
 */
public class NetworkTransaction {
    private static final String TAG = NetworkTransaction.class.getName();

    private static NetworkTransaction instance = null;

    //for Volley API
    public RequestQueue requestQueue;

    private NetworkTransaction(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //other stuf if you need
    }

    public static synchronized NetworkTransaction getInstance(Context context) {
        if (null == instance)
            instance = new NetworkTransaction(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkTransaction getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkTransaction.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }


    public void ProcessRequest(TransactionType requestType, final String URL, final JSONObject jsonParams, final NetworkTransactionListener<String> listener) {

        JsonObjectRequest request = new JsonObjectRequest(requestType.getStatusCode(), URL, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.printLog(TAG + " request param: " + jsonParams);
                        Utility.printLog(TAG + " Url: " + URL);
                        Utility.printLog(TAG + " Response: " + response.toString());


                        if (null != response.toString() && !TextUtils.isEmpty(response.toString())) {
                            STATUS status = JSONParser.getCode(response.toString());

                            switch (status) {
                                case SUCCESS:
                                    listener.onSuccess(response.toString(), jsonParams);
                                    break;
                                case FAIL:
                                    listener.onFail("Server transaction failed.", jsonParams);
                                    break;
                                case DATA_MISSING:
                                    listener.onFail("Data missing.", jsonParams);
                                    break;
                                case LIST_EMPTY:
                                    listener.onFail("List is empty.", jsonParams);
                                    break;
                                case USER_NOT_FOUND:
                                    listener.onFail("User Not Found", jsonParams);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            int code = error.networkResponse.statusCode;
                            Utility.printLog(TAG + " Url: " + URL);
                            Utility.printLog(TAG + " Response code: " + code);
                            Utility.printLog(TAG + " Error Message: " + new String(error.networkResponse.data));

                            switch (code) {
                                case 200:
                                    listener.onSuccess("Success", jsonParams);
                                    break;
                                case 400:
                                    listener.onFail("Bad request", jsonParams);
                                    break;
                                case 401:
                                    listener.onFail("You do not have access to this", jsonParams);
                                    break;
                                case 404:
                                    listener.onFail("Resource not found", jsonParams);
                                    break;
                                case 500:
                                    listener.onNetworkError("Not able to connect with server at this moment", jsonParams);
                                    break;
                                case 503:
                                    listener.onNetworkError("Not able to connect with server at this moment", jsonParams);
                                    break;
                                case 504:
                                    listener.onNetworkError("Request timed out, please try again.", jsonParams);
                                    break;
                            }
                        } else if (error instanceof ServerError) {
                            listener.onNetworkError("Not able to connect with server at the moment, please try again.", jsonParams);
                        } else if (error instanceof AuthFailureError) {
                            listener.onNetworkError("Not able to connect with server at the moment, please try again.", jsonParams);
                        } else if (error instanceof ParseError) {
                            listener.onNetworkError("Not able to connect with server at the moment, please try again.", jsonParams);
                        } else if (error instanceof TimeoutError) {
                            listener.onNetworkError("Request timed out, please try again.", jsonParams);
                        } else if (error instanceof NoConnectionError) {
                            listener.onNetworkError("Please make sure that you are on active internet connection.", jsonParams);
                        } else if (error instanceof NetworkError) {
                            listener.onNetworkError("Not able to connect with server at the moment, please try again.", jsonParams);
                        }
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }
}
