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
@Entity(name = "tb_car_schedule")
public class CarSchedule extends Model{
    @Required
    public String driverName;

    public Date date;

    public String receiveKm;

    public String wentKm;

    public String giveKm;

    public String fuelInfo;
    public String fineInfo;

    @ManyToOne
    public Car car;

}
