package com.auction.auction.data.services;

import com.auction.auction.data.models.RegisterLoginRequestModel;
import com.auction.auction.data.models.RegisterResponseModel;
import com.auction.auction.utils.GetRequestUtils;
import com.auction.auction.utils.PostRequestUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationRemoteService implements IAuthenticationRemoteService {
    private static final String REGISTER_REQUEST_URL = "http://192.168.1.106:3000/api/users";
    private static final String LOGIN_REQUEST_URL = "http://192.168.1.106:3000/api/login";

    public AuthenticationRemoteService() {

    }

    public boolean isLoginSuccessful(RegisterLoginRequestModel model, String encodedToken) {
        Map<String, String> loginRequestHeaders = new HashMap<>();
        loginRequestHeaders.put("Authorization", "Basic " + encodedToken);
        String loginRequestResult = GetRequestUtils.make(AuthenticationRemoteService.LOGIN_REQUEST_URL, loginRequestHeaders);
        if (loginRequestResult.equals("Authorized")) {
            return true;
        }

        return false;
    }

    public boolean isRegistrationSuccessful(RegisterLoginRequestModel model) {
        String registerRequestResult = PostRequestUtils.make(AuthenticationRemoteService.REGISTER_REQUEST_URL, model, new HashMap<String, String>());
        RegisterResponseModel responseModel = new Gson().fromJson(registerRequestResult, RegisterResponseModel.class);
        if (responseModel.username != null && responseModel._id != null && responseModel.username.equals(model.username)) {
            return true;
        }

        return false;
    }
}