package a3.n1mo.mobjav.a816.myapplication.View.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.R;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;


/**
 * Created by digitalhouse on 26/10/16.
 */
public class AdapterNews extends RecyclerView.Adapter implements View.OnClickListener {

    private Context unContext;
    private List<News> unaListaNews = null;
    private View.OnClickListener listener;
    private List<News> unaListaNewsAdapter = null;

    public void setUnaListaNews(List<News> unaListaNews) {
        this.unaListaNews = unaListaNews;
    }


    public AdapterNews(List<News> unaListNewsAdapter) {
        this.unaListaNewsAdapter = unaListNewsAdapter;
    }

    public List<News> getListaDeNews() {
       unaListaNews = unaListaNewsAdapter;
       return unaListaNews;
    }

    public List<News> dameListaDeNews() {
        return unaListaNews;
    }


    public AdapterNews(Context unContext, List<News> unaListaNews, View.OnClickListener listener) {
        this.unContext = unContext;
        this.unaListaNews = unaListaNews;
        this.listener = listener;
    }

    public AdapterNews() {
    }

    public News getNewsAtPosition(Integer position){
        return unaListaNews.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(unContext);
        View viewDeLaCelda = inflater.inflate(R.layout.fragment_news_celda,parent,false);

        //LE DECIMOS A CADA CELDA QUE EL QUE LO VA A ESCUCHAR ES EL ADAPTADOR.
        viewDeLaCelda.setOnClickListener(this);

        NewsViewHolder newsViewHolder = new NewsViewHolder(viewDeLaCelda);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        News unaNews = unaListaNews.get(position);
        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        newsViewHolder.cargarNews(unaNews);

    }

    @Override
    public int getItemCount() {
        if(unaListaNews == null){
            return 0;
        }else {
            return unaListaNews.size();
        }
    }

    public void onClick(View view) {
        listener.onClick(view);
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitleNews;
        private ImageView imagenNoticia;
        private ImageView imageViewURLFotoNews;
        private ImageButton botonFavorita;


        public NewsViewHolder(View view){
            super(view);
            textViewTitleNews = (TextView) view.findViewById(R.id.textViewTitleNews);
            imageViewURLFotoNews = (ImageView) view.findViewById(R.id.imagenCanal);
            imagenNoticia = (ImageView) view.findViewById(R.id.imagenNoticia);
            botonFavorita = (ImageButton) view.findViewById(R.id.botonFavorito);
        }

        public void cargarNews(News unaNews){
            textViewTitleNews.setText(unaNews.getTitle());
            imageViewURLFotoNews.setImageResource(unaNews.getSource().getLogo());

            if(unaNews.getFavorita() != null && unaNews.getFavorita() == 1){
                botonFavorita.setImageResource(btn_star_big_on);
            }else{
                botonFavorita.setImageResource(btn_star_big_off);
            }

            botonFavorita.setTag(unaNews);
            Glide.with(unContext).load(unaNews.getUrl_foto()).error(R.drawable.error_sinfoto).into(imagenNoticia);
        }
    }

}