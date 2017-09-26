package a3.n1mo.mobjav.a816.myapplication.View.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragment extends Fragment {

    public DetalleFragment() {
    }

    public static DetalleFragment dameUnFragment (News newsTocada){

        DetalleFragment fragmentDetalleNoticia = new DetalleFragment();
        Bundle bundle = new Bundle();

        bundle.putString("Imagen", newsTocada.getUrl_foto());
        bundle.putInt("FotoCanal", newsTocada.getSource().getLogo());
        bundle.putString("Titulo", newsTocada.getTitle());
        bundle.putString("Descripcion", newsTocada.getDescription());
      //  bundle.putString("Fecha", newsTocada.getPubDate());
        bundle.putString("URL", newsTocada.getUrl());

        fragmentDetalleNoticia.setArguments(bundle);

        return fragmentDetalleNoticia;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        ImageView imagenNoticia = (ImageView) view.findViewById(R.id.imagenNoticiaAmpliada);
        ImageView imagenCanal = (ImageView) view.findViewById(R.id.imagenCanalAmpliada);
        TextView tituloNoticia = (TextView) view.findViewById(R.id.textViewTitleNewsAmpliada);
        TextView descripcionNoticia = (TextView) view.findViewById(R.id.detalleNoticiaAmpliada);
      //  TextView fechaPublicadaNoticia = (TextView) view.findViewById(R.id.fechaPubAmpliada);
      //  TextView URLNoticia = (TextView) view.findViewById(R.id.urlNoticia);

        Bundle unBundle = getArguments();

        String imagenAMostrar = unBundle.getString("Imagen");
        Integer fotoCanalAMostrar = unBundle.getInt("FotoCanal");
        String tituloAMostrar = unBundle.getString("Titulo");
        String descripcionAMostrar = unBundle.getString("Descripcion");
       // String fechaAMostrar = unBundle.getString("Fecha");
        String URLAMostrar = unBundle.getString("URL");

        Glide.with(this.getActivity()).load(imagenAMostrar).error(R.drawable.error_sinfoto).into(imagenNoticia);
        imagenCanal.setImageResource(fotoCanalAMostrar);
        tituloNoticia.setText(tituloAMostrar);
        descripcionNoticia.setText(descripcionAMostrar);
        //fechaPublicadaNoticia.setText(fechaAMostrar);
        //URLNoticia.setText(Html.fromHtml(URLAMostrar));

        return view;

    }
    /*
    public void clickURLNoticia (View view){


        Intent unIntent = new Intent(this, DetalleOriginalNews.class);
        Bundle unBundle = new Bundle();

        unIntent.putExtras(unBundle);
        startActivity(unIntent);

    }
    */

}
