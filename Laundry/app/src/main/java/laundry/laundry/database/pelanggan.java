package laundry.laundry.database;

/**
 * Created by Saepul Uyun on 4/25/2018.
 */

//Class konstruksi dan getter setter untuk pelanggan
public class pelanggan {
    private  String plgnId;
    private  String Nama;
    private  String Status;

    public pelanggan(){

    }

    public pelanggan(String plgnId, String nama, String status) {
        this.plgnId = plgnId;
        Nama = nama;
        Status = status;
    }

    public String getPlgnId() {
        return plgnId;
    }

    public String getNama() {
        return Nama;
    }

    public String getStatus() {
        return Status;
    }

    public void setPlgnId(String plgnId) {
        this.plgnId = plgnId;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
