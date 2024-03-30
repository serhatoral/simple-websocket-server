package dev.serhat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.serhat.websocket.model.Person;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class BasicWebSocketHandler extends TextWebSocketHandler {

    // ConcurrentHashMap is  trade safe , HashMap not trade safe
    private final ConcurrentHashMap<String, WebSocketSession> connectedSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("\n"+ session.getId()+" bağlandı!");
        connectedSessions.put(session.getId(), session);
        writeConnectedClients();

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(message.getPayload(), Person.class);

        System.out.println("\n"+ session.getId()+" soket idli client'tan gelen veri: " + message.getPayload());

        String response = String.format("Merhaba %s! Veriniz alındı. Göderdiğiniz veri: %s",person.getName(),message.getPayload());
        session.sendMessage(new TextMessage(response));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("\n"+ session.getId()+" id'li kullanıcı disconnect oldu!");
        connectedSessions.remove(session.getId());
        writeConnectedClients();
    }

    private void writeConnectedClients(){

        System.out.println("\n -------- Sokete Bağlı Kullanıcılar");
        connectedSessions.forEach((key, value)->{
            System.out.println("Soket Id: " + key + "  | Session data:" +value);
        });
    }
}
