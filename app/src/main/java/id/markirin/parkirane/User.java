package id.markirin.parkirane;

/**
 * Created by faldyikhwanfadila on 7/14/17.
 */

public class User {
    private String uid;
    private String name;
    private String email;
    private String booking;

    public User(String uid, String name, String email, String booking) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.booking = booking;
    }

    public User() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
