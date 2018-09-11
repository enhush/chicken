package controllers;

import models.User;
import models.UserTeam;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class FunctionController extends Controller {

    private static String controllerName = "FunctionController";

    public static List<User> getUsers() {
        return User.find("active=true ORDER BY userTeam.queue, userTeam.name, userPosition.rate, firstName").fetch();
    }

    public static List<User> getTeamUsers(Long id) {
        return User.find("active=true AND userTeam.id=?1 ORDER BY userPosition.rate, firstName", id).fetch();
    }

    public static List<UserTeam> getTeams() {
        return UserTeam.find("ORDER BY queue, name").fetch();
    }


    public static void downloadFile(String fileDir, String fileName, String extension) throws IOException, GeneralSecurityException {
        String downloadUrl = fileDir + "." + extension;
        File file = new File(Play.applicationPath.getAbsoluteFile() + downloadUrl);
        Http.Response.current().contentType = "application/octet-stream";
        try {
            String des = fileName + "." + extension;
            renderBinary(new FileInputStream(file), des, "application/octet-stream", false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            notFound();
        }
    }
}