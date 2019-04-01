package exercise1and2.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import exercise1and2.deserializer.JsonCampaignSerializer;
import exercise1and2.deserializer.JsonPartnerSerializer;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@JsonSerialize(using = JsonPartnerSerializer.class)
public class Partner {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private int teamId;

    private String fullName;

    private String email;

    private Date birthDate;

    public Partner(int teamId, String fullName, String email, Date birthDate) {
        this.id = count.incrementAndGet();
        this.teamId = teamId;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getId() {
        return id;
    }
}
