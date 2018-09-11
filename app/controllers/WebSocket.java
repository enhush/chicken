package controllers;

import play.libs.F.Either;
import play.libs.F.EventStream;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.WebSocketClose;
import play.mvc.WebSocketController;

import java.util.Date;

import static play.libs.F.Matcher.ClassOf;
import static play.libs.F.Matcher.Equals;
import static play.mvc.Http.WebSocketEvent.TextFrame;

/**
 * Created by enkhamgalan on 3/8/15.
 */
public class WebSocket extends WebSocketController {

    public static void join(Long id) {
        Long userId = id;
        long uuid = (new Date()).getTime();
        UserLiveRoom room = UserLiveRoom.get();
        // Socket connected, join the chat room
        EventStream<UserLiveRoom.Event> roomMessagesStream = room.join(userId,uuid);

        // Loop while the socket is open
        while (inbound.isOpen()) {
            // Wait for an event (either something coming on the inbound socket channel, or UserLiveRoom messages)
            Either<Http.WebSocketEvent, UserLiveRoom.Event> e = await(Promise.waitEither(
                    inbound.nextEvent(),
                    roomMessagesStream.nextEvent()
            ));
            // Case: User typed 'quit'
            for (String userMessage : TextFrame.and(Equals("quit")).match(e._1)) {
                room.onlineStateFn(userId,uuid,UserLiveRoom.offline);
                disconnect();
            }
            for (String userData : TextFrame.match(e._1)) {
                room.webRTCData(userData,userId);
            }
            // Case: Someone receiveCount
            for (UserLiveRoom.NotificationEvent notificationEvent : ClassOf(UserLiveRoom.NotificationEvent.class).match(e._2)) {
                outbound.sendJson(notificationEvent.json);
            }
            for (UserLiveRoom.NotificationReminder reminderTag : ClassOf(UserLiveRoom.NotificationReminder.class).match(e._2)) {
                outbound.sendJson(reminderTag.json);
            }
            for (UserLiveRoom.ChatMessageEvents chatMessageEvent : ClassOf(UserLiveRoom.ChatMessageEvents.class).match(e._2)) {
                System.out.println("connected User Id "+ userId);
                if (chatMessageEvent.remoteId == userId || chatMessageEvent.senderId == userId ){
                    outbound.sendJson(chatMessageEvent.data);
                    System.out.println("SEND:");
                }
            }

            for (UserLiveRoom.OnlineStateEvent onlineState : ClassOf(UserLiveRoom.OnlineStateEvent.class).match(e._2)) {
                outbound.sendJson(onlineState.json);
            }
            // Case: The socket has been closed
            for (WebSocketClose closed : Http.WebSocketEvent.SocketClosed.match(e._1)) {
                room.onlineStateFn(userId,uuid,UserLiveRoom.offline);
                disconnect();
            }
        }
    }
}
