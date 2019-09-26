//Hayden Vass
//Jav21905
//article
package com.example.vasshayden_ce07.objects;

import java.io.Serializable;

public class Article implements Serializable {

    private final String articleName;
    private final String author;
    private final String imgURL;
    private final String articleUrl;
    private final int upVotes;
    private final int score;

    public Article(String articleName, String author, String imgURL, String articleUrl, int upVotes, int score) {
        this.articleName = articleName;
        this.author = author;
        this.imgURL = imgURL;
        this.articleUrl = articleUrl;
        this.upVotes = upVotes;
        this.score = score;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getAuthor() {
        return author;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getArticleUrl() { return articleUrl; }

    public int getUpVotes() {
        return upVotes;
    }

    public int getScore() {
        return score;
    }
}
