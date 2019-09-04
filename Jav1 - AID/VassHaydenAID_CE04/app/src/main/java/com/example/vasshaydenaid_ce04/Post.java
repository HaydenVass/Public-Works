package com.example.vasshaydenaid_ce04;

public class Post {

    String user;
    String date;
    int postTitle;
    String postContent;
    int userPicture;
    int thumbsupImg;
    int moreImg;
    int ratingImg;

    public Post(String _user, String _date, int _postTitle, String _postContent, int _usrPpicture,
                int _moreImg, int _rating, int _thumbs){
        user = _user;
        date = _date;
        postTitle = _postTitle;
        postContent = _postContent;
        userPicture = _usrPpicture;
        moreImg = _moreImg;
        ratingImg = _rating;
        thumbsupImg = _thumbs;

    }
}
