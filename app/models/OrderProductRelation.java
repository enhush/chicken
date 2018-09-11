package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by unurbayar on 6/26/2018.
 */
@Entity(name = "tb_order_product_rel")
public class OrderProductRelation extends Model {
    @ManyToOne
    public Product product;

    @ManyToOne
    public Order order;

    public Integer count;

    public Long price;

}
