package uz.pdp.appclickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.enums.SystemRole;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.LoginDto;
import uz.pdp.appclickup.payload.RegisterDto;
import uz.pdp.appclickup.repository.UserRepository;
import uz.pdp.appclickup.security.JwtProvider;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ApiResponse registerUser(RegisterDto registerDto) {

        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Such user already exists", false);

        User user = new User(registerDto.getFullName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                SystemRole.SYSTEM_USER);

        int code = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(code).substring(0, 4));
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("User successfully registered", true);
    }


    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("test@Pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Account ni Tasdiqlash");
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent())
            return new ApiResponse("Such user not found", false);

        User user = optionalUser.get();

        if (user.getEmailCode().equals(emailCode)) {
            user.setEnabled(true);
            userRepository.save(user);
            return new ApiResponse("User successfully logged", true);
        } else {
            return new ApiResponse("EmailCode is wrong", false);
        }

    }

    public ApiResponse loginUser(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginDto.getEmail(), loginDto.getPassword()));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(user.getEmail());
            return new ApiResponse(token, true);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Parol yoki login xato", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s + "topilmadi"));

    }
}
