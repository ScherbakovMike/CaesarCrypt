package ru.mikescherbakov.crypt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CryptedDataRepository extends CrudRepository<CryptedData, Long> {

}
