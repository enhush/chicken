package models;

import controllers.CRUD;
import play.data.validation.MaxSize;
import play.data.validation.Required;

import javax.persistence.*;
import java.util.List;
import controllers.Consts;


/**
 * Created by enkhamgalan on 3/1/15.
 */
@Entity(name = "tb_fileshare")
public class FileShare extends BaseModel {

    @Required
    @CRUD.Hidden
    @Column(length = 10000)
    public String name;

    @Required
    public String dir;

    @Required
    public String extension;

    @Required
    public String fileSize;

    public Float fileSizeM;

    @Required
    public Long ftype;

    @Required
    @CRUD.Hidden
    @ManyToOne
    public User uploader;

    @Column(length = 10000)
    public String sendUserList;

    @Column(length = Consts.maxDescriptionLength)
    @MaxSize(Consts.maxDescriptionLength)
    public String description;

    public Long downloadCount = 0L;

    @CRUD.Hidden
    @OneToMany(mappedBy = "fileShare", cascade = CascadeType.ALL)
    public List<FileShareReceivedUser> receivedUsers;

    @ManyToOne
    public FolderStructure folderStructure;

    public String toString() {
        try {
            return name + "." + extension;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "none";
    }

    public boolean isSharedWithMe(Object user) {

        if (user instanceof User && sendUserList.contains(((User) user).id + ",")) {
            return true;
        } else if (user instanceof String && sendUserList.contains(user + ",")) {
            return true;
        }
        return false;
    }
}