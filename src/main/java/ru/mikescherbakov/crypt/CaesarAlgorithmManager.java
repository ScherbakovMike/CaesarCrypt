package ru.mikescherbakov.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CaesarAlgorithmManager implements AlgorithmManager {
    Logger logger = LoggerFactory.getLogger(CaesarAlgorithmManager.class);

    private String getAlphabet() {
        String alphabet = "";
        alphabet += "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        alphabet += "1234567890";
        alphabet += "~`@\"№#;$%:^?&*()-_+=|\\//' ";

        return alphabet;
    }

    @Override
    public void crypt(CryptedData cryptedData, MultipartFile file) {
        StringBuilder result = new StringBuilder();

        //String source_text = cryptedData.getSource_text().toUpperCase();
        String source_text = "";
        try {
            source_text = new String(file.getBytes(), Charset.forName("UTF-8"));
            source_text = source_text.toUpperCase();
            cryptedData.setSource_text(source_text);
        } catch (IOException e) {
            cryptedData.setCrypt_result(CryptResult.ERROR);
            logger.error("file.getBytes() error", e);
        }

        //source_text = StringEscapeUtils.unescapeHtml3(source_text);
        int key = cryptedData.getCrypt_key();

        if (key != 0) {
            String alphabet = getAlphabet();

            for (int i = 0; i < source_text.length(); i++) {
                String from = source_text.substring(i, i + 1);
                String to;
                if (alphabet.contains(from)) {
                    int currentPosition = alphabet.indexOf(from);
                    int newPosition = (currentPosition + key) % alphabet.length();
                    to = alphabet.substring(newPosition, newPosition + 1);
                } else {
                    to = from;
                }

                result.append(to);
            }
            cryptedData.setCrypted_text(result.toString());
            cryptedData.setTransaction_time(System.currentTimeMillis());
            cryptedData.setCrypt_result(CryptResult.SUCCESS);
        } else
            cryptedData.setCrypt_result(CryptResult.ERROR);
    }

    @Override
    public String decrypt(CryptedData cryptedData, DecryptMethod method) {
        if (method == DecryptMethod.BRUTEFORCE) {
            String alphabet = getAlphabet();
            int key = 0;
            StringBuilder decryptedText = new StringBuilder();
            String cryptedText = cryptedData.getCrypted_text();
            boolean success = false;
            do {
                for (int i = 0; i < cryptedText.length(); i++) {
                    String from = cryptedText.substring(i, i + 1);
                    String to;
                    if (alphabet.contains(from)) {
                        int currentPosition = alphabet.indexOf(from);
                        int newPosition = (currentPosition + key) % alphabet.length();
                        to = alphabet.substring(newPosition, newPosition + 1);
                    } else {
                        to = from;
                    }

                    decryptedText.append(to);
                }
                if(textCriteria(decryptedText.toString())){
                    success = true;
                    break;
                }

                key++;
            } while(key<alphabet.length());

            if(!success)
                return "Can't decrypt by bruteforce method";
            else
                return decryptedText.toString();

        } else {
            return "Unsupported decrypt method";
        }
    }

    private boolean textCriteria(String text) {
        if (text.length() == 0)
            return true;

        LinkedHashMap<Character, Integer> statMap = characterCount(text);
        Object[] entries = statMap.entrySet().toArray();
        return ((Map.Entry<Character, Integer>) entries[0]).getKey().equals(' ');
    }

    private LinkedHashMap<Character, Integer> characterCount(String inputString) {
        LinkedHashMap<Character, Integer> charCountMap
                = new LinkedHashMap<>();

        char[] strArray = inputString.toCharArray();
        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {
                charCountMap.put(c, charCountMap.get(c) + 1);
            } else {
                charCountMap.put(c, 1);
            }
        }

        return charCountMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

    }
}
