package com.hansliao.springboot_mall.config;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * SecurityConfig
 */

// 若要自訂登入邏輯則要繼承 WebSecurityConfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 禁用 CSRF 防護 (僅適用於測試或開發環境)
        http.csrf(csrf -> csrf.disable());
        
        // // 表單提交
        // http.formLogin(login -> login
        //         // login.html 表單 action 內容
        //         .loginProcessingUrl("/users/login")
        //         // 自定義登入頁面
        //         .loginPage("/users/login/index")
        //         // 登入成功後要造訪的頁面
        //         .successForwardUrl("/index")
        //         // 登入失敗後要造訪的頁面
        //         .failureForwardUrl("/fail"));

        // // 授權認證
        // http.authorizeRequests(requests -> requests
        //         // 不需要被認證的頁面
        //         .antMatchers("/users/login/index").permitAll()
        //         // 其他的都要被認證
        //         .anyRequest().permitAll());
    }

    // ! 一定要建立密碼演算的實例 !
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}