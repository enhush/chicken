package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by User on 18.09.01.
 */
@Entity(name = "tb_stock_file")
public class StockFile extends Model {
    @ManyToOne
    public Stock stock;

    public String path;
    public String name;
    public String extension;
}
