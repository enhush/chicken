package controllers.BackgroundModes;

import controllers.UserLiveRoom;
import play.jobs.Job;

/**
 * Created by enkhamgalan on 11/4/16.
 */
public class OfflineUserDo extends Job{

    long userId;

    public OfflineUserDo(long userId) {
        this.userId = userId;
    }

    @Override
    public void doJob() {
        UserLiveRoom room = UserLiveRoom.get();
        if(!room.alreadyOnline(userId)){ // nevtersen holbolt
            room.plushUserState(userId, UserLiveRoom.offline);
            System.out.println("OfflineUserDo  plushUserState");
        }
    }

}
