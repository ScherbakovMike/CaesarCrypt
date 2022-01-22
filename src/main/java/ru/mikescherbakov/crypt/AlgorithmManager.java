package ru.mikescherbakov.crypt;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface AlgorithmManager {
    void crypt(CryptedData cryptedData, MultipartFile file);
    String decrypt(CryptedData cryptedData, DecryptMethod method);
}
