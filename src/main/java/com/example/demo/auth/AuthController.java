package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.User;
import com.example.demo.UserRepository;
import com.example.demo.jwt.ErrorRes;
import com.example.demo.jwt.LoginReq;
import com.example.demo.jwt.LoginRes;
import com.example.demo.jwt.RegisterReq;

@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
@Controller
@RequestMapping("/rest/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }
    @CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            String username = authentication.getName();
            User user = new User(username,"");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(username,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
     @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterReq registerReq) {
        try {
            // Sprawdź, czy użytkownik o podanej nazwie już istnieje w bazie danych
            if (userRepository.existsByUsername(registerReq.getUsername())) {
                ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Username already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Stwórz nowego użytkownika
            User newUser = new User(registerReq.getUsername(), registerReq.getPassword());
            userRepository.save(newUser);

            // Utwórz token JWT dla nowego użytkownika
            String token = jwtUtil.createToken(newUser);
            LoginRes loginRes = new LoginRes(newUser.getUsername(), token);

            return ResponseEntity.ok(loginRes);

        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}