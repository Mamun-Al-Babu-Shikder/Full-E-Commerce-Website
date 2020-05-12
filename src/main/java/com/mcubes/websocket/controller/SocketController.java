package com.mcubes.websocket.controller;

import com.mcubes.model.NotifyItemCarted;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by A.A.MAMUN on 4/25/2020.
 */
@RestController
public class SocketController {


    @MessageMapping("/send-item-carted-message")
    @SendTo("/from-server/listen-item-carted-notification")
    private NotifyItemCarted sendItemCartedNotification(NotifyItemCarted notifyItemCarted)
    {
        System.out.println("#Income Message : "+notifyItemCarted.toString());
        return notifyItemCarted;
    }


}
