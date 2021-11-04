package it.unimore.fum.iot.model;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project java-mqtt-laboratory
 * @created 27/10/2021 - 14:17
 */
public class VehicleDescriptor {

    private String uuid = null;

    private String manufacturer = null;

    private String model = null;

    private String driverId = null;

    public VehicleDescriptor() {
    }

    public VehicleDescriptor(String uuid, String manufacturer, String model, String driverId) {
        this.uuid = uuid;
        this.manufacturer = manufacturer;
        this.model = model;
        this.driverId = driverId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VehicleDescriptor{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", manufacturer='").append(manufacturer).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", driverId='").append(driverId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
