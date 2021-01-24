package com.project.library;

import java.io.Serializable;

public class Author implements Serializable {
    private String uid;
    private String name;
    private int numberOfPublishedBooks;
    private String bestSellingBookName;
    private String username;

    public Author() {
    }

    public Author(String name, int numberOfPublishedBooks, String bestSellingBookName, String username) {
        this.name = name;
        this.numberOfPublishedBooks = numberOfPublishedBooks;
        this.bestSellingBookName = bestSellingBookName;
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPublishedBooks() {
        return numberOfPublishedBooks;
    }

    public void setNumberOfPublishedBooks(int numberOfPublishedBooks) {
        this.numberOfPublishedBooks = numberOfPublishedBooks;
    }

    public String getBestSellingBookName() {
        return bestSellingBookName;
    }

    public void setBestSellingBookName(String bestSellingBookName) {
        this.bestSellingBookName = bestSellingBookName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Author{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", numberOfPublishedBooks=" + numberOfPublishedBooks +
                ", bestSellingBookName='" + bestSellingBookName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
