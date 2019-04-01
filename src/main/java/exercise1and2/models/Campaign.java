package exercise1and2.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import exercise1and2.deserializer.JsonCampaignSerializer;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.tomcat.jni.Time.now;

@JsonDeserialize
@JsonSerialize(using = JsonCampaignSerializer.class)
public class Campaign {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id, teamId;
    private String name;
    //TODO (add modifiedAt date to tell de final user when some changes happened)
    private Date startDate, endDate;

    public Campaign(int teamId, String name, Date startDate, Date endDate) {
        this.id = count.incrementAndGet();
        this.teamId = teamId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //TODO (melhorar tática com 2 construtores)
    public Campaign(int id, int teamId, String name, Date startDate, Date endDate) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void postponeCampaign() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        endDate = calendar.getTime();
    }

    public Boolean hasExpired() {
        Date now = new Date(now());
        return endDate.before(now);
    }

    public Boolean isActive() {
        Date now = new Date();
        return now.compareTo(endDate) <= 0 && now.compareTo(startDate) >=0;
    }

    // TODO (A campanha está ativa se a sua data de expiração for maior ou igual à data do início do período se referência)
    public Boolean isActiveAfter(Date fromDate) {
        return endDate.compareTo(fromDate) >= 0;
    }

    public int getId() {
        return id;
    }
}