package lk.ijse.gdse.aad68.notetaker;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import lk.ijse.gdse.aad68.notetaker.config.WebAppConfig;
import lk.ijse.gdse.aad68.notetaker.config.WebAppRootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebAppRootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) { //// conveyance ekk widiyt tempery folder ek denne
        String tempDir = System.getProperty("java.io.tmpdir"); //temper directory ek arn ek assign krnne
        registration.setMultipartConfig(new MultipartConfigElement(tempDir));
    }
}
