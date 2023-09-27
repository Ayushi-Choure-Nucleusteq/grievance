package com.grievance.indto;

import javax.persistence.Column;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Data transfer object for Login.
 */
public final class LoginDto {

    /**
     * Email address of the member.
     * Expected to be in a valid email format.
     */
    @Email
    @Column(unique = true)
    @NotEmpty(message = "Email is required")
    private String email;

    /**
     * Password for the member.
     * Expected to have a minimum length of 8 characters.
     */
    @NotEmpty(message = "Password is required")
    private String password;

    /**
     * Gets the email address.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param emailParam the email address
     */
    public void setEmail(final String emailParam) {
        this.email = emailParam;
    }

    /**
     * Gets the password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param passwordParam the password
     */
    public void setPassword(final String passwordParam) {
        this.password = passwordParam;
    }

    /**
     * Returns the string representation of LoginDto.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Login [email=" + email + ", password=" + password + "]";
    }
}
