package exercise1and2.models;

import java.util.List;

public class PartnerCampaign {

    private Partner partner;
    private List<Campaign> campaigns;

    public PartnerCampaign(Partner partner, List<Campaign> campaigns) {
        this.partner = partner;
        this.campaigns = campaigns;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }
}
