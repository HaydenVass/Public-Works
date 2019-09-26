package com.example.bark_buddy.objects;

public class Message {

    private String senderId;
    private String recieverId;
    private String message;
    private String dateTime;

    //empty constructor for firestore


    //need this for firebase -- inspection will say its unused but thats because its not accessed
    //directly. Its a nested in the chat channel class and firestore needs the empty constructor to
    //pull from the DB
    public Message() {

    }

    public Message(String senderId, String recieverId, String message, String dateTime) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getSenderId() {
        return senderId;
    }


    public String getRecieverId() {
        return recieverId;
    }


    public String getMessage() {
        return message;
    }

    //says its never used but it is
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", recieverId='" + recieverId + '\'' +
                ", message='" + message + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
