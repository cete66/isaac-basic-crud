package people.login;

import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import people.login.adapter.persistence.LoginAdapter;
import people.login.adapter.persistence.dto.LoginEntity;
import people.login.adapter.rest.dto.LoginRequest;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.Optional;

@Component
@Transactional
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginAdapter loginAdapter;

    public LoginService(final BCryptPasswordEncoder bCryptPasswordEncoder,
                        final LoginAdapter loginAdapter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginAdapter = loginAdapter;
    }

    public void login(final LoginRequest loginRequest) throws NotFoundException {
        loginRequest.setPassword(loginRequest.getPassword().hashCode() + "");

        final Optional<LoginEntity> optLoginFound = loginAdapter.findByEmail(loginRequest.getEmail());

        if (optLoginFound.isEmpty()) {
            throw new NotFoundException("No matching login data found. Try again.");
        }

        final LoginEntity loginEntity = optLoginFound.get();

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), loginEntity.getPassword())) {
            throw new InvalidParameterException("Invalid login data. Try again.");
        }
    }
}
