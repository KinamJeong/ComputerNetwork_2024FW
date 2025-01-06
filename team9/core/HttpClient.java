package core;

import core.request.HttpRequestSender;
import core.response.HttpResponseReceiver;
import core.socket.HttpSocketManager;

public class HttpClient {
    private final HttpRequestSender requestSender;
    private final HttpResponseReceiver responseReceiver;
    private final HttpSocketManager socketManager;

    public HttpClient(final HttpRequestSender requestSender, final HttpResponseReceiver responseReceiver, final HttpSocketManager socketManager) {
        this.requestSender = requestSender;
        this.responseReceiver = responseReceiver;
        this.socketManager = socketManager;
    }

    public void executeRequest() {
        // 요청 전송
        requestSender.sendRequest();

        // 응답 수신 및 파싱 후 렌더링
        responseReceiver.receiveResponse();

        // 커넥션 종료
        closeConnection();
    }

    public void closeConnection() {
        socketManager.closeConnection();
    }
}