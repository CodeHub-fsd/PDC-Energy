package uk.tw.energy.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CostCalculationReadingService {

    private MeterReadingService meterReadingService;
    CostCalculationReadingService(MeterReadingService meterReadingService){
        this.meterReadingService=meterReadingService;
    }

    public BigDecimal getLastWeekReadingCost(String smartMeterId){
        return BigDecimal.TEN;
    }

}
