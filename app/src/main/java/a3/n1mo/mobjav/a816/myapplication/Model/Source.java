package a3.n1mo.mobjav.a816.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leona on 24/10/2016.
 */

public class Source implements Parcelable{
    private String nombre, url, clase;
    private Integer logo, codigo;
    private Integer category;
    private Category objCategoria;
    private Integer elegida;

    public Source(Integer codigo, Integer logo, String nombre, String url, String clase) {
        this.logo = logo;
        this.nombre = nombre;
        this.url = url;
        this.clase = clase;
        this.codigo = codigo;
    }

    public Source(String nombre, String url, String clase, Integer logo, Integer codigo, Category objCategoria) {
        this.nombre = nombre;
        this.url = url;
        this.clase = clase;
        this.logo = logo;
        this.codigo = codigo;
        this.objCategoria = objCategoria;
    }

    public Source(Integer codigo, Integer logo, String nombre, String url, String clase, Integer category) {
        this.nombre = nombre;
        this.url = url;
        this.clase = clase;
        this.logo = logo;
        this.category = category;
        this.codigo = codigo;
    }

    public Integer getElegida() {
        return elegida;
    }

    public void setElegida(Integer elegida) {
        this.elegida = elegida;
    }

    public Category getObjCategoria() {
        return objCategoria;
    }

    public void setObjCategoria(Category objCategoria) {
        this.objCategoria = objCategoria;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }


    // Agrego los metodos para implementar un objeto Parcelable
    protected Source(Parcel in) {
        codigo = in.readByte() == 0x00 ? null : in.readInt();
        logo = in.readByte() == 0x00 ? null : in.readInt();
        nombre = in.readString();
        url = in.readString();
        clase = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (codigo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(codigo);
        }
        if (logo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(logo);
        }
        dest.writeString(nombre);
        dest.writeString(url);
        dest.writeString(clase);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Source> CREATOR = new Parcelable.Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    // Fin de interfaz Parcelable

}