package ru.mikescherbakov.crypt;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class CaesarAlgorithmManager implements AlgorithmManager {

    private String getAlphabet() {
        String alphabet = "";
        for (int i = 32; i <= 126; i++) {
            alphabet += (String.valueOf((char) i));
        }
        // знаки препинания и английский алфавит
        for (int i = 32; i <= 126; i++) {
            alphabet += (String.valueOf((char) i));
        }
        //ё
        alphabet += (String.valueOf((char) 168));
        //Ё
        alphabet += (String.valueOf((char) 184));
        //№
        alphabet += (String.valueOf((char) 185));
        // знаки препинания и английский алфавит
        for (int i = 192; i <= 255; i++) {
            alphabet += (String.valueOf((char) i));
        }
        return alphabet;
    }

    @Override
    public void crypt(CryptedData cryptedData) {
        String result = "";
        String source_text = cryptedData.getSource_text();
        source_text = StringEscapeUtils.unescapeHtml3(source_text);
        int key = cryptedData.getCrypt_key();

        if (Objects.nonNull(source_text)
                && key != 0) {
            String alphabet = getAlphabet();

            for (int i = 0; i < source_text.length(); i++) {
                int currentPosition = alphabet.indexOf(source_text.substring(i, i + 1));
                int newPosition = (currentPosition + key) % alphabet.length();
                result += alphabet.substring(newPosition, newPosition + 1);
            }
            //cryptedData.setKey(0);
            cryptedData.setCrypted_text(result);
            cryptedData.setTransaction_time(System.currentTimeMillis());
        }
    }
}
