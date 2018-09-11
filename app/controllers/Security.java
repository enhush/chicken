package controllers;

import models.User;
import models.UserPermissionRelation;

import java.util.Date;

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
        username = username.toUpperCase();
        User user = User.find("active=true and username=?1", username).first();
        if (user != null) {
            if (!user.username.equals(username)) return false;
            boolean pass = user.password.equals(Functions.getSha1String(password));
            if (pass && (user.attempt <= 6 || user.lastAttempt == null || Functions.durationMinute(user.lastAttempt, new Date()) > 30)) {
                session.put("passLength", password.length());
                return true;
            } else {
                if (user.lastAttempt == null || Functions.durationMinute(user.lastAttempt, new Date()) > 30)
                    user.attempt = 0;
                else user.attempt++;
                user.lastAttempt = new Date();
                session.put("attemptError", user.attempt);
                user._save();
            }
        }
        return false;
    }

    static boolean check(String type) {
        System.out.println("Check: " + connected() + " = " + type + " = " + Consts.myDateFormat2.format(new Date()));
        UserPermissionRelation permissionRelation = UserPermissionRelation.find("user.username=?1 AND permissionType.permission.alias=?2", connected(), type).first();
        if (type.equals("ToolReg"))
            return (permissionRelation != null && permissionRelation.permissionType.value > 2);
        return (permissionRelation != null && permissionRelation.permissionType.value > 0);
    }
}
