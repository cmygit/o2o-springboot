package com.cmy.o2o.config.web;

import com.cmy.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.cmy.o2o.interceptor.shopadmin.ShopPermissionInterceptor;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Author : cmy
 * Date   : 2018-03-13 14:47.
 * desc   : spring web 配置
 */
@Configuration
// 开启MVC，自动注入spring容器
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    // spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:\\DevelopWorkspace\\java-demo\\spring-leanrn\\o2o-assert\\upload\\");
    }

    /**
     * 定义默认的请求处理器
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建ViewResolver
     *
     * @return
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置spring容器
        viewResolver.setApplicationContext(this.applicationContext);
        // 取消缓存
        viewResolver.setCache(false);
        // 设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        // 设置视图解析的后缀
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // 1024 * 1024 * 20 = 20M
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }

    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.textproducer.font.color}")
    private String textproducerFontColor;

    @Value("${kaptcha.image.width}")
    private String imageWidth;

    @Value("${kaptcha.textproducer.char.string}")
    private String textproducerCharString;

    @Value("${kaptcha.image.height}")
    private String imageHeight;

    @Value("${kaptcha.textproducer.font.size}")
    private String textproducerFontSize;

    @Value("${kaptcha.noise.color}")
    private String noiseColor;

    @Value("${kaptcha.textproducer.char.length}")
    private String textproducerCharLength;

    @Value("${kaptcha.textproducer.font.names}")
    private String textproducerFontNames;

    /**
     * kaptcha 验证码配置
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean serlvet = new ServletRegistrationBean(new KaptchaServlet(), "/kaptcha");
        // 是否有边框
        serlvet.addInitParameter("kaptcha.border", border);
        // 字体颜色
        serlvet.addInitParameter("kaptcha.textproducer.font.color", textproducerFontColor);
        // 图片宽度
        serlvet.addInitParameter("kaptcha.image.width", imageWidth);
        // 使用哪些字符生成验证码
        serlvet.addInitParameter("kaptcha.textproducer.char.string", textproducerCharString);
        // 图片高度
        serlvet.addInitParameter("kaptcha.image.height", imageHeight);
        // 字体大小
        serlvet.addInitParameter("kaptcha.textproducer.font.size", textproducerFontSize);
        // 扰线颜色
        serlvet.addInitParameter("kaptcha.noise.color", noiseColor);
        // 字符个数
        serlvet.addInitParameter("kaptcha.textproducer.char.length", textproducerCharLength);
        // 字体
        serlvet.addInitParameter("kaptcha.textproducer.font.names", textproducerFontNames);
        return serlvet;
    }

    /**
     * 添加拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        // 注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        // 配置拦截的路径
        loginIR.addPathPatterns(interceptPath);
        // 注册其拦截器
        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
        // 配置拦截的路径
        permissionIR.addPathPatterns(interceptPath);
        // 配置不拦截的路径
        /* shoplist page */
        permissionIR.excludePathPatterns("/shopadmin/shoplist");
        permissionIR.excludePathPatterns("/shopadmin/getshoplist");
        /* shopregister page */
        permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
        permissionIR.excludePathPatterns("/shopadmin/registershop");
        permissionIR.excludePathPatterns("/shopadmin/shopoperation");
        /* shopmanage page */
        permissionIR.excludePathPatterns("/shopadmin/shopmanage");
        permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
    }
}

