package uk.tw.energy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.tw.energy.error.Global400Error;
import uk.tw.energy.error.NoSuchSmartMeterExtsit;
import uk.tw.energy.service.CostCalculationReadingService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cost")
public class CostCalculationReadingController {

    @Autowired
    private CostCalculationReadingService costCalculationReadingService;
    CostCalculationReadingController(CostCalculationReadingService costCalculationReadingService ){
        this.costCalculationReadingService=costCalculationReadingService;
    }

    @GetMapping("/reading/{smartMeterId}")
    public ResponseEntity getLastWeenElectricityConsumption(@PathVariable String smartMeterId){
        BigDecimal amount = costCalculationReadingService.getLastWeekReadingCost(smartMeterId);
        if(amount.equals(BigDecimal.valueOf(-1))){
            throw new NoSuchSmartMeterExtsit("reading is null");
        }

        if(smartMeterId==null || smartMeterId.trim().isEmpty()){
            throw new Global400Error("Missing input parameter");
        }

        return ResponseEntity.ok(amount);
    }

}
