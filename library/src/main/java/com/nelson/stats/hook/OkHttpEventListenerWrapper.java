package com.nelson.stats.hook;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * PROXY Http EventListener
 *
 * Created by Nelson on 2019-11-26.
 */
public class OkHttpEventListenerWrapper extends EventListener {

    public static EventListener wrap(EventListener listener) {
        return new OkHttpEventListenerWrapper(listener);
    }

    private final EventListener oldEventListener;
    private final EventListener ownEventListener;

    OkHttpEventListenerWrapper(EventListener oldEventListener) {
        this.oldEventListener = oldEventListener;
        this.ownEventListener = new HttpEventListener();
    }

    public EventListener getOldEventListener() {
        return this.oldEventListener;
    }

    public EventListener getOwnEventListener() {
        return this.ownEventListener;
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        this.oldEventListener.callStart(call);
        this.ownEventListener.callStart(call);
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        this.oldEventListener.dnsStart(call, domainName);
        this.ownEventListener.dnsStart(call, domainName);
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        this.oldEventListener.dnsEnd(call, domainName, inetAddressList);
        this.ownEventListener.dnsEnd(call, domainName, inetAddressList);
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        this.oldEventListener.connectStart(call, inetSocketAddress, proxy);
        this.ownEventListener.connectStart(call, inetSocketAddress, proxy);
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
        this.oldEventListener.secureConnectStart(call);
        this.ownEventListener.secureConnectStart(call);
    }

    @Override
    public void secureConnectEnd(Call call, Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        this.oldEventListener.secureConnectEnd(call, handshake);
        this.ownEventListener.secureConnectEnd(call, handshake);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        this.oldEventListener.connectEnd(call, inetSocketAddress, proxy, protocol);
        this.ownEventListener.connectEnd(call, inetSocketAddress, proxy, protocol);
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        this.oldEventListener.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        this.ownEventListener.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
        this.oldEventListener.connectionAcquired(call, connection);
        this.ownEventListener.connectionAcquired(call, connection);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
        this.oldEventListener.connectionReleased(call, connection);
        this.ownEventListener.connectionReleased(call, connection);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
        this.oldEventListener.requestHeadersStart(call);
        this.ownEventListener.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
        this.oldEventListener.requestHeadersEnd(call, request);
        this.ownEventListener.requestHeadersEnd(call, request);
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
        this.oldEventListener.requestBodyStart(call);
        this.ownEventListener.requestBodyStart(call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        this.oldEventListener.requestBodyEnd(call, byteCount);
        this.ownEventListener.requestBodyEnd(call, byteCount);
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
        this.oldEventListener.responseHeadersStart(call);
        this.ownEventListener.responseHeadersStart(call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
        this.oldEventListener.responseHeadersEnd(call, response);
        this.ownEventListener.responseHeadersEnd(call, response);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
        this.oldEventListener.responseBodyStart(call);
        this.ownEventListener.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        this.oldEventListener.responseBodyEnd(call, byteCount);
        this.ownEventListener.responseBodyEnd(call, byteCount);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        this.oldEventListener.callEnd(call);
        this.ownEventListener.callEnd(call);
    }
}
