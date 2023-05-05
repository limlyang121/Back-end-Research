package com.myapp.restapi.researchconference.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetDataFromJWT {

    private final String SECRET_KEY = "SomeRandomStringalsdjsadjsadlksajdlkasjdlakjdlajdadjalkdjalsdjaiwjdaildjslk";

    @Autowired
    public GetDataFromJWT() {
    }

    public int getID(HttpServletRequest request){
        try{
            String jwt = request.getHeader("Authorization").replace("Bearer ", "");
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
            int userID = ((Number) claims.get("id")).intValue();
            return userID;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public String getRole(HttpServletRequest request){
        try{
            String jwt = request.getHeader("Authorization").replace("Bearer ", "");
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
            String roleName = (String) claims.get("myRole");
            return roleName;
        }catch (Exception e){
            return null;
        }
    }
}
