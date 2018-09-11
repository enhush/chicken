package models;

import controllers.CRUD;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by unurbayar on 6/29/2018.
 */
@Entity(name = "tb_order")
public class Order extends Model{
    @Required
    public String name;

    @Required
    public Date takeDate;

    public Date endDate;

    @ManyToOne
    public User takeUser;
    @ManyToOne
    public User chief;
    @ManyToOne
    public User driver;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderProductRelation> orderProductRelations;

    public String phoneNumber;

    @ManyToOne
    public Address address;

    public Long totalAmount;

    public Integer payType;

    public Integer paymentOther;

    public Integer status;

    public Integer customer;

    public Integer payment;

    public Integer other;

//    public boolean create() {
//        this.takeDate = new Date();
//        this.endDate = new Date();
//        return super.create();
//    }


}
