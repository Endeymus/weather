package com.endeymus.weather;

import com.endeymus.weather.entities.User;
import com.endeymus.weather.repositories.UserRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@SpringBootTest
@Component
class WeatherApplicationTests {

	private final String ACCESS_KEY = "PCVx93qlGOtJlZIwpwDj";

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testMqtt() {
//		String topic = "MQTT topic";
		String topic = String.format("api/v1/%s/attributes?clientKeys=temperature", ACCESS_KEY);

		String content = "{\"temperature\": 2}";
		int qos = 2;
//		String broker = "tcp://mqtt.eclipse.org:1883";
		String broker = "tcp://demo.thingsboard.io";
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");

			System.out.println("setCallback");

			sampleClient.setCallback(new MqttCallback() {

				@Override
				public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) {
					System.out.println("---message arrive");
					System.out.println(topic + ": " + new String(message.getPayload(), Charset.defaultCharset()));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
				}
			});


			sampleClient.subscribe(topic);
			System.out.println("subscribe");

			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
//			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");

			sampleClient.disconnect();
			System.out.println("Disconnected");
//			System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	@Test
	@Transactional
	void testRoles() {
		User user = userRepository.findUserById(2);
		user.getRoles().forEach(System.out::println);
	}
}
