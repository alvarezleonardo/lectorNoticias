package a3.n1mo.mobjav.a816.myapplication.View.Activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdaptadorViewPagerDetalle;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContenedorDetalle extends Fragment {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundleRecibido = getArguments();

        Integer posicion = bundleRecibido.getInt("Posicion");

        final List<News> newsList = bundleRecibido.getParcelableArrayList("Lista");

        View view = inflater.inflate(R.layout.fragment_contenedor_detalle, container, false);


        FragmentManager miFragmentManager = getFragmentManager();

        AdaptadorViewPagerDetalle unAdaptadorViewPagerDetalle = new AdaptadorViewPagerDetalle(miFragmentManager, getActivity(), newsList);

        final ViewPager viewPagerDetalle = (ViewPager) view.findViewById(R.id.viewPagerDetalle);

        viewPagerDetalle.setAdapter(unAdaptadorViewPagerDetalle);
        viewPagerDetalle.setClipToPadding(false);
        viewPagerDetalle.setPageMargin(12);
        viewPagerDetalle.setCurrentItem(posicion);


        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) view.findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Integer posActual = viewPagerDetalle.getCurrentItem(); //POSICION ACTUAL
                Toast.makeText(getActivity(), "Leer mas tarde: " + newsList.get(posActual).getTitle(), Toast.LENGTH_SHORT).show();
                    DAOSQLite daoCanal = new DAOSQLite(getActivity());
                    News unaNews = newsList.get(posActual);
                    if(unaNews.getMarcada() != null && unaNews.getMarcada() == 1){
                        daoCanal.NoticiaMarcada(unaNews.getUrl(), 0);
                        unaNews.setMarcada(0);
                    }else{
                        daoCanal.NoticiaMarcada(unaNews.getUrl(), 1);
                        unaNews.setMarcada(1);
                    }
                }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Integer posActual = viewPagerDetalle.getCurrentItem(); //POSICION ACTUAL

                Uri uri = Uri.parse(newsList.get(posActual).getUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Toast.makeText(getActivity(), "FUENTE: " + newsList.get(posActual).getSource().getNombre(), Toast.LENGTH_SHORT).show();

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Integer posActual = viewPagerDetalle.getCurrentItem(); //POSICION ACTUAL

                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //Indicamos que voy a compartir texto
                share.setType("text/plain");
                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, newsList.get(posActual).getTitle());
                //Le agrego el texto a compartir (Puede ser un link tambien)
                share.putExtra(Intent.EXTRA_TEXT, newsList.get(posActual).getTitle() + ". " + newsList.get(posActual).getDescription() + " Fuente: " + newsList.get(posActual).getUrl());
                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, "Compartir Noticia"));

                //Toast.makeText(getActivity(), "Comparto: " + newsList.get(posActual).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

        /*FloatingActionButton floatButton = (FloatingActionButton) view.findViewById(R.id.floatinActionButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer posActual = viewPagerDetalle.getCurrentItem(); //POSICION ACTUAL
                Toast.makeText(getActivity(), "Comparto: " + newsList.get(posActual).getTitle(), Toast.LENGTH_SHORT).show();
            }

        });
        */

        Toolbar miToolbar = (Toolbar) view.findViewById(R.id.toolbarNews);
        ((AppCompatActivity) getActivity()).setSupportActionBar(miToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Noticia Ampliada");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;

    }

}