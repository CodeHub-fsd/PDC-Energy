package uk.tw.energy.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import uk.tw.energy.domain.ElectricityReading;

import static org.mockito.Mockito.*;

public class MeterReadingServiceTest {

    private MeterReadingService meterReadingService;
    private final String SMART_METER_ID = "0101010";

    private Map<String, List<ElectricityReading>> meterAssociatedReadings;

    @BeforeEach
    public void setUp() {
        meterAssociatedReadings = new HashMap<>();
        meterReadingService = new MeterReadingService(meterAssociatedReadings);
    }
    //modified code here
    @Test
    public void givenMeterIdThatDoesNotExistShouldReturnNull() {
        assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.empty());
    }

        @Test
    public void givenMeterReadingThatExistsShouldReturnMeterReadings() {
        meterReadingService.storeReadings("random-id", new ArrayList<>());
        assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.of(new ArrayList<>()));
    }
}
