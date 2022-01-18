package ru.mikescherbakov.crypt;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class CryptedData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date datetime;
    private String source_text;
    private String crypted_text;
    private int key;

    protected CryptedData() {

    }

    protected CryptedData(Date datetime, String source_text, String crypted_text, int key) {
        this.datetime = datetime;
        this.source_text = source_text;
        this.crypted_text = crypted_text;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDate_time(Date datetime) {
        this.datetime = datetime;
    }

    public String getSource_text() {
        return source_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getCrypted_text() {
        return crypted_text;
    }

    public void setCrypted_text(String crypted_text) {
        this.crypted_text = crypted_text;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
