package com.newland.oauth2server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 授权服务配置适配器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    //使用内存存储令牌(普通令牌)
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    //配置服务端点信息
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices()).tokenStore(tokenStore()).allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
    //客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()//使用内存存储
                .withClient("client")//client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端秘钥
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")//允许的授权范围
                .autoApprove(true)//false跳转到授权页面
                .redirectUris("http://www.xxx.com");//验证回调地址
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")//auth/token_key是公开
                .checkTokenAccess("permitAll()")//oauth/check_token公开
                .allowFormAuthenticationForClients();//表单认证
    }
}
