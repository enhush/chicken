package models;

import controllers.CRUD;
import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseModel extends Model {

    @CRUD.Hidden
    public Boolean deleteFlag = false;

    @CRUD.Hidden
    public Date createdDate;

    @CRUD.Hidden
    public Date updatedDate;

    public Model merge() {
        this.updatedDate = new Date();
        if (this.createdDate == null) this.createdDate = this.updatedDate;
        return super.merge();
    }

    public Model save() {
        this.updatedDate = new Date();
        return super.save();
    }

    public boolean create() {
        this.updatedDate = this.createdDate = new Date();
        this.deleteFlag = false;
        return super.create();
    }

    public Model delete() {
        this.deleteFlag = true;
        return super.save();
    }
}
