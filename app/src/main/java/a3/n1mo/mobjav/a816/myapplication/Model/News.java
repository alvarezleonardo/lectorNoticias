package a3.n1mo.mobjav.a816.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leona on 24/10/2016.
 */

public class News implements Parcelable {
    private String title, url, url_foto, description;
    private String pubDate;
    private Source source;
    private Integer favorita, marcada;


    public static List<News> dameUnaNews() {
        List<News> listaADevolver = new ArrayList<>();
        return listaADevolver;
    }


    public News(Source source) {
        this.source = source;
    }

    public Integer getFavorita() {
        return favorita;
    }

    public void setFavorita(Integer favorita) {
        this.favorita = favorita;
    }

    public Integer getMarcada() {
        return marcada;
    }

    public void setMarcada(Integer marcada) {
        this.marcada = marcada;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected News(Parcel in) {
        title = in.readString();
        url = in.readString();
        url_foto = in.readString();
        description = in.readString();
        pubDate = in.readString();
        source = (Source) in.readValue(Source.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(url_foto);
        dest.writeString(description);
        dest.writeString(pubDate);
        dest.writeValue(source);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

}