package com.project.library;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.project.library.data.model.LoggedInUser;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "books", foreignKeys = @ForeignKey(entity = LoggedInUser.class, parentColumns = "username", childColumns = "username", onDelete = CASCADE), indices = @Index("username"))
public class Book implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String author;
    private Date publishDate;
    private float rating;
    private Genre bookGenre;
    private int noPages;
    private String coverImageUri;
    private boolean isRead;
    private String username;

    @Ignore
    public Book(String title, String author, Date publishDate, float rating, Genre bookGenre, int noPages, String coverImageUri, boolean isRead) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.rating = rating;
        this.bookGenre = bookGenre;
        this.noPages = noPages;
        this.coverImageUri = coverImageUri;
        this.isRead = isRead;
    }

    public Book(String title, String author, Date publishDate, float rating, Genre bookGenre, int noPages, String coverImageUri, boolean isRead, String username) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.rating = rating;
        this.bookGenre = bookGenre;
        this.noPages = noPages;
        this.coverImageUri = coverImageUri;
        this.isRead = isRead;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if(rating>0 && rating<=5) {
            this.rating = rating;
        }else{
            this.rating = rating>5 ? 5 : 0;
        }
    }

    public Genre getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(Genre gen) {
        this.bookGenre = gen;
    }

    public int getNoPages() {
        return noPages;
    }

    public void setNoPages(int noPages) {
        this.noPages = noPages;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public String getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(String coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", rating=" + rating +
                ", bookGenre=" + bookGenre +
                ", noPages=" + noPages +
                ", coverImageUri='" + coverImageUri + '\'' +
                ", isRead=" + isRead +
                ", username='" + username + '\'' +
                '}';
    }
}
