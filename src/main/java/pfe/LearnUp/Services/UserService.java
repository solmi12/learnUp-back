package pfe.LearnUp.Services;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Configurations.PasswordEncoder;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {



   @Autowired
   private UserRepository userRepository;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(java.lang.String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("user with email "+username+" not exist");
        }else {
        }


        return  new User(user.getEmail(),user.getPassword(),user.getAuthorities());
    }


    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
