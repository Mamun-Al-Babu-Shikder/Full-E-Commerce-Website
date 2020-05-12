package com.mcubes.websocket.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Created by A.A.MAMUN on 4/25/2020.
 */
@Component
public class SocketListener {

    @EventListener
    public void handelWebSocketConnectListener(SessionConnectedEvent event){
        System.out.println("# WebSocket Connected : "+event.getMessage());
    }

    @EventListener
    public void handelWebSocketDisconnectListener(SessionDisconnectEvent event){
        System.out.println("# WebSocket Disconnect : "+event.getMessage());
    }

}
