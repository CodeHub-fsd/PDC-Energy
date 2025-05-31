package uk.tw.energy.domain;

import java.util.List;

public class MeterReadings {
    private String smartMeterId;
    private List<ElectricityReading> electricityReadings;
    public MeterReadings(){
    }
    public MeterReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        this.smartMeterId=smartMeterId;
        this.electricityReadings=electricityReadings;
    }

    public String getSmartMeterId(){
        return smartMeterId;
    }

    public List<ElectricityReading> getElectricityReadings() {
        return electricityReadings;
    }
}
