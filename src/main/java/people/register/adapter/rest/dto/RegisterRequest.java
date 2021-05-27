package people.register.adapter.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class RegisterRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordRetyped;

    public RegisterRequest() {
        //deserialize
    }

    public RegisterRequest(final String email,
                           final String password,
                           final String passwordRetyped) {
        this.email = email;
        this.password = password;
        this.passwordRetyped = passwordRetyped;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordRetyped() {
        return passwordRetyped;
    }

    public void setPasswordRetyped(final String passwordRetyped) {
        this.passwordRetyped = passwordRetyped;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getPasswordRetyped(), that.getPasswordRetyped());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getPasswordRetyped());
    }

}
