package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

/**
 * Service class for providing authentication funtionality.
 */
@Service
public class AuthenticationService {

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    /**
     * Method is used for providing authentication to a user
     * The method takes username and password as argument.
     *
     * @param username String username
     * @param password String password
     * @return UserAuthEntity object
     * @throws AuthenticationFailedException exception indicating Authentication failed
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        UserEntity userEntity = userDao.getUserByUserName(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }

        //delete existing auth details for the user in database.
        //userDao.deleteExistingAuthDetailsForUser(userEntity.getUuid());

        String encryptedPwd = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
        if (encryptedPwd.equals(userEntity.getPassword())) {
            JwtTokenProvider tokenProvider = new JwtTokenProvider(encryptedPwd);
            UserAuthEntity userAuthToken = new UserAuthEntity();
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(tokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setUuid(userEntity.getUuid());
            userDao.createAuthToken(userAuthToken);
            userAuthToken.setLogoutAt(null);
            return userAuthToken;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }
    }

    /**
     * method used for authenticating access token of user
     * if access token is not there that means user is not signed in.
     *
     * @param accessToken accessToken String to be authenticated
     * @return UserEntity Object
     * @throws SignOutRestrictedException thrown if user does not exist
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity authenticateAccessToken(final String accessToken) throws SignOutRestrictedException {

        UserAuthEntity userAuthEntity = userDao.getUserByAccessToken(accessToken);
        if (userAuthEntity == null) {
            throw new SignOutRestrictedException("SGR-001", "User is not Signed in");
        } else {
            final ZonedDateTime logoutAtDate = ZonedDateTime.now();
            userAuthEntity.setLogoutAt(logoutAtDate);
            userDao.updateLogOutDate(userAuthEntity);
            return userAuthEntity.getUser();
        }
    }


}
