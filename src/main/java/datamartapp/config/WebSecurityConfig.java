package datamartapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/js/**").permitAll()
                        .requestMatchers("/data-mart/admin**").hasRole("ADMIN")
                        .requestMatchers("/data-mart/home").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/data-mart/login")
                        .defaultSuccessUrl("/data-mart/home", true))
                .logout(logout -> logout.permitAll().logoutSuccessUrl("/data-mart/login"));
        return http.build();
    }


/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/data-mart/admin/**").hasRole("ADMIN") //requestMatchers("/data-mart/resources/**").permitAll()
                        .requestMatchers("/data-mart/home").hasRole("USER")
                        .requestMatchers("/data-mart/home").hasRole("ADMIN")
                        ) //.anyRequest().authenticated()
                .formLogin(form -> form.loginPage("/data-mart/login").permitAll()
                        .defaultSuccessUrl("/data-mart/home", true).permitAll())
                .logout(logout -> logout.permitAll().logoutSuccessUrl("/data-mart/login"));
        return http.build();
    }*/

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/data-mart/resources/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin(form -> form.loginPage("/data-mart/login")
                        .defaultSuccessUrl("/data-mart/home", true).permitAll())
                .logout(logout -> logout.permitAll().logoutSuccessUrl("/data-mart/login"));
        return http.build();
    }*/

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authz -> authz
                        .requestMatchers("/data-mart/admin/user/**").hasRole("ADMIN")
                        .requestMatchers("/data-mart/login/**").permitAll()
                        .requestMatchers("/resources/**").permitAll())




                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }*/

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authz -> authz
                        .requestMatchers("/data-mart/admin/user/**").hasRole("ADMIN")
                        .requestMatchers("/data-mart/login/**").permitAll()
                        .requestMatchers("/resources/**").permitAll())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home", true))
                //.httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }*/

}
