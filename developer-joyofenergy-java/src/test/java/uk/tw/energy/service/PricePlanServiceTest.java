package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.tw.energy.domain.ElectricityReading;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PricePlanServiceTest {

    private static final String SMART_METER_ID = "01010101";

    @Mock
    private PricePlanService pricePlanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenSmartMeterIdThatExistsShouldReturnMeterReadings() {
        List<ElectricityReading> electricityReadingList = new ArrayList<>();
        electricityReadingList.add(new ElectricityReading(Instant.now(), BigDecimal.TEN));
        when(pricePlanService.perMeterElectricityReadings(SMART_METER_ID)).thenReturn(electricityReadingList);
        assertThat(pricePlanService.perMeterElectricityReadings(SMART_METER_ID)).isEqualTo(electricityReadingList);
    }

}
