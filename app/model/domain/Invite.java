package model.domain;

import java.io.Serializable;

/**
 * Created by volodymyrd on 22.10.15.
 */
public class Invite implements Serializable {
    private String fromUser;
    private String toUser;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "Invite{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                '}';
    }
}
