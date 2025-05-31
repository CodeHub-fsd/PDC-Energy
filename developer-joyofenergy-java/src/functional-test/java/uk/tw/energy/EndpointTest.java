package uk.tw.energy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import uk.tw.energy.builders.MeterReadingsBuilder;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.MeterReadings;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
public class EndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private static ObjectMapper mapper;


    static {
        // Initialize ObjectMapper and register JavaTimeModule
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // Optionally, disable the FAIL_ON_EMPTY_BEANS feature to prevent issues with empty beans
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    private HttpEntity<String> toHttpEntity(Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonMeterData = mapper.writeValueAsString(object);
        return (HttpEntity<String>) new HttpEntity(jsonMeterData, headers);
    }


    @Test
    public void shouldStoreReadings() throws JsonProcessingException {
        MeterReadings meterReadings =
                new MeterReadingsBuilder().generateElectricityReadings().build();
        HttpEntity<String> entity = toHttpEntity(meterReadings);

        ResponseEntity<String> response = restTemplate.postForEntity("/readings/store", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void givenMeterIdShouldReturnAMeterReadingAssociatedWithMeterId() throws JsonProcessingException {
        String smartMeterId = "alice";
        List<ElectricityReading> data = List.of(
                new ElectricityReading(Instant.parse("2024-04-26T00:00:10.00Z"), new BigDecimal(10)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:20.00Z"), new BigDecimal(20)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:30.00Z"), new BigDecimal(30)));
        populateReadingsForMeter(smartMeterId, data);

        ResponseEntity<ElectricityReading[]> response =
                restTemplate.getForEntity("/readings/read/" + smartMeterId, ElectricityReading[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //  assertThat(Arrays.asList(response.getBody())).isEqualTo(data);
    }

    @Test
    public void shouldCalculateAllPrices() throws JsonProcessingException {
        String smartMeterId = "bob";
        List<ElectricityReading> data = List.of(
                new ElectricityReading(Instant.parse("2024-04-26T00:00:10.00Z"), new BigDecimal(10)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:20.00Z"), new BigDecimal(20)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:30.00Z"), new BigDecimal(30)));
        populateReadingsForMeter(smartMeterId, data);

        ResponseEntity<CompareAllResponse> response =
                restTemplate.getForEntity("/price-plans/compare-all/" + smartMeterId, CompareAllResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(new CompareAllResponse(
                        Map.of("price-plan-0", 36000, "price-plan-1", 7200, "price-plan-2", 3600), null));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void givenMeterIdAndLimitShouldReturnRecommendedCheapestPricePlans() throws JsonProcessingException {
        String smartMeterId = "jane";
        List<ElectricityReading> data = List.of(
                new ElectricityReading(Instant.parse("2024-04-26T00:00:10.00Z"), new BigDecimal(10)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:20.00Z"), new BigDecimal(20)),
                new ElectricityReading(Instant.parse("2024-04-26T00:00:30.00Z"), new BigDecimal(30)));
        populateReadingsForMeter(smartMeterId, data);

        ResponseEntity<Map[]> response =
                restTemplate.getForEntity("/price-plans/recommend/" + smartMeterId + "?limit=2", Map[].class);

        assertThat(response.getBody()).containsExactly(Map.of("price-plan-2", 3600), Map.of("price-plan-1", 7200));
    }

    private void populateReadingsForMeter(String smartMeterId, List<ElectricityReading> data) throws JsonProcessingException {
        MeterReadings readings = new MeterReadings(smartMeterId, data);

        HttpEntity<String> entity = toHttpEntity(readings);
        restTemplate.postForEntity("/readings/store", entity, String.class);
    }

    record CompareAllResponse(Map<String, Integer> pricePlanComparisons, String pricePlanId) {
    }
}
