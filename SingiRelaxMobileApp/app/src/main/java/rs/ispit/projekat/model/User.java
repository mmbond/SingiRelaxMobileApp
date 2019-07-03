package rs.ispit.projekat.model;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressLint("SimpleDateFormat")
public class User {

    private static Integer id;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String password;
    private static String phone;
    private static String address;
    private static Date registrationDate;
    private static String interests;
    private static List<Event> events = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String phone, String address, Date registrationDate, String interests) {
        User.firstName = firstName;
        User.lastName = lastName;
        User.email = email;
        User.password = password;
        User.phone = phone;
        User.address = address;
        User.registrationDate = registrationDate;
        User.interests = interests;
    }

    public static Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        User.id = id;
    }

    public static String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        User.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        User.lastName = lastName;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public static List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        User.events = events;
    }

    public static String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        User.phone = phone;
    }

    public static String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        User.address = address;
    }

    public static Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        User.registrationDate = registrationDate;
    }

    public static String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        User.interests = interests;
    }

    //parsiranje iz JSON u domenske objekte
    //parsiranje jednog objekta
    public static User parseJSONObject(JSONObject object) {
        User user = new User();
        try {
            if (object.has("id")) {
                user.setId(object.getInt("id"));
            }
            if (object.has("firstName")) {
                user.setFirstName(object.getString("firstName"));
            }
            if (object.has("lastName")) {
                user.setLastName(object.getString("lastName"));
            }
            if (object.has("email")) {
                user.setEmail(object.getString("email"));
            }
            if (object.has("password")) {
                user.setPassword(object.getString("password"));
            }
            if (object.has("phone")) {
                user.setPhone(object.getString("phone"));
            }
            if (object.has("address")) {
                user.setAddress(object.getString("address"));
            }
            if (object.has("interests")) {
                user.setInterests(object.getString("interests"));
            }
            if (object.has("registrationDate")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                user.setRegistrationDate(format.parse(object.getString("registrationDate")));
            }
            if (object.has("events")) {
                user.setEvents(Event.parseJSONArray(object.getJSONArray("events")));
            }

        } catch (Exception ignored) {

        }
        return user;
    }

    //parsiranje liste
    public static List<User> parseJSONArray(JSONArray array) {
        List<User> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                User user = parseJSONObject(array.getJSONObject(i));
                list.add(user);
            }
        } catch (Exception ignored) {

        }
        return list;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return "{" +
                "\"id\":" + id +
                ",\"address\":\""+ address +'\"' +
                ",\"email\":\""+ email +'\"' +
                ",\"firstName\":\""+ firstName +'\"' +
                ",\"interests\":\""+ interests +'\"' +
                ",\"lastName\":\""+ lastName +'\"' +
                ",\"password\":\""+ password +'\"' +
                ",\"phone\":\""+ phone +'\"' +
                ",\"registrationDate\":\""+format.format(registrationDate)+'\"' +
                ",\"events\":"+ events +
                ",\"userType\":\"POSETILAC"+
                "\"}";
    }
}
