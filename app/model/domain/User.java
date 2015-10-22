package model.domain;

/**
 * Created by volodymyrd on 21.10.15.
 */
public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
