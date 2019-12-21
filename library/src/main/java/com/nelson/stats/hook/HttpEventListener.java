package com.nelson.stats.hook;

import android.util.Log;
import com.nelson.stats.NetworkStatsManager;
import com.nelson.stats.User;
import com.nelson.stats.db.NetworkCallEntity;
import com.nelson.stats.utils.TimeUtil;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http Event Monitoring
 *
 * Created by Nelson on 2019-11-26.
 */
public class HttpEventListener extends EventListener {

    private static final String TAG = "nstats.eventListener";

    private long mRequestBytes;
    private long mResponseBytes;
    private int mResponseCode;

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        // try to clear data before every http call.
        NetworkStatsManager.clearIntervalData();
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
        this.mRequestBytes += request.headers().byteCount();
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        this.mRequestBytes += byteCount;
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
        this.mResponseBytes += response.headers().byteCount();
        this.mResponseCode = response.code();
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        this.mResponseBytes += byteCount;
    }

    /**
     * Called on worker thread, can do hard work!
     */
    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        buryTag(call.request());
        saveCallEntity(call.request());
    }

    private void saveCallEntity(Request request) {
        NetworkCallEntity callEntity = new NetworkCallEntity();
        User user = NetworkStatsManager.getUser();
        callEntity.userId = user.getUserId();
        callEntity.userName = user.getUserName();
        callEntity.url = request.url().toString();
        callEntity.domain = request.url().host();
        callEntity.method = request.method();
        callEntity.requestBytes = this.mRequestBytes;
        callEntity.responseBytes = this.mResponseBytes;
        callEntity.totalBytes = this.mRequestBytes + this.mResponseBytes;
        callEntity.responseCode = this.mResponseCode;
        callEntity.timestamp = System.currentTimeMillis();
        callEntity.formatTimestamp = TimeUtil.getTimeString(callEntity.timestamp, TimeUtil.DEFULT_FORMAT);
        NetworkStatsManager.saveData(callEntity);
    }

    private void buryTag(Request request) {
        User user = NetworkStatsManager.getUser();
        String userId = user.getUserId();
        String userName = user.getUserName();
        long totalBytes = this.mRequestBytes + this.mResponseBytes;
        Log.d(TAG, String.format("userName: %s, userId: %s, Total Bytes: %d = (sendBytes: %d, receivedBytes: %d), %s",
                userName, userId,
                totalBytes, this.mRequestBytes,
                this.mResponseBytes, stripRequestStr(request)));
    }

    private String stripRequestStr(Request request) {
        return "Request{method="
                + request.method()
                + ", url="
                + request.url()
                + ", contentType="
                + request.header("Content-Type")
                + '}';
    }
}
