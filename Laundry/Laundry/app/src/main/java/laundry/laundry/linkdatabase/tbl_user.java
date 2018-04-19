package laundry.laundry.linkdatabase;

/**
 * Created by Saepul Uyun on 4/5/2018.
 */

public class tbl_user {
    private String Username;
    private String Password;
    private String Nama;
    private String Alamat;
    private String Email;
    private String Nohp;
    private String Harga;

    public tbl_user() {
    }

    public tbl_user(String username, String password, String nama, String alamat, String email, String nohp, String harga) {
        Username = username;
        Password = password;
        Nama = nama;
        Alamat = alamat;
        Email = email;
        Nohp = nohp;
        Harga = harga;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
            return Password;
        }

    public void setPassword(String password) {
            Password = password;
        }

    public String getNama() {
            return Nama;
        }

    public void setNama(String nama) {
            Nama = nama;
        }

    public String getAlamat() {
            return Alamat;
        }

    public void setAlamat(String alamat) {
            Alamat = alamat;
        }

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        Email = email;
    }

    public String getNohp() {
            return Nohp;
        }

    public void setNohp(String nohp) {
            Nohp = nohp;
        }

    public String getHarga() {
            return Harga;
        }

    public void setHarga(String harga) {
            Harga = harga;
        }
}