package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired 
    private MessageRepository messageRepository;
    
    public Message checkUser(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }

    public Message createMessage(Message msg) {
        return messageRepository.save(msg);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(int messageId) {
        return messageRepository.findById(messageId);
    }

    public void deleteMessage(int messageId) {
        messageRepository.delete(messageRepository.findById(messageId));
    }

    // Unit of work approach
    // Managed objects are identified by their primary keys, so if we make an object
    // with predfined primary key, it'll be assoated with the database record of the same it
    // and state of the object will be propgated to the record automatically
    public void updateMessage(int messageId, Message msg) {
        // find the message with the message id
        Message updatedMessage = messageRepository.findById(messageId);
        updatedMessage.setMessageText(msg.getMessageText());
        messageRepository.save(updatedMessage);
    }


    public List<Message> getAllMessagesUser(int accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
