package com.myapp.restapi.researchconference.Rest;

import com.myapp.restapi.researchconference.Exception.InvalidUsernameOrPassword;
import com.myapp.restapi.researchconference.Restservice.Impl.MyUserDetails;
import com.myapp.restapi.researchconference.Util.JwtUtil;
import com.myapp.restapi.researchconference.entity.AuthenticationRequest;
import com.myapp.restapi.researchconference.entity.AuthenticationResponse;
import com.myapp.restapi.researchconference.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetails MyUserDetails;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new InvalidUsernameOrPassword("Incorrect username or password");
        }

        final CustomUserDetails userDetails = MyUserDetails.loadUserByUsername(request.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @PostMapping("logout")
    public ResponseEntity<String> logOut(){
        MyUserDetails.logout();
        return ResponseEntity.ok("Successfully Log out");
    }

}
