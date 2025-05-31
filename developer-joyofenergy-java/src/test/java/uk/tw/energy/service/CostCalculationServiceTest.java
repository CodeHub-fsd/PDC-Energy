package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.domain.ElectricityReading;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.setMaxLengthForSingleLineDescription;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CostCalculationServiceTest {
    private final String SMART_METER_ID="013343455";

    @Mock
    private CostCalculationService costCalculationService;
    @Mock
    private CostCalculationService costSev;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void givenSmartMeterShouldReturnLastWeekElectricityConsumptionCost(){
        List<ElectricityReading> electricityReadingList=new ArrayList<>();
        when(costCalculationService.getLastWeekElectricityConsumptionCost(SMART_METER_ID)).thenReturn(BigDecimal.valueOf(101.01));
        assertThat(costCalculationService.getLastWeekElectricityConsumptionCost(SMART_METER_ID)).isEqualTo(BigDecimal.valueOf(101.01));


    }


}


