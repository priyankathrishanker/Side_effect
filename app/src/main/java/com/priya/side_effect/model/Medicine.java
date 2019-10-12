
package com.priya.side_effect.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("reaction")
    @Expose
    private List<Reaction> reaction = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reaction> getReaction() {
        return reaction;
    }

    public void setReaction(List<Reaction> reaction) {
        this.reaction = reaction;
    }

}
