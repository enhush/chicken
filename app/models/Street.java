package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by unurbayar on 6/29/2018.
 */
@Entity(name = "tb_street")
public class Street extends Model{
    @Required
    public String name;
}
