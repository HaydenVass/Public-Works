//Hayden Vass
//JAV2 - 1905
//Article
package com.example.vasshaydewn_ce01.Objects;

import java.io.Serializable;

public class Article implements Serializable {

    private final String articleName;
    private final String articleAuthor;
    private final String numPost;

    public Article(String articleName, String articleAuthor, String numPost) {
        this.articleName = articleName;
        this.articleAuthor = articleAuthor;
        this.numPost = numPost;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getNumPost() {
        return numPost;
    }

    @Override
    public String toString() {
        return articleName +  " " + articleAuthor + " " + numPost;
    }
}
