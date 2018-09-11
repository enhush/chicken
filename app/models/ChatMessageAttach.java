package models;

import play.data.validation.Required;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by Enkhbayar on 4/13/2016.
 */
@Entity(name = "tb_chat_message_attach")
public class ChatMessageAttach extends ModelAttach {

    @Required
    public String name;

    @Required
    public String dir;

    @Required
    public String extension;

    @OneToOne
    public ChatMessage chatMessage;

    public ChatMessageAttach(String name, String dir, String extension, Float filesize, ChatMessage chatMessage) {
        this.name = name;
        this.dir = dir;
        this.filesize = filesize;
        this.extension = extension;
        this.chatMessage = chatMessage;
        this.createdDate = new Date();
    }
}
