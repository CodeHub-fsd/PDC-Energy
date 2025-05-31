package uk.tw.energy.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.error.NoPlansFoundException;

@Service
public class MeterReadingService {

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingService(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        return Optional.ofNullable(meterAssociatedReadings.get(smartMeterId));
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
      if (!meterAssociatedReadings.containsKey(smartMeterId)) {
            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
       }

        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);
    }

    public Optional<List<ElectricityReading>> getLastWeekElectricityReading(String smartMeterId){
        List<ElectricityReading> electricityReadingList = meterAssociatedReadings.get(smartMeterId);
        if(electricityReadingList==null){
           throw new NoPlansFoundException("electricity reading is null");
        }

        List<ElectricityReading> result=new ArrayList<>();
        Instant  lastWeek=Instant.now().minus(Duration.ofDays(7));
        for(ElectricityReading reading:electricityReadingList){
            if(reading.getTime().isAfter(lastWeek))
                result.add(reading);
        }
        return Optional.of(result);

    }

}
