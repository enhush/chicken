package models;

import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "tb_userposition")
public class UserPosition extends Model {

    @Required
    public String name;

    @Required
    public Long rate = 1L;

    @CRUD.Hidden
    @OneToMany(mappedBy = "userPosition", cascade = CascadeType.ALL)
    public List<User> users;

    public String toString() {
        return this.name;
    }

    public String getShortName() {

        if (this.name.length() < 10) return this.name;
        else return this.name.substring(0, 10) + " ...";
    }
}
