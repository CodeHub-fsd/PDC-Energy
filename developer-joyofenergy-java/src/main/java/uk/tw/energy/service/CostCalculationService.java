package uk.tw.energy.service;


import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostCalculationService {

    private MeterReadingService meterReadingService;
    private PricePlanService pricePlanService;
    private final List<PricePlan> pricePlans;
    private AccountService accountService;

    public CostCalculationService(MeterReadingService meterReadingService, PricePlanService pricePlanService,
                                  List<PricePlan> pricePlans,AccountService accountService) {
        this.meterReadingService = meterReadingService;
        this.pricePlanService = pricePlanService;
        this.pricePlans = pricePlans;
        this.accountService=accountService;
    }


    public BigDecimal getLastWeekElectricityConsumptionCost(String smartMeterId){
        Optional<List<ElectricityReading>> electricityReadingList=meterReadingService.getLastWeekElectricityReading(smartMeterId);
        String planName=accountService.getPricePlanIdForSmartMeterId(smartMeterId);
      Optional<PricePlan>  plans= pricePlans.stream().filter(plan->pricePlans.get(0).getPlanName().equalsIgnoreCase(plan.getPlanName())).findFirst();
       if(plans.isPresent())
//      for(PricePlan plans:pricePlans){
//          if(plans.getPlanName()==planName){
             return pricePlanService.calculateCost(electricityReadingList.get(),plans.get());
//          }
//        }
        return BigDecimal.valueOf(-1);
    }
}
