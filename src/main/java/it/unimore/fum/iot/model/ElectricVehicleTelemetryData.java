package it.unimore.fum.iot.model;

import java.util.Random;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project java-mqtt-laboratory
 * @created 27/10/2021 - 14:09
 */
public class ElectricVehicleTelemetryData {

    private GeoLocation geoLocation;

    private double batteryLevel = 100.0;

    private double speedKmh;

    private double engineTemperature;

    private long timestamp;

    //transient keyword used to ignore the field in the serialization
    private transient Random random;

    public ElectricVehicleTelemetryData() {
    }

    public ElectricVehicleTelemetryData(GeoLocation geoLocation, double batteryLevel, double speedKmh, double engineTemperature, long timestamp) {
        this.geoLocation = geoLocation;
        this.batteryLevel = batteryLevel;
        this.speedKmh = speedKmh;
        this.engineTemperature = engineTemperature;
        this.timestamp = timestamp;
    }

    /**
     * Update (with random values) vehicle sensor measurements
     */
    public void updateMeasurements(){

        if(this.random == null)
            this.random = new Random(System.currentTimeMillis());

        double randomLatitude = 10.0 + this.random.nextDouble() * 10.0;
        double randomLongitude = 40.0 + this.random.nextDouble() * 10.0;

        this.geoLocation = new GeoLocation(randomLatitude, randomLongitude, 0.0);
        this.engineTemperature = 80 + this.random.nextDouble() * 20.0;
        this.batteryLevel = this.batteryLevel - (this.random.nextDouble() * 5.0);
        this.speedKmh = 10 + this.random.nextDouble() * 80.0;
        this.timestamp = System.currentTimeMillis();
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getSpeedKmh() {
        return speedKmh;
    }

    public void setSpeedKmh(double speedKmh) {
        this.speedKmh = speedKmh;
    }

    public double getEngineTemperature() {
        return engineTemperature;
    }

    public void setEngineTemperature(double engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VehicleDescriptor{");
        sb.append("geoLocation=").append(geoLocation);
        sb.append(", batteryLevel=").append(batteryLevel);
        sb.append(", speedKmh=").append(speedKmh);
        sb.append(", engineTemperature=").append(engineTemperature);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", random=").append(random);
        sb.append('}');
        return sb.toString();
    }
}
