package pfe.LearnUp.Configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pfe.LearnUp.Services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;
    private final JwtTokenFilter jwtTokenFilter;
    @Autowired
    private  CorsFilterConfig corsFilterConfig;
    private final JwtTokenUtil jwtTokenUtil;

    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;

    }
    @Bean

    public JwtTokenFilter authFilter(){
        return new JwtTokenFilter();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userService.loadUserByUsername(username));
    }

    @Bean
    public SecurityFilterChain filterAdminChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/students/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/students/all").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/students/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/students/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/students/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/formateur/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/formateur/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/formateur/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/question/**").permitAll()

                        .requestMatchers(HttpMethod.PUT,"/question/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/question/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/formateur/add").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/formateur/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/apprenantCour/**").permitAll()

                        .requestMatchers(HttpMethod.PUT,"/apprenantCour/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/apprenantCour/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/apprenantCour/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/admin/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/chapitres/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/chapitres/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/chapitres/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/chapitres/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/admin/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/cour/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/cour/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/cour/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/cour/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider());

        httpSecurity.addFilterBefore(corsFilterConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
