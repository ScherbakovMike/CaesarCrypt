package ru.mikescherbakov.crypt;

import org.springframework.data.repository.CrudRepository;

public interface CryptedDataRepository extends CrudRepository<CryptedData, Long> {

}
