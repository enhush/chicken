package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by enkhamgalan on 4/6/16.
 */
@Entity(name = "tb_permission_rel")
public class UserPermissionRelation extends Model{
    @ManyToOne
    public UserPermissionType permissionType;

    @ManyToOne
    public User user;
}
