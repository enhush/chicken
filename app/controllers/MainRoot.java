package controllers;

import models.User;
import play.mvc.With;

/**
 * Created by enkhamgalan on 5/16/15.
 */
@With(Secure.class)
public class MainRoot extends CRUD {
    public static void root() {
        User user = Users.getUser();
        if (user.getPermissionType(Consts.permissionDashboard) > 0) Dashboard.index(null);
        Users.list(1, null, null, null, null, null, null, null, null, null, null, null);
    }
}
