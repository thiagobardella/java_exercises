package exercise1and2.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.SerializedName;
import exercise1and2.deserializer.JsonPartnerInputDeserializer;

import java.util.Date;

@JsonDeserialize(using = JsonPartnerInputDeserializer.class)
public class PartnerInput {

    @SerializedName("team_id")
    private int teamId;

    @SerializedName("full_name")
    private String fullName;

    private String email;

    @SerializedName("birth_date")
    private Date birthDate;


    public PartnerInput(int teamId, String fullName, String email, Date birthDate) {
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
}
