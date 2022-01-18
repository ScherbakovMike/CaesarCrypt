package ru.mikescherbakov.crypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping(value = "/crypt", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute("cryptedData") CryptedData cryptedData) {
        cryptedService.cryptAndSave(cryptedData);
        return "redirect:/";
    }
}
