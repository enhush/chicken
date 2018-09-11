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
@Entity(name = "tb_car")
public class Car extends Model{
    @Required
    public String name;

    public String carNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    public List<CarSchedule> carSchedules;

}
