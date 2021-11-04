package it.unimore.fum.iot.conf;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project java-mqtt-laboratory
 * @created 27/10/2021 - 14:50
 */
public class MqttConfigurationParameters {

    public static String BROKER_ADDRESS = "155.185.228.20";

    //PORT of the target MQTT Broker
    public static int BROKER_PORT = 7883;

    //MQTT account username to connect to the target broker
    public static final String MQTT_USERNAME = "<your_username>";

    //MQTT account password to connect to the target broker
    public static final String MQTT_PASSWORD = "<your_password>";

    //Basic Topic used to consume generated demo data (the topic is associated to the user)
    public static final String MQTT_BASIC_TOPIC = String.format("/iot/user/%s", MQTT_USERNAME);

    public static final String VEHICLE_TOPIC = "vehicle";

    public static final String VEHICLE_TELEMETRY_TOPIC = "telemetry";

    public static final String VEHICLE_INFO_TOPIC = "info";

}
