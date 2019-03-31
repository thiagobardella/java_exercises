package exercise1.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import exercise1.deserializer.JsonCampaignInputDeserializer;
import exercise1.deserializer.JsonCampaignInputSerializer;

import java.util.Date;

@JsonSerialize(using = JsonCampaignInputSerializer.class)
@JsonDeserialize(using = JsonCampaignInputDeserializer.class)
public class CampaignInput {

    @SerializedName("team_id")
    private int teamId;

    private String name;

    @SerializedName("start_date")
    private Date startDate;

    @SerializedName("end_date")
    private Date endDate;

    //TODO (validate input dates - ex.: startDate must be less than or equals endDate)
    public CampaignInput(int teamId, String name, Date startDate, Date endDate) {
        this.teamId = teamId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

