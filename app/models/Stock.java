package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 18.09.01.
 */
@Entity(name = "tb_stock")
public class Stock extends Model{
    @Required
    public String name;
    public Integer count;
    public Long unitPrice;
    public Long allPrice;
    public String note;
    public int state;
    public Date startDate;
    @ManyToOne
    public UserTeam respondentTeam;
    @ManyToOne
    public User respondent;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    public List<StockFile> stockFiles;

}
