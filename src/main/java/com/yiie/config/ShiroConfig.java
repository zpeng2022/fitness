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
        //新建
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/sys/user/login", "anon");
        // customer
        filterChainDefinitionMap.put("/customer/user/register", "anon");
        filterChainDefinitionMap.put("/customer/user/login", "anon");

        filterChainDefinitionMap.put("/sys/loadPicture", "anon");
        filterChainDefinitionMap.put("/sys/loadPicture2", "anon");
        filterChainDefinitionMap.put("/sys/uploadBlackFile", "anon");
        filterChainDefinitionMap.put("/sys/loadGymPicture", "anon");
        filterChainDefinitionMap.put("/index/loadGymPicture", "anon");
        filterChainDefinitionMap.put("/sys/gym/getPicture/**", "anon");
        //黑名单模板下载
        filterChainDefinitionMap.put("/sys/downloadModel", "anon");
        filterChainDefinitionMap.put("/sys/download", "anon");

        //开发所有静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");

        filterChainDefinitionMap.put("/index/**", "anon");
        filterChainDefinitionMap.put("*.html", "anon");
        //图片上传
        filterChainDefinitionMap.put("/sys/user/token", "anon");
        //Gym名称联想
        filterChainDefinitionMap.put("/sys/searchGymName", "anon");
        //gymInfo
//        filterChainDefinitionMap.put("/sys/gymInfo", "anon");

        //h5
        filterChainDefinitionMap.put("/sys/H5GymOrders", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerRecords", "anon");
        filterChainDefinitionMap.put("/sys/H5CancelGymOrder", "anon");
        filterChainDefinitionMap.put("/sys/H5GymComments", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerOtherInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerUpdateBasicInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerBasicInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5AddGymComment", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerContactInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerAddContactInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5AddGymOrders", "anon");
        filterChainDefinitionMap.put("/sys/H5CustomerDeleteContactInfo", "anon");
        filterChainDefinitionMap.put("/sys/H5Gyms", "anon");
        filterChainDefinitionMap.put("/sys/H5GymCommentTags", "anon");
        filterChainDefinitionMap.put("/sys/H5SearchGyms", "anon");
        filterChainDefinitionMap.put("/banner/36kr", "anon");
        //2023/1/18开放
        filterChainDefinitionMap.put("/H5/getH5Background", "anon");
        filterChainDefinitionMap.put("/H5/changeH5Background", "anon");
        filterChainDefinitionMap.put("/H5/getSportIndex", "anon");
        filterChainDefinitionMap.put("/H5/getTodaySportData", "anon");
        filterChainDefinitionMap.put("/H5/getSportData", "anon");
        filterChainDefinitionMap.put("/H5/getSportTestData", "anon");
        //2023/3/6开放
        filterChainDefinitionMap.put("/H5/CustomerBackground", "anon");
        filterChainDefinitionMap.put("/index/customerChangeBackground", "anon");

        //取消的预约提醒接口
        filterChainDefinitionMap.put("/sys/H5UnreadCancelOrders", "anon");
        filterChainDefinitionMap.put("/sys/H5AllCancelOrders", "anon");
        filterChainDefinitionMap.put("/sys/H5ReadCancelOrders", "anon");
        filterChainDefinitionMap.put("/sys/H5DelCancelOrders", "anon");

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
        filterChainDefinitionMap.put("/pic/**", "anon");
        filterChainDefinitionMap.put("/var/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/editormd/**", "anon");
        filterChainDefinitionMap.put("/layuimini/**", "anon");
        //下拉多选框静态资源
        filterChainDefinitionMap.put("/layui-select-ext-master/**", "anon");

        //添加lanhu资源
        filterChainDefinitionMap.put("/lanhu/**", "anon");
        //layuimini
//        filterChainDefinitionMap.put("/static/layuimini/**", "anon");

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
