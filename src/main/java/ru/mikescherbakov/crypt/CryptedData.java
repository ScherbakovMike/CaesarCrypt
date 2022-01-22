package ru.mikescherbakov.crypt;

import javax.persistence.*;

@Entity
public class CryptedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long transaction_time;
    private String source_text;
    private String crypted_text;
    private int crypt_key;
    private CryptResult crypt_result;

    protected CryptedData() {

    }

    protected CryptedData(Long transaction_time, String source_text, String crypted_text, int crypt_key) {
        this.transaction_time = transaction_time;
        this.source_text = source_text;
        this.crypted_text = crypted_text;
        this.crypt_key = crypt_key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(long transaction_time) {
        this.transaction_time = transaction_time;
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

    public int getCrypt_key() {
        return crypt_key;
    }

    public void setCrypt_key(int crypt_key) {
        this.crypt_key = crypt_key;
    }

    public void setTransaction_time(Long transaction_time) {
        this.transaction_time = transaction_time;
    }

    public CryptResult getCrypt_result() {
        return crypt_result;
    }

    public void setCrypt_result(CryptResult crypt_result) {
        this.crypt_result = crypt_result;
    }
}
