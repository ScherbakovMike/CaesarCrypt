package ru.mikescherbakov.crypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class CryptedService {
    Logger logger = LoggerFactory.getLogger(CryptedService.class);

    @Autowired
    CryptedDataRepository repo;
    @Autowired
    CaesarAlgorithmManager cryptoManager;

    public void save(CryptedData cryptedData) {
        repo.save(cryptedData);
    }

    public List<CryptedData> listAll() {
        return (List<CryptedData>) repo.findAll();
    }

    public CryptedData get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void cryptAndSave(CryptedData cryptedData, MultipartFile file) {
        cryptoManager.crypt(cryptedData, file);
        repo.save(cryptedData);
    }

    public String getDecryptedFile(long id, DecryptMethod method) {
        return cryptoManager.decrypt(repo.findById(id).get(), method);
    }

    public String getFileFor(long id) {
        return repo.findById(id).get().getCrypted_text();
    }
}

