package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties(ignoreUnknown=true)
public
class Temperature5Days {
    private MinMaxTemperature minimum;
    private MinMaxTemperature maximum;

    @JsonSetter("Minimum")
    public void setMinimum(MinMaxTemperature value) {
        this.minimum = value;
    }

    @JsonGetter("Minimum")
    public MinMaxTemperature getMinimum() {
        return this.minimum;
    }

    @JsonSetter("Maximum")
    public void setMaximum(MinMaxTemperature value) {
        this.maximum = value;
    }

    @JsonGetter("Maximum")
    public MinMaxTemperature getMaximum() {
        return this.maximum;
    }

    public Temperature5Days() {}
}
