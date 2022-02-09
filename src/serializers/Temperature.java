package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Temperature {
    private Metric metric;

    @JsonGetter("Metric")
    public Metric getMetric() {
        return this.metric;
    }

    @JsonSetter("Metric")
    public void setMetric(Metric value) {
        this.metric = value;
    }
}