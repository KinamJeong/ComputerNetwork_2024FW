package config;

import core.HttpClient;
import core.cache.CacheManager;
import core.cookie.CookieManager;
import core.header.HttpHeaders;
import core.request.HttpRequestBuilder;
import core.request.HttpRequestSender;
import core.response.HttpResponseParser;
import core.response.HttpResponseReceiver;
import core.socket.HttpSocketManager;
import core.util.URLParser;
import redering.ResponseRenderer;
import redering.impl.SwingResponseRenderer;

public class AppConfig {
    private final CookieManager cookieManager;
    private final CacheManager cacheManager;
    private final URLParser urlParser;
    private final HttpHeaders requestHeaders;
    private final HttpSocketManager socketManager;
    private final HttpRequestBuilder httpRequestBuilder;
    private final HttpRequestSender httpRequestSender;
    private final HttpResponseParser httpResponseParser;
    private final ResponseRenderer responseRenderer;
    private final HttpResponseReceiver httpResponseReceiver;
    private final HttpClient httpClient;

    public AppConfig(String urlString) throws Exception {
        this.cookieManager = CookieManager.getInstance();   // 싱글톤 패턴
        this.cacheManager = CacheManager.getInstance();     // 싱글톤 패턴
        this.urlParser = new URLParser(urlString);
        this.requestHeaders = new HttpHeaders(urlParser.getHost());
        this.socketManager = new HttpSocketManager(urlParser.getHost(), urlParser.getPort());
        this.httpRequestBuilder = new HttpRequestBuilder(urlParser, requestHeaders, cacheManager, cookieManager);
        this.httpRequestSender = new HttpRequestSender(httpRequestBuilder, socketManager);
        this.httpResponseParser = new HttpResponseParser(urlParser, cacheManager, cookieManager);
        this.responseRenderer = new SwingResponseRenderer();
        this.httpResponseReceiver = new HttpResponseReceiver(httpResponseParser, socketManager, responseRenderer);
        this.httpClient = new HttpClient(httpRequestSender, httpResponseReceiver, socketManager);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public URLParser getUrlParser() {
        return urlParser;
    }

    public HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public HttpSocketManager getSocketManager() {
        return socketManager;
    }

    public HttpRequestBuilder getHttpRequestBuilder() {
        return httpRequestBuilder;
    }

    public HttpRequestSender getHttpRequestSender() {
        return httpRequestSender;
    }

    public ResponseRenderer getResponseRenderer() {
        return responseRenderer;
    }

    public HttpResponseReceiver getHttpResponseReceiver() {
        return httpResponseReceiver;
    }
}