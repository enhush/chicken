package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by enkhamgalan on 2/25/15.
 */
@Entity(name = "tb_accountsetting")
public class AccountSetting extends Model {

    @Required
    @OneToOne
    public User user;

    public String menu;

    public String menuNext;
}
