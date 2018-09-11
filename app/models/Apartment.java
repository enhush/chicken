package models;

import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by unurbayar on 6/29/2018.
 */
@Entity(name = "tb_apartment")
public class Apartment extends Model {
    @Required
    public String name;

//    @ManyToOne
//    public District district;

    @ManyToOne
    public Khoroo khoroo;
}
