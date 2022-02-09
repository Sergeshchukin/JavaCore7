package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Metric {
    private double value;
    private String unit;

    @JsonGetter("Value")
    public double getValue() {
        return this.value;
    }

    @JsonSetter("Value")
    public void setValue(double value) {
        this.value = value;
    }

    @JsonGetter("Unit")
    public String getUnit() {
        return this.unit;
    }

    @JsonSetter("Unit")
    public void setUnit(String value) {
        this.unit = value;
    }

    public Metric() {}
}