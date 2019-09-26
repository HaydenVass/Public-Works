package com.example.bark_buddy.objects;

import java.util.ArrayList;

public class ChatChannel {

    private ArrayList<Message> messages;

    public ChatChannel(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ChatChannel() {
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }

}
