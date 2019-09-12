package app.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Spring跟容器
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * SpringMVC容器
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {AppConfig.class};
    }

    /**
     * DispatcherServlet映射,从"/"开始
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
