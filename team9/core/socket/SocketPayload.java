package core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketPayload {
    private final Socket socket;
    private final long lastUsedTime;
    private final int timeout;
    private int useCount;
    private final int maxUse;

    public SocketPayload(Socket socket, int timeout, int maxUse) {
        this.socket = socket;
        this.timeout = timeout;
        this.maxUse = maxUse;
        this.lastUsedTime = System.currentTimeMillis();
        this.useCount = 0;
    }

    public BufferedReader getBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean canUse() {
        return useCount < maxUse && !isExpired();
    }

    public void incrementUse() {
        useCount++;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - lastUsedTime > timeout * 1000L;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}