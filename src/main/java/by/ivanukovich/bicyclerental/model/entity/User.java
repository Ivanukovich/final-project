package by.ivanukovich.bicyclerental.model.entity;

public class User {
    private long userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private UserRole role;
    private UserStatus userStatus;
    boolean isBlocked;

    public User() {
    }

    public User(long userId, String firstName, String lastName, String login, String password, String email,
                UserRole role, UserStatus userStatus) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.userStatus = userStatus;
        this.isBlocked = false;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private User user = new User();

        public Builder userId(long userId) {
            user.userId = userId;
            return this;
        }

        public Builder login(String login) {
            user.login = login;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder role(UserRole role) {
            user.role = role;
            return this;
        }

        public Builder userStatus(UserStatus userStatus) {
            user.userStatus = userStatus;
            return this;
        }

        public Builder isBlocked(boolean isBlocked) {
            user.isBlocked = isBlocked;
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;
        return userId == user.userId &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                role == user.role &&
                userStatus == user.userStatus &&
                isBlocked == user.isBlocked;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (isBlocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("User {");
        result .append("userId: ").append(userId);
        result .append(", firstName: ").append(firstName);
        result .append(", lastName: ").append(lastName);
        result .append(", login: ").append(login);
        result .append(", email: ").append(email);
        result .append(", role: ").append(role);
        result .append(", userStatus: ").append(userStatus);
        if (isBlocked()){
            result .append(", blocked").append(userStatus);
        }
        result.append('}');
        return result.toString();
    }
}
