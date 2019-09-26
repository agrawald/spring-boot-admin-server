package au.com.mayi.config;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Profile("secure")
// tag::configuration-spring-security[]
@Configuration
public class SecureConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");

        http.authorizeRequests()
            .antMatchers("/assets/**").permitAll() // <1>
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated() // <2>
            .and()
        .formLogin().loginPage("/login").successHandler(successHandler).and() // <3>
        .logout().logoutUrl("/logout").and()
        .httpBasic().and() // <4>
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // <5>
            .ignoringRequestMatchers(
                new AntPathRequestMatcher("/instances", HttpMethod.POST.toString()),  // <6>
                new AntPathRequestMatcher("/instances/*", HttpMethod.DELETE.toString()),  // <6>
                new AntPathRequestMatcher("/actuator/**")  // <7>
            )
        .and()
        .rememberMe().key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600);
        // @formatter:on
    }

    // Required to provide UserDetailsService for "remember functionality"
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}