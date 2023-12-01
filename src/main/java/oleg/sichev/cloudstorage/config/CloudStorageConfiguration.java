package oleg.sichev.cloudstorage.config;

import oleg.sichev.cloudstorage.repository.UserRepository;
import oleg.sichev.cloudstorage.security.JWTFilter;
import oleg.sichev.cloudstorage.security.JWTUtil;
import oleg.sichev.cloudstorage.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author OlegSichev
 */

@Configuration
public class CloudStorageConfiguration {

    @Bean
    public JWTFilter jwtFilter(UserDetailsService userDetailsService) {
        return new JWTFilter(jwtUtil(userDetailsService));
    }

    @Bean
    public JWTUtil jwtUtil(UserDetailsService userDetailsService) {
        return new JWTUtil(userDetailsService);
    }

    @Bean
    public MyUserDetailsService myUserDetailsService(UserRepository userRepository){
        return new MyUserDetailsService(userRepository);
    }
}
