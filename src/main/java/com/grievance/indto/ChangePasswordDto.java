package com.grievance.indto;


/**
 * Data Transfer Object for changing passwords.
 */
public final class ChangePasswordDto {

    /** The user's old password. */
    private String oldPassword;

    /** The user's new password. */
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "password not valid")
    private String newPassword;

    /**
     * Retrieves the old password.
     *
     * @return The old password.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the old password.
     *
     * @param oldPwd The old password to set.
     */
    public void setOldPassword(final String oldPwd) {
        this.oldPassword = oldPwd;
    }

    /**
     * Retrieves the new password.
     *
     * @return The new password.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPwd The new password to set.
     */
    public void setNewPassword(final String newPwd) {
        this.newPassword = newPwd;
    }

}
