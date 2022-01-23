package ru.mikescherbakov;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import ru.mikescherbakov.config.SettingsManager;
import ru.mikescherbakov.config.WebMvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebMvcConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        String TMP_FOLDER = SettingsManager.getString("TMP_FOLDER");
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                SettingsManager.getInt("MAX_UPLOAD_SIZE"),
                SettingsManager.getInt("MAX_UPLOAD_SIZE") * 2,
                SettingsManager.getInt("MAX_UPLOAD_SIZE") / 2);

        dispatcher.setMultipartConfig(multipartConfigElement);
    }
}
