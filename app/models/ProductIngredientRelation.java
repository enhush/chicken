package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by unurbayar on 6/26/2018.
 */
@Entity(name = "tb_product_ingredient_rel")
public class ProductIngredientRelation extends Model {
    @ManyToOne
    public Product product;

    @ManyToOne
    public Ingredient ingredient;

    public Integer portion;

}
