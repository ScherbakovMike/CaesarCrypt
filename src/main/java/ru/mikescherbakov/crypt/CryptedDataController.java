package ru.mikescherbakov.crypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class CryptedDataController {
    private final CryptedService cryptedService;

    @Autowired
    public CryptedDataController(CryptedService cryptedService) {
        this.cryptedService = cryptedService;
    }

    // главная страница
    @RequestMapping("/html")
    public ModelAndView home() {
        List<CryptedData> listCryptedData = cryptedService.listAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("listCryptedData", listCryptedData);
        return mav;
    }

    // новая запись
    @RequestMapping("/new")
    public String newCryptedDataForm(Map<String, Object> model) {
        CryptedData cryptedData = new CryptedData();
        model.put("cryptedData", cryptedData);
        return "new_crypteddata";
    }

    // сохранить и зашифровать
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCryptedData(@ModelAttribute("cryptedData") CryptedData cryptedData, @RequestParam("file") MultipartFile file) {
        if (Objects.equals(file.getContentType(), "text/plain")) {
            cryptedService.cryptAndSave(cryptedData, file);
            return "redirect:/";
        } else return "uncorrectfile";
    }

    @RequestMapping("/delete")
    public String deleteCryptedDataForm(@RequestParam long id) {
        cryptedService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/saveresult",
            method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> saveresultCryptedDataForm(@RequestParam long id) {
        byte[] documentBody = cryptedService.getFileFor(id).getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=crypted_" + id + ".txt");
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

    @RequestMapping(value = "/decrypt_bruteforce",
            method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> decryptBruteforce(@RequestParam long id) {
        byte[] documentBody = cryptedService.getDecryptedFile(id, DecryptMethod.BRUTEFORCE).getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=decrypted_" + id + ".txt");
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

    @RequestMapping(value = "/loadDictionary",
            method = RequestMethod.GET)
    public ModelAndView loadDictionary(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("stat_analysis");
        CryptedData cryptedData = cryptedService.get(id);
        mav.addObject("cryptedData", cryptedData);
        return mav;
    }

    @RequestMapping(value = "/decrypt_statanalysis",
            method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<byte[]> decryptStatAnalysis(@ModelAttribute("cryptedData") CryptedData cryptedData, @RequestParam("file") MultipartFile file) {
        byte[] documentBody = cryptedService.getDecryptedFile(cryptedData.getId(), DecryptMethod.STAT_ANALISYS, file).getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=decrypted_" + cryptedData.getId() + ".txt");
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }
}

