package com.antazri.service.impl;

import com.antazri.generated.auth.*;

public class AuthenticationClientService extends AuthenticationService implements AuthPort {

    @Override
    public DoLoginResponse doLogin(DoLoginRequest parameters) throws BadLoginException {
        return getAuthenticationPort().doLogin(parameters);
    }
}
