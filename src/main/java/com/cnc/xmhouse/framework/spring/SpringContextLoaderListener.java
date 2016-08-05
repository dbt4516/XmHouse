package com.cnc.xmhouse.framework.spring;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import com.cnc.xmhouse.framework.config.SpringWebConfig;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import java.beans.Introspector;

public class SpringContextLoaderListener extends ContextLoaderListener {
    private static AnnotationConfigWebApplicationContext rootContext;

    static {
        rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppsApplicationConfig.class, SpringWebConfig.class);
    }

    public SpringContextLoaderListener() {
        super(rootContext);
    }

    public SpringContextLoaderListener(WebApplicationContext context) {
        super(context);
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {


        CachedIntrospectionResults.acceptClassLoader(Thread.currentThread().getContextClassLoader());

        // 启动spring

        super.contextInitialized(event);

        configDispatcherServlet(event.getServletContext());
    }

    private void configDispatcherServlet(ServletContext servletContext){
        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);//找不到handler则抛出异常, 统一处理

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setAsyncSupported(true);
        dispatcher.setMultipartConfig(multipartConfigElement());

    }


    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        super.contextDestroyed(event);

        CachedIntrospectionResults.clearClassLoader(Thread.currentThread().getContextClassLoader());
        Introspector.flushCaches();
    }

}
