package ru.mikescherbakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mikescherbakov.crypt.AlgorithmManager;
import ru.mikescherbakov.crypt.CaesarAlgorithmManager;

@Configuration
public class CryptedDataConfig {

    @Bean
    public AlgorithmManager caesarAlgorithManager() {
        return new CaesarAlgorithmManager();
    }
}
