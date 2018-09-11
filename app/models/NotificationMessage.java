package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by enkhamgalan on 3/8/15.
 */
@Entity(name = "tb_notification_message")
public class NotificationMessage extends Model {

    @Required
    public Date date;

    @Required
    @ManyToOne
    public User acceptor;
//
//    @ManyToOne
//    public Meg meg;

    public String message;

    public int status; // 1-Ахлагч батлах ёстой, 2-Ахлагч буцаасан, 3-Ерөнхий батлах ёстой, 4-Ерөнхий буцаасан

    @Required
    @ManyToOne
    public User sender;

    public boolean seen = false;

    public String lateDate() {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long d = diff / (24 * 60 * 60 * 1000);
        if (d > 0) return (d + 1) + " өдрийн өмнө";
        else {
            d = diff / (60 * 60 * 1000) % 24;
            if (d > 0) return d + " цагийн өмнө";
            else {
                d = diff / (60 * 1000) % 60;
                if (d > 0) return d + " минутын өмнө";
                else {
                    d = diff / 1000 % 60;
                    if (d > 0) return d + " секундын өмнө";
                }
            }
        }
        return "Яг одоо";
    }
}
