package com.operantic.contactqueryservice.config.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
      @Value("${com.operantic.jwt.aws.identityPoolUrl}")
    private String identityPoolUrl;
    private static final String USERNAME_FIELD="username";
    private static final String BEARER="Bearer ";
    private static final String AUTHORIZATION="Authorization";
    private static final String rolesFiled="cognito:groups";

    @Autowired
    ConfigurableJWTProcessor configurableJWTProcessor;

    private String getUsername(JWTClaimsSet claims){
        return claims.getClaim(USERNAME_FIELD).toString();
    }

    public Authentication authenticate(HttpServletRequest request) throws Exception{
        String token=request.getHeader(AUTHORIZATION);
        if(token !=null){
            JWTClaimsSet claims=configurableJWTProcessor.process(getToken(token), null);
           // validateToken(claims);
            String username= getUsername(claims);
            if(username !=null){
                //TODO set roles
                String roles=getRoles(claims);
                List<GrantedAuthority> authorities=rolesToList(roles).stream().map(rol->new SimpleGrantedAuthority(rol)).collect(Collectors.toList());
                log.info(authorities.toString());
                User user=new User(username, "", authorities);
                return new JwtAuthenticator(authorities,user,claims);
            }
        }
        return null;
    }

    private List<GrantedAuthority> extractAuthorities(JWTClaimsSet claims) throws ParseException {
        // Extrait les attributs de rôle du JWT et les convertit en autorisations (rôles)
        List<String> roles = claims.getStringListClaim("roles");
        if (roles != null) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void validateToken (JWTClaimsSet claims) throws Exception {
        if(claims.getIssuer().equals(identityPoolUrl)){
            throw new Exception("JWT not valid");
        }
    }

    private String getToken(String token){
        return token.startsWith(BEARER) ? token.substring(BEARER.length()) : token;
    }

    private String getRoles(JWTClaimsSet claims){
        return claims.getClaim(rolesFiled).toString();
    }

    private List<String> rolesToList(String roles){
        String noSquare=roles.replace("[","");
        noSquare=noSquare.replace("]","");
        String noQuotes=noSquare.replace("\"","");
        String noSpaces=noQuotes.replace(" ","");
        return List.of(noSpaces.split(","));
    }
}
