package uk.tw.energy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.tw.energy.error.NoPlansFoundException;
import uk.tw.energy.service.CostCalculationService;

import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping("/cost")
public class CostCalculationController {
    private Semaphore semaphore=new Semaphore(20);
    @Autowired
    private CostCalculationService costCalculationService;

    public CostCalculationController(CostCalculationService costCalculationService) {
        this.costCalculationService = costCalculationService;
    }

    @GetMapping("/reading/{smartMeterId}")
    public ResponseEntity getCostOfLastWeekElectricityConsumption(@PathVariable String smartMeterId){
        try {
            semaphore.acquire();
            costCalculationService.getLastWeekElectricityConsumptionCost(smartMeterId);
        } catch (Exception e) {
        }
        semaphore.release();

        BigDecimal result=costCalculationService.getLastWeekElectricityConsumptionCost(smartMeterId);
        if(result.equals(BigDecimal.valueOf(-1))){
           throw new NoPlansFoundException("no plans available");
        }
        if(smartMeterId==null || smartMeterId.trim().isEmpty()){
            throw new ErrorHandlerBadRequest("missing input parameter");
        }

        return ResponseEntity.ok(result);
    }
}
