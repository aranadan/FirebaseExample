package com.fox.andrey.firebaseexample;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {
   private String userName = "John Doe";
           private String textMessage;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public Message() {
    }

    public Message(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
