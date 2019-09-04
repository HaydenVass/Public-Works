package com.example.vasshayden_ce07;

class Books {

    private  String title;
    private final String bookCover;

    public Books(String _title, String _bookCover) {
        title = _title;
        bookCover = _bookCover;
    }

    public String getTitle() { return title; }
    public String getBookCover() {return bookCover;}

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
