package ru.mikescherbakov;

import ru.mikescherbakov.crypt.CryptedData;
import ru.mikescherbakov.crypt.CryptedService;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class Solution {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.refresh();
        ctx.registerBean("crypt.CryptedService",
                CryptedService.class, () -> new CryptedService());

        CryptedService cryptedService1 = (CryptedService) ctx.getBean(CryptedService.class);

       // ctx.
//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.
//        ctx.load("classpath:spring-config.xml"); //move from src.main.java to src.main.resources
        //ctx.refresh();

        //CryptedService cryptedService2 = ctx.getBean("CryptedService", CryptedService.class);
        //List<CryptedData> contacts = service.listAll();

    }
}
