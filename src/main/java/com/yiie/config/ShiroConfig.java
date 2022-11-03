package com.yiie.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yiie.shiro.CustomAccessControlFilter;
import com.yiie.shiro.CustomHashedCredentialsMatcher;
import com.yiie.shiro.CustomRealm;
import com.yiie.shiro.RedisCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Time：2020-1-2 17:27
 * Email： yiie315@163.com
 * Desc：
 *
 * @author： yiie
 * @version：1.0.0
 */
@Configuration
public class ShiroConfig {
    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }

    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm=new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(redisCacheManager());
        return customRealm;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager();
    }


    @Bean
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/sys/user/login", "anon");

        filterChainDefinitionMap.put("/sys/loadPicture", "anon");
        filterChainDefinitionMap.put("/sys/loadPicture2", "anon");
        filterChainDefinitionMap.put("/sys/uploadBlackFile", "anon");
        filterChainDefinitionMap.put("/sys/loadGymPicture", "anon");
        filterChainDefinitionMap.put("/index/loadGymPicture", "anon");
        filterChainDefinitionMap.put("/sys/gym/getPicture/**", "anon");
        //开发所有静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/static/pic/*", "anon");
        filterChainDefinitionMap.put("/pic/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");

        filterChainDefinitionMap.put("/index/**", "anon");
        filterChainDefinitionMap.put("*.html", "anon");
        //图片上传
        filterChainDefinitionMap.put("/sys/user/token", "anon");
        //Gym名称联想
        filterChainDefinitionMap.put("/sys/searchGymName", "anon");

        //放开swagger-ui地址
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/images/*.svg", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/captcha.jpg", "anon");
        filterChainDefinitionMap.put("/","user");
        filterChainDefinitionMap.put("/csrf","anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");

        //添加lanhu资源
        filterChainDefinitionMap.put("/lanhu/**", "anon");

        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/treetable-lay/**", "anon");

        filterChainDefinitionMap.put("/**","token,authc");
        shiroFilterFactoryBean.setLoginUrl("/index/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
