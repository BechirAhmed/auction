package com.auction.auction.data.services;

import com.auction.auction.data.models.RegisterLoginRequestModel;

public interface IAuthenticationRemoteService {
    boolean isLoginSuccessful(RegisterLoginRequestModel model, String encodedToken);

    boolean isRegistrationSuccessful(RegisterLoginRequestModel model);
}