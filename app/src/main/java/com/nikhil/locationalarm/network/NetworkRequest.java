package com.nikhil.locationalarm.network;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.nikhil.locationalarm.model.NetworkModel;
import com.nikhil.locationalarm.model.RawDataModel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NetworkRequest extends Request<NetworkModel> {

    public static final String TAG = NetworkRequest.class.getSimpleName();
    private final Response.Listener<NetworkModel> mResponseListener;
    private Map<String, String> mHeaders;
    private NetworkModel mDataModel;
    private final Gson mGson;
    private String mRequestBody;
    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    public NetworkRequest(int method, String url, Response.Listener<NetworkModel> responseListener,
                          @Nullable Response.ErrorListener listener, NetworkModel model, Map<String,
            String> headers, String requestBody) {
        super(method, url, listener);
        this.mResponseListener = responseListener;
        this.mDataModel = model;
        this.mHeaders = headers;
        this.mRequestBody = requestBody;
        mGson = new Gson();

    }

    @Override
    protected Response<NetworkModel> parseNetworkResponse(NetworkResponse response) {
        try {
            if (response.data != null) {
                String jsonString = new String(response.data);
                mDataModel = getNormalDataModel(response, jsonString);

                return Response.success(mDataModel,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            VolleyError volleyError = new VolleyError(response);

            return Response.error(volleyError);
        }

        return null;

    }

    @Override
    protected void deliverResponse(NetworkModel response) {
        mResponseListener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? super.getBody() : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.put("Content-Type", "application/json");
        mHeaders.put("Accept", "application/json");
        return mHeaders;
    }

    private NetworkModel getNormalDataModel(NetworkResponse response, String jsonString) {
        if (mDataModel instanceof RawDataModel) {
            return getRawDataModel(response, jsonString);
        }

        return mGson.fromJson(jsonString, mDataModel.getClass());
    }

    private NetworkModel getRawDataModel(NetworkResponse response, String content) {
        RawDataModel model = (RawDataModel) this.mDataModel;
        model.setRawContent(content);
        model.setHeaders(response.headers);
        return model;
    }
}
