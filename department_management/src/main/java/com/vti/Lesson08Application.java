package com.vti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vti.enitty.Account;
import com.vti.form.AccountCreateForm;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class Lesson08Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson08Application.class, args);

    }

    @Bean
//    Cấu hình khi mark từ acf sang a , sẽ skip id của account
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AccountCreateForm.class, Account.class)
                .addMappings(mapper -> mapper.skip(Account::setId));
        return modelMapper;
    }

    @Bean
//    Phương thức tìm ra ObjectWriter
    public ObjectWriter objectWriter() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .writerWithDefaultPrettyPrinter();
    }

    @Bean
//    Cấu hình những phương thức bất cứ HEAD", "GET", "POST", "PUT", "DELETE" đều được chấp nhận để lấy tài nguyên


    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500")
//                        127.0.0.1 ~ localhost
//                        Cho phép miền này qua ấy dữ liệu
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
