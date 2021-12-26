package zhami.com;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import zhami.com.config.MyServletV2;

import java.util.HashMap;
import java.util.Map;

public class SpringMVCApplication {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String kafkaServer="localhost:9092";
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        ProducerFactory<Long, String> producerFactory = new DefaultKafkaProducerFactory<>(props);

        KafkaTemplate<Long, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        Context ctx = tomcat.addContext("", null);
        Wrapper servlet = Tomcat.addServlet(ctx, "myServlet", new MyServletV2(kafkaTemplate));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

}
