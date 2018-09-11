package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by Enkhbayar on 1/27/2016.
 */
@Entity(name = "tb_chat_message")
public class ChatMessage extends Model {

    @ManyToOne
    public User msgSender;

    @ManyToOne
    public User msgReceiver;

    public Date date;

    public boolean readed=false;

    @Column(length = 65535)
    public String content;

    @OneToOne
    public ChatMessageAttach attach;

    public ChatMessage(User msgSender, User msgReceiver, String content) {
        this.msgSender = msgSender;
        this.msgReceiver = msgReceiver;
        this.content = content;
        this.date = new Date();
    }
}