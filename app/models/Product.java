package models;

import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by unurbayar on 6/26/2018.
 */
@Entity(name = "tb_product")
public class Product extends Model{
    @Required
    public int type;

    @Required
    public String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<ProductIngredientRelation> productIngredientRelations;

    public String size;

    public Integer price;

    public Integer priceEmployee;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<OrderProductRelation> orderProductRelations;

}
