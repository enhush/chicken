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
@Entity(name = "tb_address")
public class Address extends Model{
    public String phoneNumber;

    @ManyToOne
    public District district;

    @ManyToOne
    public Khoroo khoroo;

    @ManyToOne
    public Street street;

    @ManyToOne
    public Apartment apartment;

    public String orts;

    public String floor;

    public String door;

    public String doorCode;

    public String detail;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    public List<Order> orders;


    public String toString() {
        String str = "";
        if(this.district != null) str = str + this.district.name + " дүүрэг, ";
        if(this.khoroo != null) str = str + this.khoroo.name + " хороо, ";
        if(this.street != null) str = str + this.street.name + " гудамж, ";
        if(this.apartment != null) str = str + this.apartment.name + " байр, ";
        str = str + this.orts + " орц, " + this.floor + " давхар, " + this.door + " тоот, "+ this.doorCode + " орцны код";
        if(this.detail != null) str = str + " " + this.detail;
        return str;
    }

}
