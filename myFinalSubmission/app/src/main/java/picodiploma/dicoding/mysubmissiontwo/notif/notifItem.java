package picodiploma.dicoding.mysubmissiontwo.notif;

import android.graphics.Bitmap;

public class notifItem {
    private int uuidNotif;
    private String name;
    private String deskripsi;
    private String photo;

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    private Bitmap photoBitmap;

    notifItem() {}

    public notifItem(int uuidNotif, String sender, String mesage, String photo) {
        this.uuidNotif = uuidNotif;
        this.name = sender;
        this.deskripsi = mesage;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUuidNotif() {
        return uuidNotif;
    }

    public void setUuidNotif(int uuidNotif) {
        this.uuidNotif = uuidNotif;
    }

    public String getName() {
        return name;
    }

    public void setName(String sender) {
        this.name = sender;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String mesage) {
        this.deskripsi = mesage;
    }
}
