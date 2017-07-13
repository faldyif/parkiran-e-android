package id.markirin.parkirane;

/**
 * Created by faldyikhwanfadila on 7/13/17.
 */

public class ParkirSlot {
    private String name;
    private String kondisi;
    private String koneksi;
    private Boolean sensor;
    private String reservedBy;
    private Integer countdown;

    public ParkirSlot() {
    }

    public ParkirSlot(String name, String kondisi, String koneksi, Boolean sensor, String reservedBy, Integer countdown) {
        this.name = name;
        this.kondisi = kondisi;
        this.koneksi = koneksi;
        this.sensor = sensor;
        this.reservedBy = reservedBy;
        this.countdown = countdown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getKoneksi() {
        return koneksi;
    }

    public void setKoneksi(String koneksi) {
        this.koneksi = koneksi;
    }

    public Boolean getSensor() {
        return sensor;
    }

    public void setSensor(Boolean sensor) {
        this.sensor = sensor;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Integer getCountdown() {
        return countdown;
    }

    public void setCountdown(Integer countdown) {
        this.countdown = countdown;
    }
}
