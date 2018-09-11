package models;

import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "tb_user")
public class User extends Model {

    @Required
    public String firstName;

    @Required
    public String lastName;

    @CRUD.Hidden
    public int lastNameLength;

//    public Date birthday;

    public Date employmentDate;

    public String username;

    public String password;

    public String code;

    public String registerNumber;

//    public String webFireBasetoken; //firebase token
//
//
//    @OneToOne
//    public Doctor doctor;
//
//    @OneToOne
//    public Police police;
//
//    @OneToOne
//    public Farmer farmer;


//    public int userType_id;

    // чатанд хэрэглэж байгаа онлин эсвэл аффлиан байгаа эсэх
    @Transient
    public boolean state;

    // чатанд хэрэглэж байгаа Уншаагүй мессажийн тоо
    @Transient
    public long messageCount;

    public String address;
    public String address1;
    public String address2;
    public String address3;
    public String address4;

    @CRUD.Hidden
    public Date lastLogOutDate = new Date();

    @Required
    @ManyToOne
    public UserPosition userPosition;

//    @Required
    @ManyToOne
    public UserTeam userTeam;

    @CRUD.Hidden
    @Required
    public String profilePicture;

    @Required
    public Boolean isMen = true;

    @CRUD.Hidden
    public boolean active = true;

    @CRUD.Hidden
    public Date lastAttempt;

    @CRUD.Hidden
    public int attempt;

    @CRUD.Hidden
    public Date createdDate;

    @Required
    public String phone1;

    public String phone2;

    @Required
    public String email;

    public int x;
    public int y;
    public int w;
    public int h;

    public String webFireBasetoken;

    @CRUD.Hidden
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<UserPermissionRelation> permissionRelations;

    @CRUD.Hidden
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    public AccountSetting accountSetting;

//    @CRUD.Hidden
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    public List<Rfid> rfids;
//
//    @CRUD.Hidden
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    public List<RfidLog> rfidLogs;
//
//    @CRUD.Hidden
//    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
//    public List<Meg> megs;
//
//    @CRUD.Hidden
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    public List<MegCheckpoint> megCheckpoints;
//
//    @CRUD.Hidden
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    public List<Plan> plans;
//
//    @CRUD.Hidden
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    public List<PlanAnimal> planAnimals;

    public String toString() {
        String la = getLastnameFirstCharacter();
        return la.length() > 0 ? la + "." + this.firstName : this.firstName;
    }

    public int getPermissionType(String alias) { // Үндсэн хандах эрхүүд
        UserPermissionRelation relation = UserPermissionRelation.find("user.id=?1 AND permissionType.permission.alias=?2", this.id, alias).first();
        if (relation != null) return relation.permissionType.value;
        return 0;
    }

    public int getPermission(Long permissionId) {
        for (UserPermissionRelation rel : this.permissionRelations) {
            if (rel.permissionType.permission.id.compareTo(permissionId) == 0) {
                return rel.permissionType.value;
            }
        }
        return 0;
    }

    public int permissionRelationSize(boolean admin) {
        return UserPermissionRelation.find("user.id=?1 AND permissionType.permission.timeAtt=?2", this.id, admin).fetch().size();
    }

    private String getLastnameFirstCharacter() {
        System.out.println(this.lastName);
        System.out.println(this.lastName.length()+" : "+ this.lastNameLength);
        if (this.lastName != null && this.lastName.length() > 0) return this.lastName.substring(0, this.lastNameLength);
        return "";
    }

    public int getUserPermissionType(String alias) { // Үндсэн хандах эрхүүд
        UserPermissionRelation relation = UserPermissionRelation.find("user.id=?1 AND permissionType.permission.alias=?2", this.id, alias).first();
        if (relation != null) return relation.permissionType.value;
        return 0;
    }

//    public int getUserType() {
//        return this.userType_id;
//    }
}