package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controllers.BackgroundModes.OfflineUserDo;
import models.*;
import play.libs.F;
import play.mvc.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enkhamgalan on 3/7/15.
 */
public class UserLiveRoom extends Controller {
    // ~~~~~~~~~ Let's chat!

    final F.EventStream<Event> chatEvents = new F.EventStream<Event>();
    public static final int offline = 0;
    public static final int online = 1;
    public static final HashMap<Long, Long> onlineUsers = new HashMap<Long, Long>();

    /**
     * For WebSocket, when a user join the room we return a continuous event stream
     * of ChatEvent
     */
    public F.EventStream<Event> join(Long userId,long uuid) {
        System.out.println("JOIN: " + userId + " = " + Consts.myDateFormat2.format(new Date()));
        onlineStateFn(userId,uuid, online);
        return chatEvents;
    }

    public void notification(User owner, String type, List<User> users, Long id) {
        String uid = ",";
        Date date = new Date();
        users.remove(owner);
        String message = "";
        int status = 0;
        if (type.equals("Post") || type.equals("PostEvent") || type.equals("PostNew")) {
            Post post = Post.findById(id);
            message = type.equals("Post") ? "Таны нийтлэлд сэтгэгдэл бичлээ" : "Шинэ нийтлэл бичлээ";
            for (User us : users) {
                Notification notification = new Notification();
                notification.date = date;
                notification.message = message;
                notification.sender = owner;
                notification.acceptor = us;
                notification.post = post;
                notification.create();
                uid += us.id + ",";
            }
//        } else if(type.equals("Meg")){
//            status = 0;
//            Meg meg = Meg.findById(id);
//            message = "Танд " + owner + " -c " + meg.megNumber + " дугаартай МЭГ-ний мэдээлэл ирлээ";
//            for (User us : users) {
//                NotificationMessage notification = new NotificationMessage();
//                notification.date = date;
//                notification.sender = owner;
//                notification.acceptor = us;
//                notification.meg = meg;
//                notification.message = message;
//                notification.status = 0;
//                uid += us.id + ",";
//                notification.create();
//            }
//        } else if(type.equals("MegTasagConf") || type.equals("MegTasagRej") ||
//                type.equals("MegBaitsConf") || type.equals("MegBaitsRej")) {
//            Meg meg = Meg.findById(id);
//            if (type.equals("MegTasagConf")) {
//                status = 2;
//                message = owner + " " + meg.megNumber + " дугаартай МЭГ баталлаа";
//            }
//            if (type.equals("MegTasagRej")) {
//                status = 3;
//                message = owner + " " + meg.megNumber + " дугаартай МЭГ батлахаас татгазлаа";
//            }
//            if (type.equals("MegBaitsConf")) {
//                status = 5;
//                if (owner.userType_id == 2) {
//                    Police police = Police.findById(owner.police.id);
//                    message = owner + " " + meg.megNumber + " дугаартай МЭГ " + police.checkPoint + " постоор нэвтрэхийг зөвшөөрлөө";
//                } else {
//                    message = owner + " " + meg.megNumber + " дугаартай МЭГ постоор нэвтрэхийг зөвшөөрлөө";
//                }
//            }
//            if (type.equals("MegBaitsRej")) {
//                status = 6;
//                if (owner.userType_id == 2) {
//                    Police police = Police.findById(owner.police.id);
//                    message = owner + " " + meg.megNumber + " дугаартай МЭГ " + police.checkPoint + " постоор нэвтрэхийг хориглолоо";
//                } else {
//                    message = owner + " " + meg.megNumber + " дугаартай МЭГ постоор нэвтрэхийг хориглолоо";
//                }
//            }
            for (User us : users) {
                NotificationMessage notification = new NotificationMessage();
                notification.date = date;
                notification.sender = owner;
                notification.acceptor = us;
//                notification.meg = meg;
                notification.message = message;
                notification.status = status;
                uid += us.id + ",";
                notification.create();
            }
        }
        if (uid.length() > 1) {
            JsonObject sendJson = new JsonObject();
            sendJson.addProperty("mainType", "notification");
            sendJson.addProperty("type", type);
            sendJson.addProperty("senderId", owner.id.intValue());
            sendJson.addProperty("sender", owner.toString());
            sendJson.addProperty("uid", uid);
            sendJson.addProperty("message", message);
            sendJson.addProperty("status", status);
            chatEvents.publish(new NotificationEvent(sendJson));
        }
    }

    public void webRTCData(String data, long senderId) {
        JsonObject json = (JsonObject) new JsonParser().parse(data);
        json.addProperty("senderId", senderId);
        JsonElement mainTypeElement = json.get("mainType");
        long remoteUserId = json.get("remoteId").getAsLong();
        if (mainTypeElement != null ) {
            String mainType = mainTypeElement.getAsString();
            if (mainType.equals("chat")) {
                User remoteUser = User.findById(remoteUserId);
                User senderUser = User.findById(senderId);
                System.out.println("senderUser:"+senderUser.id);
                System.out.println("remoteUser:"+remoteUser.id);
                JsonElement attachElement = json.get("attach");
                if (remoteUser.webFireBasetoken != null && !remoteUser.webFireBasetoken.equals("")) {
                    System.out.println("notification:");
                    JsonObject jsonPhone = new JsonObject();
                    jsonPhone.addProperty("to", remoteUser.webFireBasetoken);
                    JsonObject notiPhone = new JsonObject();
                    notiPhone.addProperty("title", senderUser.toString());
                    notiPhone.addProperty("body", attachElement == null ? json.get("msg").getAsString() : "Файл илгээлээ");
                    notiPhone.addProperty("click_action", CompanyConf.ip + "/chat/" + senderId);
                    notiPhone.addProperty("icon", CompanyConf.ip + senderUser.profilePicture);
                    jsonPhone.add("notification", notiPhone);
                    Functions.sendNotificationPhone(jsonPhone, "android");
                }

                ChatMessage chatMessage = new ChatMessage(senderUser, remoteUser,  attachElement == null ? json.get("msg").getAsString() : null);
                chatMessage.create();
                if (attachElement != null ) {
                    JsonObject attach = attachElement.getAsJsonObject();
                    ChatMessageAttach chatMessageAttach = new ChatMessageAttach(
                            attach.get("fileName").getAsString(),
                            attach.get("fileDir").getAsString(),
                            attach.get("extension").getAsString(),
                            Float.parseFloat(attach.get("filesize").getAsString()),
                            chatMessage);
                    chatMessageAttach.create();
                    chatMessage.attach = chatMessageAttach;
                    chatMessage._save();
                }

            }
        }
        chatEvents.publish(new ChatMessageEvents(json, remoteUserId, senderId));
    }

    public void plushUserState(Long userId, int state) {
        User plushUser = User.findById(userId);
        JsonObject sendJson = new JsonObject();
        sendJson.addProperty("mainType", "chat");
        sendJson.addProperty("type", "userState");
        sendJson.addProperty("userId", userId);
        sendJson.addProperty("state", state);
        sendJson.addProperty("avatar", plushUser.profilePicture);
        sendJson.addProperty("name", plushUser.toString());
        sendJson.addProperty("position", plushUser.userPosition.name);
        chatEvents.publish(new OnlineStateEvent(sendJson));
        if(state == offline){
            plushUser.lastLogOutDate = new Date();
            plushUser._save();
        }
    }

    public void onlineStateFn(long userId,long uuid, int state) {
        if (state == online){
            if (!alreadyOnline(userId)) // oor nevtersen hereglegch baihgui uyd
                plushUserState(userId, online); // online notfi ilgeene
            onlineUsers.put(uuid, userId);
        }else{
            onlineUsers.remove(uuid);
            if (!alreadyOnline(userId)){ // oor nevtersen holbolt baihgui uyd
                //TODO 1 minute huleeged offline notfi ilgeene
                (new OfflineUserDo(userId)).in(30);
            }
        }

    }


    // app asah uyd duudagdana
    public void addAllUserOffline(){

    }

    // app untrah uyd duudagdana
    public void updateAllUserLogOutDate(){

    }


    public boolean alreadyOnline(long userId){
        for (Long currentUserId : onlineUsers.values())
            if (currentUserId == userId)
                return true;
        return false;

    }

    public List<User> getOnlineOfflineUsers(Long receiverId) {
        List<User> userOnlineOfflines = User.find("active=true AND id!=?1 ORDER BY lastLogOutDate DESC",receiverId).fetch();
        for (User user : userOnlineOfflines) {
            user.state = alreadyOnline(user.id);
            user.messageCount = Functions.getCountUnreadMessage(user.id,receiverId);
        }
        return userOnlineOfflines;
    }


    // ~~~~~~~~~ Chat room events
    public static abstract class Event {

        final public String type;
        final public Long timestamp;

        public Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }

    }

    public static class NotificationEvent extends Event {
        final public JsonObject json;

        public NotificationEvent(JsonObject obj) {
            super("notification");
            this.json = obj;
        }
    }

    public static class NotificationReminder extends Event {
        final public JsonObject json;

        public NotificationReminder(JsonObject obj) {
            super("remindermodelClass");
            this.json = obj;
        }

    }

    public static class ChatMessageEvents extends Event {
        final public JsonObject data;
        final public long remoteId;
        final public long senderId;

        public ChatMessageEvents(JsonObject obj, long remoteId, long senderId) {
            super("chatmessageeventsClass");
            this.data = obj;
            this.remoteId = remoteId;
            this.senderId = senderId;
        }
    }

    public static class OnlineStateEvent extends Event {
        final public JsonObject json;

        public OnlineStateEvent(JsonObject obj) {
            super("onlinestateevent");
            this.json = obj;
        }
    }

    // ~~~~~~~~~ Chat room factory
    static UserLiveRoom instance = null;

    public static UserLiveRoom get() {
        if (instance == null) {
            instance = new UserLiveRoom();
        }
        return instance;
    }

    public class UserOnline implements Comparable<UserOnline> {
        public int tabCount;
        public HashMap<Long, Integer> msgCount = new HashMap<Long, Integer>();
        public boolean pushed;
        public String avatar;
        public String name;
        public String position;
        public Long userId;
        public Date date;

        public UserOnline(Long userId, boolean pushed, int tabCount, Date date
                , String avatar, String name, String position) {
            this.userId = userId;
            this.date = date;
            this.pushed = pushed;
            this.tabCount = tabCount;
            this.avatar = avatar;
            this.name = name;
            this.position = position;
        }

        @Override
        public int compareTo(UserOnline o) {
            if (o.date == null)
                return this.date == null ? 0 : -1;
            else if (this.date == null)
                return 1;
            else
        /* For Ascending order*/
                return o.date.compareTo(this.date);
        }
    }
}


