package com.auction.auction.data.services;

import com.auction.auction.data.models.RegisterRequestModel;

public interface IAuthenticationRemoteService {
    boolean isLoginSuccessful(String encodedToken);

    boolean isRegistrationSuccessful(RegisterRequestModel model);
}