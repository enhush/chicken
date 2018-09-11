package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.*;
import play.Play;
import play.mvc.With;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by enkhamgalan on 2/25/15.
 */
@With(Secure.class)
@Check(Consts.permissionFileShare)
public class FolderShare extends CRUD {
    public static void center() {
        User us = Users.getUser();
        boolean admin = (getPermission(us) == 3);
        render(admin);
    }

    public static void getTree(Long id) {
        User us = Users.getUser();
        boolean admin = (getPermission(us) == 3);
        List<FolderStructure> folderStructures = FolderStructure.find("(permission=NULL OR LOCATE('," + us.id + ",',permission)!=0) AND folderStructure.id=" + id + " order by queue, name").fetch();
        JsonArray array = new JsonArray();
        for (FolderStructure structure : folderStructures) {
            array.add(getTreeJson(structure, admin));
        }
        renderJSON(array);
    }

    public static JsonObject getTreeJson(FolderStructure structure, boolean admin) {
        JsonObject treeJson = new JsonObject();
        treeJson.addProperty("id", structure.id);
        if (structure.name.length() > 30) {
            treeJson.addProperty("text", structure.name.substring(0, 30) + "...");
            treeJson.addProperty("tooltip", structure.name);
        } else {
            treeJson.addProperty("text", structure.name);
            treeJson.addProperty("tooltip", "");
        }
        if (structure.folderStructure != null) treeJson.addProperty("parent", structure.folderStructure.id);
        else treeJson.addProperty("parent", "#");
        if (structure.permission != null && admin)
            treeJson.addProperty("icon", "fa fa-lock " + structure.folderColor.color);
        else treeJson.addProperty("icon", "fa " + structure.folderType.val + " " + structure.folderColor.color);
        if (structure.folderStructures.size() > 0) treeJson.addProperty("children", true);
        else treeJson.addProperty("children", false);
        treeJson.addProperty("data", Functions.fileShareExtensionTypes[structure.extension]);
        return treeJson;
    }

    public static int getPermission(User user) {
        return user.getUserPermissionType(Consts.permissionFileShare);
    }

    public static void fileAlreadyNamed(Long fid, String fileName) {
        FileShare file = FileShare.find("name=?1 and folderStructure.id=?2", fileName, fid).first();
        boolean exists = file != null;
//        String response = exists + "," + (exists ? file.uploader + "," + Consts.myDateFormat.format(file.uploadedAt) : ",");
        String response = exists + ",";
        renderText(response);
    }

    public static void saveFiles(Long fid, String attaches, Long ftype, Long[] uid, String description) {
        JsonArray attachesJson = (JsonArray) new JsonParser().parse(attaches);
        if (fid != null && attachesJson != null && attachesJson.size() > 0) {
            User user = Users.getUser();
            FolderStructure folderStructure = FolderStructure.findById(fid);
            String des1 = "";
            for (int c = 0; c < description.length(); c++) {
                if ((int) description.charAt(c) == 10) des1 += " ";
                else des1 += description.charAt(c);
            }
            description = des1;
            for (JsonElement jsonElement : attachesJson) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                FileShare file;
                Long fileSaveId = jsonObject.get("fileSaveId").getAsLong();
                if (fileSaveId.intValue() == 0) file = new FileShare();
                else file = FileShare.findById(fileSaveId);
                if (file != null) {
                    file.folderStructure = folderStructure;
                    file.ftype = ftype;
                    file.dir = jsonObject.get("dir").getAsString();
                    file.name = jsonObject.get("filename").getAsString();
                    file.extension = jsonObject.get("extension").getAsString();
                    file.fileSize = jsonObject.get("filesize").getAsString();
                    file.fileSizeM = Float.parseFloat(file.fileSize);
                    file.description = description;
                    file.sendUserList = "";
                    if (ftype.intValue() == 2 && uid != null && uid.length > 0) {
                        file.sendUserList = ",";
                        for (int u = 0; u < uid.length; u++) {
                            file.sendUserList += uid[u] + ",";
                        }
                    }
                    if (fileSaveId.intValue() == 0) {
                        file.uploader = user;
                        file.create();
                    } else file.save();
                }
            }
        }
    }

    public static void delete(Long id) {
        FileShare fileShare = FileShare.findById(id);
        try {
            File file = new File(Play.applicationPath.getAbsolutePath() + fileShare.dir + "." + fileShare.extension);
            if (file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileShare._delete();
    }

    public static void editShow(Long id) {
        FileShare fileShare = FileShare.findById(id);
        List<User> users = new ArrayList<User>();
        if (fileShare.sendUserList.length() > 1) {
            String uid[] = fileShare.sendUserList.split(",");
            for (int u = 0; u < uid.length; u++) {
                if (uid[u].length() > 0) {
                    User user = User.findById(Long.parseLong(uid[u]));
                    users.add(user);
                }
            }
        }
        render(fileShare, users);
    }

    public static void loadFiles(Long fid, int ftype, int CurrentPageNumber, boolean attach) {
        User us = Users.getUser();
        String qr = "";
        if (ftype > 0) {
            String[] ext = Functions.fileShareExtensionTypes[ftype].split(",");
            qr = " AND (";
            for (int i = 0; i < ext.length; i++) {
                qr += "LOWER(extension) = '" + ext[i] + "'";
                if (i < ext.length - 1) qr += " OR ";
            }
            qr += ")";
        }
        Long maxSize = FileShare.count("folderStructure.id=?1" + qr + " AND (ftype=?2 OR uploader.id=?3 OR LOCATE('," + us.id + ",',sendUserList)!=0)) order by createdDate desc", fid, 0L, us.id);
        List<FileShare> fileShares = FileShare.find("folderStructure.id=?1" + qr + " AND (ftype=?2 OR uploader.id=?3 OR LOCATE('," + us.id + ",',sendUserList)!=0)) order by createdDate desc", fid, 0L, us.id).fetch(CurrentPageNumber, 18);
        int loadType = 1;
        Long MaxPageNumber = maxSize / 18;
        if (maxSize % 18 != 0) MaxPageNumber++;
        if (!attach) render(fileShares, CurrentPageNumber, MaxPageNumber, loadType);
        else
            render("/FolderShare/AttachloadFiles.html", fileShares, CurrentPageNumber, MaxPageNumber, loadType);
    }

    public static void searchFiles(String search, int CurrentPageNumber) {
        if (search.length() > 1) {
            int loadType = 0;
            User user = Users.getUser();
            String qr = "LOWER(name) LIKE '%" + search.toLowerCase() + "%' OR LOWER(description) LIKE '%" + search.toLowerCase() + "%'";
            qr += " AND (ftype=0 OR uploader.id=" + user.id + " OR LOCATE('" + user.id + ",',sendUserList)!=0)";
            Long maxSize = FileShare.count(qr);
            List<FileShare> fileShares = FileShare.find(qr).fetch(CurrentPageNumber, 20);
            Long MaxPageNumber = maxSize / 18;
            if (maxSize % 18 != 0) MaxPageNumber++;
            render("FolderShare/loadFiles.html", fileShares, loadType, search, CurrentPageNumber, MaxPageNumber);
        }
    }

    public static void showMoreInfo(Long id, int ftype) {
        FileShare fileShare = FileShare.findById(id);
        if (ftype == 1) {
            List<User> users = new ArrayList<User>();
            if (fileShare.sendUserList.length() > 1) {
                String uid[] = fileShare.sendUserList.split(",");
                for (int u = 0; u < uid.length; u++) {
                    if (uid[u].length() > 0) {
                        User user = User.findById(Long.parseLong(uid[u]));
                        users.add(user);
                    }
                }
            }
            render(fileShare, users, ftype);
        } else {
            List<FileShareReceivedUser> receivedUsers = FileShareReceivedUser.find("fileShare.id=?1 order by date desc", fileShare.id).fetch();
            render(receivedUsers);
        }
    }

    public static void downloadHistorySave(Long id) {
        FileShare fileShare = FileShare.findById(id);
        fileShare.downloadCount++;
        fileShare._save();
        FileShareReceivedUser receivedUser = new FileShareReceivedUser();
        receivedUser.date = new Date();
        receivedUser.fileShare = fileShare;
        receivedUser.user = Users.getUser();
        receivedUser.create();
    }

    public static void folderRename(String id, String parent, String name) {
        User user = Users.getUser();
        if (getPermission(user) != 3) forbidden();
        FolderStructure folderStructure;
        String ext;
        if (id.indexOf('j') == 0) {
            folderStructure = new FolderStructure();
            folderStructure.queue = 1L;
            folderStructure.folderType = FolderType.findById(1L);
            folderStructure.folderColor = FolderColor.findById(5L);
            folderStructure.name = name;
            if (!parent.equals("#")) {
                folderStructure.folderStructure = FolderStructure.findById(Long.parseLong(parent));
            }
            ext = Functions.fileShareExtensionTypes[0];
            folderStructure.create();
        } else {
            folderStructure = FolderStructure.findById(Long.parseLong(id));
            folderStructure.name = name;
            ext = Functions.fileShareExtensionTypes[folderStructure.extension];
            folderStructure._save();
        }
        folderStructure.refresh();
        renderText(folderStructure.id.toString() + "_" + ext);
    }

    public static void folderDelete(Long id) {
        User user = Users.getUser();
        if (getPermission(user) != 3) forbidden();
        FolderStructure folderStructure = FolderStructure.findById(id);
        if (folderStructure != null && !folderStructure.systemFolder) {
            List<FileShare> fileShares = FileShare.find("folderStructure.id=?1", id).fetch();
            for (FileShare fileShare : fileShares) {
                Functions.deleteFileSingle(fileShare.dir + "." + fileShare.extension);
                fileShare._delete();
            }
            List<FolderStructure> folderStructures = folderStructure.getFolderStructures();
            for (FolderStructure folderStructure1 : folderStructures) folderStructure1._delete();
            folderStructure._delete();
        }
    }

    public static void folderSettings(Long id) {
        User user = Users.getUser();
        if (getPermission(user) != 3) forbidden();
        FolderStructure folderStructure = FolderStructure.findById(id);
        List<FolderType> folderTypes = FolderType.findAll();
        List<FolderColor> folderColors = FolderColor.findAll();
        render(folderColors, folderTypes, folderStructure);
    }

    public static void folderSettingsSave(Long id, Long cid, Long tid, int eid, Long queue) {
        FolderStructure folderStructure = FolderStructure.findById(id);
        folderStructure.folderColor = FolderColor.findById(cid);
        folderStructure.folderType = FolderType.findById(tid);
        folderStructure.extension = eid;
        if (queue != null) folderStructure.queue = queue;
        folderStructure._save();
        if (folderStructure.permission != null) renderText("fa-lock " + folderStructure.folderColor.color);
        else renderText(folderStructure.folderType.val + " " + folderStructure.folderColor.color);
    }

    public static void folderPermission(Long id) {
        User owner = Users.getUser();
        if (getPermission(owner) != 3) forbidden();
        FolderStructure folderStructure = FolderStructure.findById(id);
        List<User> list = new ArrayList<User>();
        List<User> users = new ArrayList<User>();
        if (folderStructure.permission != null) {
            if (folderStructure.permission.length() > 0) {
                String[] permission = folderStructure.permission.split(",");
                for (int i = 0; i < permission.length; i++) {
                    if (permission[i].length() > 0) {
                        Long uid = Long.parseLong(permission[i]);
                        if (uid.compareTo(Users.getUser().id) != 0) {
                            User user = User.findById(uid);
                            list.add(user);
                        }
                    }
                }
                List<User> uAll = FunctionController.getUsers();
                for (User us : uAll) {
                    if (!containUser(list, us)) users.add(us);
                }
            } else {
                users = FunctionController.getUsers();
            }
        } else list = FunctionController.getUsers();
        render(users, list, folderStructure);
    }

    public static boolean containUser(List<User> users, User user) {
        for (User us : users) {
            if (us.id.compareTo(user.id) == 0) return true;
        }
        return false;
    }

    public static void folderPermissionSave(Long id, int unseens, String users) {
        User owner = Users.getUser();
        if (getPermission(owner) != 3) forbidden();
        FolderStructure folderStructure = FolderStructure.findById(id);
        if (unseens == 0) folderStructure.permission = null;
        else folderStructure.permission = users;
        folderStructure._save();
        if (folderStructure.permission != null) renderText("fa-lock " + folderStructure.folderColor.color);
        else renderText(folderStructure.folderType.val + " " + folderStructure.folderColor.color);
    }

    public static void getPermissionedUsers(Long id) {
        FolderStructure folderStructure = FolderStructure.findById(id);
        List<User> users = new ArrayList<User>();
        if (folderStructure.permission != null && folderStructure.permission.length() > 1) {
            String uid[] = folderStructure.permission.split(",");
            for (int u = 0; u < uid.length; u++) {
                if (uid[u].length() > 0) {
                    User user = User.findById(Long.parseLong(uid[u]));
                    users.add(user);
                }
            }
        }
        render(users);
    }

    public static void getTreeAttach(String id) {
        User us = Users.getUser();
        JsonArray array = new JsonArray();
        if (id.equals("#")) {
            id = "NULL";
        }
        List<FolderStructure> folderStructures = FolderStructure.find(
                "(permission=NULL OR LOCATE('," + us.id + ",',permission)!=0) AND folderStructure.id=" + id + " order by queue, name").fetch();
        for (FolderStructure structure : folderStructures) {
            array.add(getTreeJson(structure, false));
        }
        renderJSON(array);
    }
}
