package com.maptools.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Created by mr on 2017/8/21.
 */
public class WebInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        ctx.setServletContext(servletContext);

        Dynamic servletRegistration=servletContext.addServlet("dispatcher",new DispatcherServlet(ctx));
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(1);
    }


}
