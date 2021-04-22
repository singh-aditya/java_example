package com.company.todos.api.user;

import com.company.todos.api.user.model.UserLoginDetails;
import com.company.todos.security.Constants;
import com.company.todos.security.token.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Login {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * FakeLogin method so that Open API documentation is generated for login url
     * Spring security automatically over-ride this end point as we have defined
     * Authentication Filter for login
     * @param loginReq Login req body
     */
    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    headers = {
                            @Header(name = "authorization",
                                    description = "Bearer {JWT value here}"),
                            @Header(name = "userId",
                                    description = "{Public User Id value here}")
                    })
    })
    @PostMapping(Constants.USER_LOGIN_URL)
    public void theFakeLogin(@RequestBody UserLoginDetails loginReq) {
        throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
    }

    @Operation(summary = "Refresh Login token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    headers = {
                            @Header(name = "authorization",
                                    description = "Bearer {JWT value here}")
                    })
    })
    @GetMapping(Constants.USER_LOGIN_URL + "/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest req, HttpServletResponse res) {
        String token = jwtTokenUtil.getTokenFromReq(req);

        if (token != null && jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            res.addHeader(Constants.AUTH_HEADER_PREFIX, Constants.TOKEN_PREFIX + refreshedToken);
            return ResponseEntity.ok(refreshedToken);
        }
        return ResponseEntity.badRequest().build();
    }
}
