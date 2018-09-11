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
@Entity(name = "tb_district")
public class District  extends Model{
    @Required
    public String name;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    public List<Khoroo> khoroos;

//    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
//    public List<Apartment> apartments;

}
