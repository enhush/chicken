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
@Entity(name = "tb_ingredient")
public class Ingredient extends Model{
    @Required
    public String name;

    public int remain;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    public List<ProductIngredientRelation> productIngredientRelations;

}
