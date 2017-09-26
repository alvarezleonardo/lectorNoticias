package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.Controller.ControllerNews;
import a3.n1mo.mobjav.a816.myapplication.Model.Category;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;
import a3.n1mo.mobjav.a816.myapplication.Util.ResultListener;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdapterNews;

/**
 * Created by digitalhouse on 26/10/16.
 */
public class FragmentRecyclerNews extends Fragment {

    private Context unContext;
    private RecyclerView recyclerViewNews;
    private AdapterNews unAdapterNews;
    private Source canal = null;
    private ControllerNews controllerNews;
    private SwipeRefreshLayout swipeContainer;


    public static Fragment DameUnFragment(Source canal){
        FragmentRecyclerNews unFragment = new FragmentRecyclerNews();
        unFragment.setCanal(canal);
        Bundle unBundle = new Bundle();
        unBundle.putString("Nombre", canal.getNombre());
        unBundle.putString("Url", canal.getUrl());
        unBundle.putString("Clase", canal.getClase());
        unBundle.putInt("Logo", canal.getLogo());
        unBundle.putInt("Codigo", canal.getCodigo());

        if(canal.getObjCategoria() != null) {
            unBundle.putString("NombreCat", canal.getObjCategoria().getName());
            unBundle.putInt("CodigoCat", canal.getObjCategoria().getCodigo());
        }
        unFragment.setArguments(unBundle);
        return unFragment;
    }

    public void setCanal(Source canal) {
        this.canal = canal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        Category unacategoria = new Category(bundle.getString("NombreCAT"), bundle.getInt("CodigoCat"));
        Source canal = new Source(bundle.getString("Nombre"), bundle.getString("Url"), bundle.getString("Clase"), bundle.getInt("Logo"), bundle.getInt("Codigo"), unacategoria);
        unAdapterNews = new AdapterNews(getActivity(), null, new ListenerNews());

        controllerNews = new ControllerNews();
        ///retorna los tag y las categorias de cada tag


        //esto reporta un mapa con el nombre de cada categoria en el key y una lista de news.
        final View viewADevolver = inflater.inflate (R.layout.fragment_recycler_news, container, false);

        controllerNews.GetNews(getActivity(), canal, new ResultListener<List<News>>() {
            @Override
            public void finish(List<News> resultado) {
                List<News> unaListaNews = resultado;
                ProgressBar unaProgress = (ProgressBar) viewADevolver.findViewById(R.id.pbProgress);
                unaProgress.setVisibility(View.GONE);
                unAdapterNews.setUnaListaNews(unaListaNews);
                unAdapterNews.notifyDataSetChanged();
            }});


        recyclerViewNews = (RecyclerView) viewADevolver.findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNews.setAdapter(unAdapterNews);

        recyclerViewNews.setLayoutManager(linearLayoutManager);

        //busco el Swipe, lo inicio y le agrego color

        swipeContainer = (SwipeRefreshLayout) viewADevolver.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Codigo a ejecutarse cuando se reconoce el pull to refresh
                update();
            }
        });

        return viewADevolver;
    }

    // esto lo uso dentro del Swipe para actualizar
    public void  update (){
        controllerNews.GetNews(getActivity(), canal, new ResultListener<List<News>>() {
            @Override
            public void finish(List<News> resultado) {
                List<News> unaListaNews = resultado;
                unAdapterNews.setUnaListaNews(unaListaNews);
                unAdapterNews.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);

            }});

    }

    public CharSequence getCanalFiltro()
    {
        return canal.getNombre();
    }

    public class ListenerNews implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            ComunicadorFragmentActivity unComunicador = (ComunicadorFragmentActivity) getActivity();
            Integer posicionTocada = recyclerViewNews.getChildAdapterPosition(view);

            News newTocada = unAdapterNews.getNewsAtPosition(posicionTocada);

            Toast.makeText(getActivity(), newTocada.getTitle(), Toast.LENGTH_SHORT).show();

            List<News> newsList = unAdapterNews.dameListaDeNews();
            unComunicador.clickearonEstaNews(newsList, posicionTocada);
        }
    }

    public interface ComunicadorFragmentActivity {

        public void clickearonEstaNews(List<News> newsList, Integer posicionTocada);
    }

  
}
