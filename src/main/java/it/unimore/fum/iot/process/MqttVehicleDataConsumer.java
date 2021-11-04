package it.unimore.fum.iot.process;

import it.unimore.fum.iot.conf.MqttConfigurationParameters;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project java-mqtt-laboratory
 * @created 27/10/2021 - 14:43
 */
public class MqttVehicleDataConsumer {

    public static void main(String [ ] args) {

       System.out.println("MQTT Auth Consumer Tester Started ...");

        try{

            //Generate a random MQTT client ID using the UUID class
            String clientId = UUID.randomUUID().toString();

            //Represents a persistent data store, used to store outbound and inbound messages while they
            //are in flight, enabling delivery to the QoS specified. In that case use a memory persistence.
            //When the application stops all the temporary data will be deleted.
            MqttClientPersistence persistence = new MemoryPersistence();

            //The the persistence is not passed to the constructor the default file persistence is used.
            //In case of a file-based storage the same MQTT client UUID should be used
            IMqttClient client = new MqttClient(
                    String.format("tcp://%s:%d", MqttConfigurationParameters.BROKER_ADDRESS, MqttConfigurationParameters.BROKER_PORT),
                    clientId,
                    persistence);

            //Define MQTT Connection Options such as reconnection, persistent/clean session and connection timeout
            //Authentication option can be added -> See AuthProducer example
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MqttConfigurationParameters.MQTT_USERNAME);
            options.setPassword(new String(MqttConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            //Connect to the target broker
            client.connect(options);

            System.out.println("Connected !");

            //Subscribe to Vehicle Info (Retained Data) -
            //Topic Structure: /iot/user/<user_id>/vehicle/<vehicle_id>/info
            String vehicleInfoTopic = String.format("%s/%s/+/%s",
                    MqttConfigurationParameters.MQTT_BASIC_TOPIC,
                    MqttConfigurationParameters.VEHICLE_TOPIC,
                    MqttConfigurationParameters.VEHICLE_INFO_TOPIC);

            client.subscribe(vehicleInfoTopic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    byte[] payload = message.getPayload();
                    System.out.println("Message Received ("+topic+") Message Received: " + new String(payload));
                }
            });

            //Subscribe to Vehicle Telemetry Data
            //Topic Structure: /iot/user/<user_id>/vehicle/<vehicle_id>/telemetry
            String vehicleTelemetryTopic = String.format("%s/%s/+/%s",
                    MqttConfigurationParameters.MQTT_BASIC_TOPIC,
                    MqttConfigurationParameters.VEHICLE_TOPIC,
                    MqttConfigurationParameters.VEHICLE_TELEMETRY_TOPIC);

            //Subscribe to Vehicle Telemetry Data
            client.subscribe(vehicleTelemetryTopic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    byte[] payload = message.getPayload();
                    System.out.println("Message Received ("+topic+") Message Received: " + new String(payload));
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
