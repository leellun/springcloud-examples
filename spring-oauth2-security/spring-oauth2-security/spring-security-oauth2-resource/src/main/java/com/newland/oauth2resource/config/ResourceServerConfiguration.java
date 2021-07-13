package com.newland.oauth2resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 此处r1 r2 使用角色 r3 r4 使用权限
                // 验证WebSecurityConfiguration roles authorities 在后面的才会生效。
                // roles("r1","r2").authorities("r3","r4")  roles无效 authorities有效
                .antMatchers("/r1").hasAnyRole("r1")
                .antMatchers("/r2").hasAnyRole("r2")
                .antMatchers("/r3").hasAnyAuthority("r3")
                .antMatchers("/r4").hasAnyAuthority("r4")
                .antMatchers("/**").permitAll();
    }

    //资源服务令牌解析服务 配置远程ResourceServerTokenServices后，可不用设置yml远程security.oauth2配置
//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
//        RemoteTokenServices service = new RemoteTokenServices();
//        service.setCheckTokenEndpointUrl("http://localhost:10101/oauth/check_token");
//        service.setClientId("client");
//        service.setClientSecret("secret");
//        return service;
//    }
}
