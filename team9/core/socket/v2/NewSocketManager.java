package core.socket.v2;

import core.socket.SocketPayload;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewSocketManager {
    private static final NewSocketManager instance = new NewSocketManager();

    public static NewSocketManager getInstance() {
        return instance;
    }

    private final Map<String, SocketPayload> socketMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public NewSocketManager() {
        // 주기적으로 만료된 소켓을 정리
        scheduler.scheduleAtFixedRate(this::removeExpiredSockets, 0, 5, TimeUnit.SECONDS);
    }

    public SocketPayload getOrCreateSocket(String url, int timeout, int maxUse) {
        SocketPayload socketPayload = socketMap.get(url);

        // 기존 소켓이 없거나 만료되었으면 새로 생성
        if (socketPayload == null || !socketPayload.canUse()) {
            Socket newSocket = createNewSocket(url);
            socketPayload = new SocketPayload(newSocket, timeout, maxUse);
            socketMap.put(url, socketPayload);
        } else {
            socketPayload.incrementUse(); // 소켓 사용 횟수 증가
        }

        return socketPayload;
    }

    private Socket createNewSocket(String url) {
        try {
            // URL 기반 소켓 생성 (예시)
            String host = url; // 실제로는 URL을 분석하여 host와 port를 분리해야 합니다.
            int port = 80;     // 기본 포트 예시
            return new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create socket for URL: " + url, e);
        }
    }

    private void removeExpiredSockets() {
        socketMap.values().removeIf(SocketPayload::isExpired); // 만료된 소켓 제거
    }

    public void shutdown() {
        scheduler.shutdown();
        socketMap.values().forEach(SocketPayload::close);
    }
}