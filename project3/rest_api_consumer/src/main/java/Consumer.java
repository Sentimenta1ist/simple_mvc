import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        Random r = new Random();
        String url, response;

        /*
        creating of entities
         */
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();

        /*
        add sensor request
         */

        sensor.setName("name1");
        HttpEntity<Sensor> addSensorRequest = new HttpEntity<>(sensor);
        url = "http://localhost:8080/sensors/registration";
        response = restTemplate.postForObject(url, addSensorRequest, String.class);
        System.out.println(response);


        /*
        add 1000 measurements
         */
        measurement.setSensor(sensor);
        url = "http://localhost:8080/measurements/add";
        for(int i = 0 ; i < 1000; i++){
            measurement.setValue(-0.1f*i + r.nextFloat() * (0.2f*i));
            measurement.setRaining(Math.random() < 0.5);

            HttpEntity<Measurement> measurementHttpEntity = new HttpEntity<>(measurement);
            response = restTemplate.postForObject(url, measurementHttpEntity, String.class);
            System.out.println(i + " : " + response);
        }


        /*
        get 1000 measurements
         */
        url = "http://localhost:8080/measurements/all";
        response = restTemplate.getForObject(url, String.class);

        List<Measurement> rootNode = mapper.readValue(response, new TypeReference<>(){});



        /*
        display
         */
        List<Integer> xData = new ArrayList<>();
        List<Float> yData = new ArrayList<>();
        int i = 0;
        for(Measurement m : rootNode){
            xData.add(i);
            yData.add(m.value);
            System.out.println(i++ + " : " + m);
        }

        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
