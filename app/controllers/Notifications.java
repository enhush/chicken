package controllers;

import models.*;

import java.util.List;

/**
 * Created by enkhamgalan on 3/8/15.
 */
public class Notifications extends CRUD {
    public static int getNotifications(Long uid) {
        return Notification.find("acceptor.id=?1 and seen=?2", uid, false).fetch().size();
    }

    public static int getNotificationMessages(Long uid) {
        return NotificationMessage.find("acceptor.id=?1 and seen=?2", uid, false).fetch().size();
    }

    public static void notificationsHeader(Long uid, int type) {
        User user = Users.getUser();
        if (user.id.compareTo(uid) != 0) forbidden();
        List<Notification> notifications = getNotificationDatas(uid, type, 1);
        render(notifications, type);
    }

    public static List<Notification> getNotificationDatas(Long uid, int type, int page) {
        if (type == 0) return Notification.find("acceptor.id=?1 order by date desc", uid).fetch();
        else return NotificationMessage.find("acceptor.id=?1 order by seen asc, date desc", uid).fetch();
    }


    public static void showAllNotifications(int type) {
        render(type);
    }

    public static void getAllNotifications(int type, int CurrentPageNumber) {
        User user = Users.getUser();
        List<Notification> notifications = getNotificationDatas(user.id, type, CurrentPageNumber);
        Long maxSize;
        if (type == 0) maxSize = Notification.count("acceptor.id=?1", user.id);
        else maxSize = NotificationMessage.count("acceptor.id=?1", user.id);
        Long MaxPageNumber = maxSize / 10;
        if (maxSize % 10 != 0) MaxPageNumber++;

        render(notifications, type, CurrentPageNumber, MaxPageNumber);
    }

    public static void notificationSee(Long id) {
        Notification notification = Notification.findById(id);
        notification.seen = true;
        notification._save();
        if (notification.post != null) {
            Post post = notification.post;
            post.fromNotification = true;
            post._save();
        } else Dashboard.index();
    }

//    public static void notificationMessageSee(Long id) {
//        NotificationMessage notification = NotificationMessage.findById(id);
//        notification.seen = true;
//        notification._save();
//        if (notification.meg != null)
//            Megs.view(notification.meg.id+"");
//    }

    public static void conversation(Long remoteId) {
        User remoteUser = User.findById(remoteId);
        List<ChatMessage> chatMessages = ChatMessage.find("(msgSender.id=?1 AND msgReceiver.id=?2) OR (msgSender.id=?2 AND msgReceiver.id=?1) ORDER BY date desc", Users.getUser().id, remoteId).fetch(10);
        render(remoteUser, chatMessages);
    }

    public static void loadMoreMessages(Long remoteId, int currentIndex) {
        List<ChatMessage> chatMessages = ChatMessage.find("(msgSender.id=?1 AND msgReceiver.id=?2) OR (msgSender.id=?2 AND msgReceiver.id=?1) ORDER BY date desc", Users.getUser().id, remoteId).fetch(currentIndex, 10);
        render(chatMessages);
    }

    public static void webFirebaseToken(String token) {
        User user = Users.getUser();
        user.webFireBasetoken = token;
        user._save();
    }

    public static void saveMessageHistory(Long remoteId, String msg, String fileDir, String fileName, String filesize, String extension) {
        User remoteUser = User.findById(remoteId);
        ChatMessage chatMessage = new ChatMessage(Users.getUser(), remoteUser, msg);
        chatMessage.create();
        if (fileDir != null && !fileDir.isEmpty()) {
            ChatMessageAttach chatMessageAttach = new ChatMessageAttach(fileName, fileDir, extension, Float.parseFloat(filesize), chatMessage);
            chatMessageAttach.create();
            chatMessage.attach = chatMessageAttach;
            chatMessage._save();
        }
    }

    public static void reedMessage(Long remoteId) {
        List<ChatMessage> chatMessages = ChatMessage.find("msgSender.id=?1 AND msgReceiver.id=?2 AND readed=false", remoteId, Users.getUser().id).fetch();
        for (ChatMessage rel : chatMessages) {
            rel.readed = true;
            rel.save();
        }
    }
}
