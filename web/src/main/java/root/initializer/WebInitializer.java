package root.initializer;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import root.config.PersistenceConfig;
import root.config.WebConfig;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String SERVLET_MAPPING = "/";

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{PersistenceConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{SERVLET_MAPPING};
    }

}