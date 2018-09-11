package models;

import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "tb_userteam")
public class UserTeam extends Model {

    @Required
    public String name;

    @Required
    public String nameMin;

    @Required
    public Long queue = 1L;

    @CRUD.Hidden
    @OneToMany(mappedBy = "userTeam")
    public List<User> users;

    public String toString() {
        return this.name;
    }

    public List<User> getUsers() {
        return User.find("userTeam.id=? and active=true ORDER BY userPosition.rate, firstName", this.id).fetch();
    }
}