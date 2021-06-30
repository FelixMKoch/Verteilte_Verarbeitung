package de.thro.vv.studienarbeit.model;

import com.google.gson.*;
import de.thro.vv.studienarbeit.automat.Automat;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A class that represents the data sent to and received from the client.
 */
public class Message {

    private Automat.Symbol type;
    private String payload;

    public Message(Automat.Symbol type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * If there is already a Message-String in Json form this method can be used to build an object from the Json-String
     * @param init THe Json-String this object should be made of
     */
    public Message(String init) {
        fromString(init);
    }

    public Automat.Symbol getType() {
        return type;
    }

    public void setType(Automat.Symbol type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Changes this type and payload according to the Json-String given in the argument
     * @param from A Json-String telling what this object should look like
     */
    private void fromString(String from) {
        Gson gson = new GsonBuilder().create();
        JsonObject object = gson.fromJson(from, JsonObject.class);
        this.payload = gson.toJson(object.get("payload").getAsJsonObject());
        this.type = Automat.Symbol.valueOf(object.get("type").getAsString());
    }

    /**
     * Gives back the Json String of this message
     * @return Json String of this object
     */
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        JsonObject object = new JsonObject();
        object.addProperty("type", type.name());
        object.add("payload", gson.fromJson(payload, JsonObject.class));
        return gson.toJson(object);
    }


    /**
     * Two Message objects are equal if their type and their payloads are the same
     * @param o the object to compare
     * @return If the two Meassages are equal ones
     */
    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(Message.class != o.getClass()) return false;
        return this.payload.equals(((Message) o).payload) && this.type == ((Message) o).type;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(payload)
                .toHashCode();
    }
}
