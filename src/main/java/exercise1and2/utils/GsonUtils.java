package exercise1and2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exercise1and2.deserializer.GsonCampaignInputDeserializer;
import exercise1and2.deserializer.GsonCampaignInputSerializer;
import exercise1and2.deserializer.GsonPartnerInputDeserializer;
import exercise1and2.deserializer.GsonPartnerInputSerializer;
import exercise1and2.models.CampaignInput;
import exercise1and2.models.PartnerInput;

public class GsonUtils {

    public static Gson getCustomGsonCampaignInput() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CampaignInput.class, new GsonCampaignInputDeserializer());
        gsonBuilder.registerTypeAdapter(CampaignInput.class, new GsonCampaignInputSerializer());
        return gsonBuilder.create();
    }

    public static Gson getCustomGsonPartnerInput() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PartnerInput.class, new GsonPartnerInputDeserializer());
        gsonBuilder.registerTypeAdapter(PartnerInput.class, new GsonPartnerInputSerializer());
        return gsonBuilder.create();
    }
}
