package ru.mikescherbakov.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
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

        String source_text = "";
        try {
            source_text = new String(file.getBytes(), StandardCharsets.UTF_8);
            source_text = source_text.toUpperCase();
            cryptedData.setSource_text(source_text);
        } catch (IOException e) {
            cryptedData.setCrypt_result(CryptResult.ERROR);
            logger.error("file.getBytes() error", e);
        }

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
            int key = 15;
            StringBuilder decryptedText = new StringBuilder();
            String cryptedText = cryptedData.getCrypted_text();
            boolean success = false;
            do {
                for (int i = 0; i < cryptedText.length(); i++) {
                    String from = cryptedText.substring(i, i + 1);
                    String to;
                    if (alphabet.contains(from)) {
                        int currentPosition = alphabet.indexOf(from);
                        int newPosition = currentPosition - key;

                        if (newPosition < 0)
                            newPosition = alphabet.length() + newPosition;
                        to = alphabet.substring(newPosition, newPosition + 1);
                    } else {
                        to = from;
                    }

                    decryptedText.append(to);
                }
                if (textCriteria(decryptedText.toString())) {
                    success = true;
                    break;
                }

                key++;
            } while (key < alphabet.length());

            if (!success)
                return "Can't decrypt by bruteforce method";
            else
                return decryptedText.toString();

        } else {
            String dictionary = cryptedData.getSource_text();
            String cryptedText = cryptedData.getCrypted_text();
            String alphabet = getAlphabet();

            LinkedHashMap<Character, Double> characterPercentMapDictionary = characterPercentMap(dictionary);
            LinkedHashMap<Character, Double> characterPercentMapCryptedText = characterPercentMap(cryptedText);

            Iterator<Map.Entry<Character, Double>> iterator = characterPercentMapDictionary.entrySet().iterator();

            char[] decryptedArray = cryptedText.toCharArray();
            for (Map.Entry<Character, Double> entryCrypted : characterPercentMapCryptedText.entrySet()) {
                Character from = entryCrypted.getKey();
                if(alphabet.contains(String.valueOf(from))) {
                    if(!iterator.hasNext())
                        break;

                    Map.Entry<Character, Double> entryDictionary = iterator.next();
                    Character to = entryDictionary.getKey();
                    int pos = cryptedText.indexOf(from.toString());
                    while (pos != -1) {
                        decryptedArray[pos] = to;
                        pos = cryptedText.indexOf(from.toString(), pos + 1);
                    }
                }
            }

            return new String(decryptedArray);
        }
    }

    private boolean textCriteria(String text) {
        if (text.length() == 0)
            return true;
        LinkedHashMap<Character, Integer> statMap = characterCountMap(text);
        if (statMap.get('А') == null || statMap.get('О') == null || statMap.get('И') == null)
            return false;

        Long globalCount = statMap.values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum();
        double aFrequency = Double.valueOf(statMap.get('А')) / globalCount;
        double bFrequency = Double.valueOf(statMap.get('О')) / globalCount;
        double cFrequency = Double.valueOf(statMap.get('И')) / globalCount;
        return aFrequency > 0.04 && bFrequency > 0.04 && cFrequency > 0.04;
    }

    private LinkedHashMap<Character, Integer> characterCountMap(String inputString) {
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

    private LinkedHashMap<Character, Double> characterPercentMap(String inputString) {
        LinkedHashMap<Character, Integer> charCountMap = characterCountMap(inputString);
        long charCount = charCountMap.values().stream().collect(Collectors.summarizingInt(Integer::intValue)).getSum();
        return charCountMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        n -> 100.0 * n.getValue().doubleValue() / charCount,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

}
