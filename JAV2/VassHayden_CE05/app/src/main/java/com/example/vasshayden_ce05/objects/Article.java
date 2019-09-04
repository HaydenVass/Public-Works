//Hayden Vass
// Jav2 1905
//Article
package com.example.vasshayden_ce05.objects;

public class Article {

    private final String articleName;
    private final String body;
    private final String imgURL;

    public Article(String articleName, String body, String imgURL) {
        this.articleName = articleName;
        this.body = body;
        this.imgURL = imgURL;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getBody() {
        return body;
    }

    public String getImgURL() {
        return imgURL;
    }
}
