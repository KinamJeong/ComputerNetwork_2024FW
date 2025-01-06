package redering.impl;

import redering.ResponseRenderer;

public class ConsoleResponseRenderer implements ResponseRenderer {
    @Override
    public void render(String responseBody) {
        System.out.println("[Response Body]");
        System.out.println(responseBody);
    }
}