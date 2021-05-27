package people.register;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import people.login.adapter.persistence.LoginAdapter;
import people.login.adapter.persistence.dto.LoginEntity;
import people.register.adapter.rest.dto.RegisterRequest;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;

@Component
@Transactional
public class RegisterService {

    private final LoginAdapter loginAdapter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterService(final LoginAdapter loginAdapter,
                           final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.loginAdapter = loginAdapter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public RegisterRequest register(final RegisterRequest registerRequest) {

        validateRegister(registerRequest);
        secureData(registerRequest);

        loginAdapter.persist(new LoginEntity(registerRequest.getEmail(), registerRequest.getPassword()));

        return registerRequest;
    }

    private void validateRegister(final RegisterRequest registerRequest) {
        if (loginAdapter.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new InvalidParameterException(
                "Email already in use. Try another one or login with it.");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getPasswordRetyped())) {
            throw new InvalidParameterException("Passwords does not match.");
        }
    }

    private void secureData(final RegisterRequest registerRequest) {
        registerRequest.setPassword(secureString(registerRequest.getPassword()));
        registerRequest.setPasswordRetyped(secureString(registerRequest.getPasswordRetyped()));
    }

    private String secureString(final String password) {
        return bCryptPasswordEncoder.encode(password.hashCode() + "");
    }
}
