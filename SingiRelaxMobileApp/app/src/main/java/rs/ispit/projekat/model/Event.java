package rs.ispit.projekat.model;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuppressLint("SimpleDateFormat")
public class Event {

    private Integer id;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private Double rating;
    private String location;
    private EventType eventType;
    private String description;
    private Integer attendance;
    private static List<Event> events;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    //parsiranje iz JSON u domenske objekte
    //parsiranje jednog objekta
    public static Event parseJSONObject(JSONObject object) {
        Event event = new Event();
        try {
            if (object.has("name")) {
                event.setName(object.getString("name"));
            }
            if (object.has("attendance")) {
                event.setAttendance(object.getInt("attendance"));
            }
            if (object.has("id")) {
                event.setId(object.getInt("id"));
            }
            if (object.has("location")) {
                event.setLocation(object.getString("location"));
            }
            if (object.has("rating")) {
                event.setRating(object.getDouble("rating"));
            }
            if (object.has("eventType")) {
                event.setEventType(EventType.valueOf(object.getString("eventType")));
            }
            if (object.has("dateFrom")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                event.setDateFrom(format.parse(object.getString("dateFrom")));
            }
            if (object.has("dateTo")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                event.setDateTo(format.parse(object.getString("dateTo")));
            }
            if (object.has("description")) {
                event.setDescription(object.getString("description"));
            }
        } catch (Exception ignored) {

        }
        return event;
    }

    //parsiranje liste
    public static List<Event> parseJSONArray(JSONArray array) {
        List<Event> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                Event event = parseJSONObject(array.getJSONObject(i));
                list.add(event);
            }
        } catch (Exception ignored) {

        }
        return list;
    }

    public static List<Event> getEvents() {
        return events;
    }

    public static void setEvents(List<Event> events) {
        Event.events = events;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return "{" +
                "\"id\":" + id +
                ",\"name\":\""  + name +'\"' +
                ",\"dateFrom\":\""+format.format(dateFrom)+'\"' +
                ",\"dateTo\":\""+format.format(dateTo)+'\"' +
                ",\"rating\":" + rating +
                ",\"location\": \""+ location + '\"' +
                ",\"eventType\":\"" + eventType.toString() + '\"' +
                ",\"description\":\""+ description + '\"' +
                ",\"attendance\":" + attendance +
                "}";
    }

}
