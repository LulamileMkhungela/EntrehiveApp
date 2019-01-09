package com.example.academy_intern.sampledesign.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventProfile
{
    @SerializedName("eventId")
    @Expose
    private int eventId;

    @SerializedName("activeEvent")
    @Expose
    private boolean activeEvent;

    @SerializedName("eventTitle")
    @Expose
    private String eventTitle;

    @SerializedName("eventDateTime")
    @Expose
    private String eventDateTime;

    @SerializedName("eventDescription")
    @Expose
    private String eventDescription;

    @SerializedName("eventLocation")
    @Expose
    private String eventLocation;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("attendancePoints")
    @Expose
    private int attendancePoints;

    @SerializedName("eventHistoryList")
    @Expose
    private ArrayList<EventProfile> historyOfEvents;

    public EventProfile() {

    }

    public EventProfile(ArrayList <EventProfile> historyOfEvents)
    {
        this.historyOfEvents = historyOfEvents;
    }

    public EventProfile(String eventTitle, String eventDateTime, String eventDescription) {
        this.eventTitle = eventTitle;
        this.eventDateTime = eventDateTime;
        this.eventDescription = eventDescription;
    }

    public EventProfile(String eventTitle, String eventDescription, String eventDateTime, String eventLocation) {

        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.eventLocation = eventLocation;
        this.activeEvent = false;
    }

    public boolean isActiveEvent() {
        return activeEvent;
    }

    public void setActiveEvent(boolean activeEvent) {
        this.activeEvent = activeEvent;
    }
    public String getEventDateTime() {
        return eventDateTime;
    }
    public String setEventDate(String eventDate) {
        return eventDate;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAttendancePoints() {
        return attendancePoints;
    }
    public void setAttendancePoints(int attendancePoints) {
        this.attendancePoints = attendancePoints;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<EventProfile> getHistoryOfEvents()
    {
        return historyOfEvents;
    }

    public void setHistoryOfEvents(ArrayList<EventProfile> historyOfEvents)
    {
        this.historyOfEvents = historyOfEvents;
    }
}