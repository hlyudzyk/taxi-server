package com.taxiapp.main.net.responses;


import com.taxiapp.main.persistance.entities.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDataResponse {
    private String subject;
    private String content;
    private String senderEmail;

    public MessageDataResponse(Message message){
        this.subject = message.getSubject();
        this.content = message.getContent();
        this.senderEmail = message.getSender().getEmail();
    }
}
