package com.mitocode.springai.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHistory {

    private static final Logger logger = LoggerFactory.getLogger(ChatHistory.class);
    private final Map<String, List<Message>> chatHistoryLog;

    public ChatHistory() {
        this.chatHistoryLog = new ConcurrentHashMap<>();
    }

    public void addMessage(String chatId, Message message) {
        this.chatHistoryLog.computeIfAbsent(chatId, key -> new ArrayList<>()).add(message);
    }

    public List<Message> getAll(String chatId) {
        return this.chatHistoryLog.getOrDefault(chatId, List.of());
    }

    public List<Message> getLastN(String chatId, int lastN) {
        List<Message> response = getAll(chatId);
        if (response.size() <= lastN) return response;

        int from = response.size() - lastN;
        int to = response.size();
        logger.debug("Returning last {} messages from {} to {}", lastN, from, to);
        return new ArrayList<>(response.subList(from, to));
    }
}
