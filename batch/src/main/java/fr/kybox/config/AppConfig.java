package fr.kybox.config;

import fr.kybox.entities.Email;
import fr.kybox.gencode.ObjectFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "fr.kybox")
public class AppConfig implements WebMvcConfigurer {

    @Resource
    Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**").addResourceLocations("WEB-INF/resources/");
    }

    @Bean
    public ViewResolver viewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.debug", this.environment.getProperty("mail.debug"));
        mailProperties.put("mail.smtp.auth", this.environment.getProperty("mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", this.environment.getProperty("mail.smtp.starttls.enable"));
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(this.environment.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty("mail.port")));
        mailSender.setProtocol(this.environment.getProperty("mail.protocol"));
        mailSender.setUsername(this.environment.getProperty("mail.username"));
        mailSender.setPassword(this.environment.getProperty("mail.password"));

        return mailSender;
    }

    @Bean
    public ObjectFactory objectFactory(){
        return new ObjectFactory();
    }

    @Bean
    public Email email(){
        return new Email();
    }
}
