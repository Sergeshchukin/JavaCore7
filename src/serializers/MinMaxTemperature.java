package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class MinMaxTemperature {
    private double value;
    private String unit;

    @JsonSetter("Value")
    public void setValue(double value) {
        this.value = value;
    }

    @JsonGetter("Value")
    public double getValue() {
        return this.value;
    }

    @JsonSetter("Unit")
    public void setUnit(String value) {
        this.unit = value;
    }

    @JsonGetter("Unit")
    public String getUnit() {
        return this.unit;
    }

    public MinMaxTemperature() {}
}