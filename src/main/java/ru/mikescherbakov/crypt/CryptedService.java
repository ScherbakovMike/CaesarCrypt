package ru.mikescherbakov.crypt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CryptedService {
    @Autowired CryptedDataRepository repo;
    @Autowired CaesarAlgorithmManager cryptoManager;

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

    public void cryptAndSave(CryptedData cryptedData) {
        cryptoManager.crypt(cryptedData);
        repo.save(cryptedData);
    }
}

