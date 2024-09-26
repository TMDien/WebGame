package com.web.game.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisUser {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    public RegisUser() {
    }

    public RegisUser(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull(message = "is required") @Size(min = 1, message = "is required") String userName) {
        this.userName = userName;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "is required") @Size(min = 1, message = "is required") String password) {
        this.password = password;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "is required") @Size(min = 1, message = "is required") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") String email) {
        this.email = email;
    }
}
