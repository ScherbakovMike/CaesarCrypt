package ru.mikescherbakov.crypt;

import org.springframework.stereotype.Component;

@Component
public interface AlgorithmManager {
    void crypt(CryptedData cryptedData);
}
