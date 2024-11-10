package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;




/**
 * You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

// The controller is the entry point for HTTP requests. 
// It'll map web reqeusts to specific handler methods where application receives data or returns a response.


// Combined COntroller and Response body for all handler methods in the class
// Any return value from the methods will automaticlaly be written to the HTTP resposne body
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    // POST /register
    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody Account acc) {
        // if username is blank or pass isn't at least 4 characters long
        if(acc.getUsername().isBlank() || acc.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("Client Error");
        } else if(accountService.findByUsername(acc.getUsername())) {
            // duplicate account check
            return ResponseEntity.status(409).body("Conflict");
        } 

        // Otherwise proceed and add the account as normal
        Account registeredAccount = accountService.addAccount(acc);
        return ResponseEntity.status(200).body(registeredAccount);
    }

    // POST /login
    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody Account acc) {
        // find the account with it's details
        Account loggedAccount = accountService.loginAccount(acc); 
        // if the details don't match to something in the database, it'll return 401
        if(loggedAccount != null) {
            return ResponseEntity.status(200).body(loggedAccount);        
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    // POST /messages
    @PostMapping("/messages")
    public ResponseEntity<?> postMessages(@RequestBody Message msg) {
        // Check if messageText is not blank,isn't over 255 characters, and postedBy refers to real user
        if(msg.getMessageText().isBlank() || msg.getMessageText().length() > 255 || messageService.checkUser(msg.getPostedBy()) == null) {
            return ResponseEntity.status(400).body("Client Error");
        }

        // otherwise proceed and create the new message
        Message createdMessage = messageService.createMessage(msg);
        return ResponseEntity.status(200).body(createdMessage);
    }

    // GET /messages
    @GetMapping("messages") 
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    //  GET /messages/{messageId}
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessage(messageId));
    }
    
    // DELETE /messages/{messageId}
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        if(messageService.getMessage(messageId) != null) {
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).body("");
        }

    }

    // Patch /messages/{messageId}
    @PatchMapping ("messages/{messageId}")
    public ResponseEntity<?> patchMessage(@PathVariable int messageId, @RequestBody Message msg) {
        // Check if messageId already exists, messageText is not blank and isn't over 255 characters
        if(messageService.getMessage(messageId) == null || msg.getMessageText().isBlank() || msg.getMessageText().length() > 255) {
            return ResponseEntity.status(400).body("Client Error");
        }

        messageService.updateMessage(messageId, msg);   
        return ResponseEntity.status(200).body(1);
    }

    // GET /accounts/{accountId}/message
    @GetMapping ("accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessagesUser (@PathVariable int accountId) {
        List<Message> allMessages = messageService.getAllMessagesUser(accountId);
        return ResponseEntity.status(200).body(allMessages);
    }
}
