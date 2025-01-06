package core.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaders {
    private final Map<String, String> headers;

    // 요청 헤더에 사용되는 생성자
    public HttpRequestHeaders(String host) {
        headers = new HashMap<>();
        headers.put("Host", host);
        headers.put("User-Agent", "JavaSocketClient/1.0");  // 별 의미 없는 값
        headers.put("Accept", "text/html,application/json");
        headers.put("Connection", "keep-alive");    // TTL
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getAllHeaders() {
        return headers;
    }
}