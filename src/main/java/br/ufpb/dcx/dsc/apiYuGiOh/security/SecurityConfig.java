package br.ufpb.dcx.dsc.apiYuGiOh.security;


import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**",  "/", "/error","/api/login", "/api/change-password", "/api/register", "/h2-ui/**"};

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST_URL).permitAll()

                        //EndPoints de User
                        //Permitir tanto ADMIN quanto USER possam acessar as rotas PUT's específicas
                        .requestMatchers(HttpMethod.PUT, "/api/user/{userId}/deck/{deckId}/add").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/{userId}/deck/{deckId}/remove").hasAnyRole("USER", "ADMIN")
                        //Permitir apenas ADMIN acessar as rotas POST, PUT, DELETE e GET
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")

                        //EndPoints de Photo
                        //Permitir apenas ADMIN acessar as rotas POST, PUT, DELETE e GET
                        .requestMatchers(HttpMethod.GET, "/api/photo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/photo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/photo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/photo/**").hasRole("ADMIN")

                        //Endpoints de Card
                        //Permitir que tanto USER quanto ADMIN possam acessar a rota GET
                        .requestMatchers(HttpMethod.GET, "/api/cards/**").hasAnyRole("USER", "ADMIN")
                        // Permitir apenas ADMIN acessar as rotas POST, PUT, DELETE
                        .requestMatchers(HttpMethod.POST, "/api/cards/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cards/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cards/{cardId}/photo/{photoId}/remove").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cards/{cardId}/photo/{photoId}/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cards/**").hasRole("ADMIN")

                        //Endpoints de Deck
                        //Permitir que tanto USER quanto ADMIN possam acessar a rota GET, POST, PUTs e Delete
                        .requestMatchers(HttpMethod.GET, "/api/deck/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/deck/{deckId}/card/{cardId}/add").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/deck/{deckId}/card/{cardId}/remove").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/deck/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/deck/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/deck/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());  // Certifique-se de que o PasswordEncoder é definido aqui
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
