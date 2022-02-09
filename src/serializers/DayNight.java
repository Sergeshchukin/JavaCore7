package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class DayNight {
    private String iconPhrase = null;

    @JsonSetter("IconPhrase")
    public void seIconPhrase(String value) {
        this.iconPhrase = value;
    }

    @JsonGetter("IconPhrase")
    public String getIconPhrase() {
        return this.iconPhrase;
    }

    public DayNight() {}
}
