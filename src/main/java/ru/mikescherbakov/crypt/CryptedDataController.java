package ru.mikescherbakov.crypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
public class CryptedDataController {

    @Autowired
    private CryptedService cryptedService;

    // главная страница
    @RequestMapping("/")
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
        if (file.getContentType().equals("text/plain")) {
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

        return new HttpEntity<byte[]>(documentBody, header);
    }

    @RequestMapping(value = "/decrypt_bruteforce",
            method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> decryptBruteforce(@RequestParam long id) {
        byte[] documentBody = cryptedService.getDecryptedFile(id, DecryptMethod.BRUTEFORCE).getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=decrypted_" + id + ".txt");
        header.setContentLength(documentBody.length);

        return new HttpEntity<byte[]>(documentBody, header);
    }

    @RequestMapping(value = "/decrypt_statanalysis",
            method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> decryptStatanalysis(@RequestParam long id) {
        byte[] documentBody = cryptedService.getDecryptedFile(id, DecryptMethod.STAT_ANALISYS).getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=decrypted_" + id + ".txt");
        header.setContentLength(documentBody.length);

        return new HttpEntity<byte[]>(documentBody, header);
    }
}

