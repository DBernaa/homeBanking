package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/client/current/account","/api/client/current/cards", "/api/client/current/transactions").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.PATCH, "/api/client/current/cards/remove","/api/client/current/account/remove").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.POST, "/api/admin/loans").hasAnyAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/client","/api/loans").permitAll()


                .antMatchers("/web/index.html","/web/index.js", "/style.css", "/stylelanding.css", "/api/client/current","/api/loans").permitAll()

                .antMatchers("/web/**","/api/client").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers("/api/**", "/rest/**","/h2-console","manager.html","manager.js").hasAuthority("ADMIN");

        http.formLogin()
                .usernameParameter("email")

                .passwordParameter("pwd")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}

