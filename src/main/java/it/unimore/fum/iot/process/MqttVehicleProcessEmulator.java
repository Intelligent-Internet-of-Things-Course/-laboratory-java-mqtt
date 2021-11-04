package it.unimore.fum.iot.process;

import com.google.gson.Gson;
import it.unimore.fum.iot.conf.MqttConfigurationParameters;
import it.unimore.fum.iot.model.ElectricVehicleTelemetryData;
import it.unimore.fum.iot.model.VehicleDescriptor;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project java-mqtt-laboratory
 * @created 27/10/2021 - 14:09
 */
public class MqttVehicleProcessEmulator {

    private static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) {

        System.out.println("VehicleEmulator started ...");

        try{

            String vehicleId = String.format("vehicle-%s", MqttConfigurationParameters.MQTT_USERNAME);

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient mqttClient = new MqttClient(
                    String.format("tcp://%s:%d", MqttConfigurationParameters.BROKER_ADDRESS, MqttConfigurationParameters.BROKER_PORT),
                    vehicleId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MqttConfigurationParameters.MQTT_USERNAME);
            options.setPassword(new String(MqttConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            //Connect to the target broker
            mqttClient.connect(options);

            System.out.println("Connected !");

            //Create Vehicle Reference
            VehicleDescriptor vehicleDescriptor = new VehicleDescriptor();
            vehicleDescriptor.setUuid(vehicleId);
            vehicleDescriptor.setDriverId(MqttConfigurationParameters.MQTT_USERNAME);
            vehicleDescriptor.setManufacturer("Tesla");
            vehicleDescriptor.setModel("Model Y");

            //Create the object to handle Vehicle Telemetry Data
            ElectricVehicleTelemetryData electricVehicleTelemetryData = new ElectricVehicleTelemetryData();

            //Publish Vehicle Information
            publishDeviceInfo(mqttClient, vehicleDescriptor);

            //Start to publish MESSAGE_COUNT messages
            for(int i = 0; i < MESSAGE_COUNT; i++) {

                //Measure new values
                electricVehicleTelemetryData.updateMeasurements();

                publishTelemetryData(mqttClient, vehicleDescriptor.getUuid(), electricVehicleTelemetryData);

                //Sleep for 1 Second
                Thread.sleep(3000);
            }

            //Disconnect from the broker and close the connection
            mqttClient.disconnect();
            mqttClient.close();

            System.out.println("Disconnected !");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Publish Vehicle information as a retained message
     *
     * @param mqttClient
     * @param vehicleDescriptor
     */
    public static void publishDeviceInfo(IMqttClient mqttClient, VehicleDescriptor vehicleDescriptor) {

        try {

            Gson gson = new Gson();

            if (mqttClient.isConnected() ) {

                //Topic Structure: /iot/user/<user_id>/vehicle/<vehicle_id>/info
                String topic = String.format("%s/%s/%s/%s",
                        MqttConfigurationParameters.MQTT_BASIC_TOPIC,
                        MqttConfigurationParameters.VEHICLE_TOPIC,
                        vehicleDescriptor.getUuid(),
                        MqttConfigurationParameters.VEHICLE_INFO_TOPIC);

                String payloadString = gson.toJson(vehicleDescriptor);

                MqttMessage msg = new MqttMessage(payloadString.getBytes());
                msg.setQos(0);
                msg.setRetained(true);
                mqttClient.publish(topic,msg);

                System.out.println("Device Data Correctly Published ! Topic: " + topic + " Payload: " + payloadString);
            }
            else{
                System.err.println("Error: Topic or Msg = Null or MQTT Client is not Connected !");
            }

        }catch(Exception e) {
            System.err.println("Error Publishing Vehicle Information ! Error: " + e.getLocalizedMessage());
        }

    }

    /**
     * Send a target String Payload to the specified MQTT topic
     *
     * @param mqttClient
     * @param vehicleId
     * @param telemetryData
     * @throws MqttException
     */
    public static void publishTelemetryData(IMqttClient mqttClient, String vehicleId, ElectricVehicleTelemetryData telemetryData) {

        try{

            Gson gson = new Gson();

            //Topic Structure: /iot/user/<user_id>/vehicle/<vehicle_id>/telemetry
            String topic = String.format("%s/%s/%s/%s",
                    MqttConfigurationParameters.MQTT_BASIC_TOPIC,
                    MqttConfigurationParameters.VEHICLE_TOPIC,
                    vehicleId,
                    MqttConfigurationParameters.VEHICLE_TELEMETRY_TOPIC);

            String payloadString = gson.toJson(telemetryData);

            System.out.println("Publishing to Topic: " + topic + " Data: " + payloadString);

            if (mqttClient.isConnected() && payloadString != null && topic != null) {

                MqttMessage msg = new MqttMessage(payloadString.getBytes());
                msg.setQos(0);
                msg.setRetained(false);
                mqttClient.publish(topic,msg);
                System.out.println("Data Correctly Published !");
            }
            else{
                System.err.println("Error: Topic or Msg = Null or MQTT Client is not Connected !");
            }

        }catch (Exception e){
            System.err.println("Error Publishing Telemetry Information ! Error: " + e.getLocalizedMessage());
        }

    }

}
