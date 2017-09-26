package a3.n1mo.mobjav.a816.myapplication.View.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.Category;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.View.Activity.FragmentRecyclerNews;

import static android.R.attr.category;


/**
 * Created by leona on 27/10/2016.
 */

public class AdaptadorViewPager extends FragmentStatePagerAdapter{

       private List<FragmentRecyclerNews> fragmentList;

        public AdaptadorViewPager(FragmentManager fm, Context context, String queMuestro) {
            super(fm);



            if(queMuestro.equals("CHECK")) {
                fragmentList = new ArrayList<>();
                Source sourceNoticia = new Source(-1, 0, "Leer mas tarde", "", "");
                fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
            }else if(queMuestro.equals("FAV")) {
                fragmentList = new ArrayList<>();
                Source sourceNoticia = new Source(-2, 0, "Favoritas", "", "");
                fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
            }else if(queMuestro.equals("DEPO")){
                    fragmentList = new ArrayList<>();
                    Source sourceNoticia = new Source(-300,0, "Deportes", "", "");
                    fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
            }else if(queMuestro.equals("POLI")){
                fragmentList = new ArrayList<>();
                Source sourceNoticia = new Source(-100,0, "Politica", "", "");
                fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
            }else if(queMuestro.equals("SOCI")){
                fragmentList = new ArrayList<>();
                Source sourceNoticia = new Source(-200,0, "Sociedad", "", "");
                fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
            }else if(queMuestro.equals("LOULTIMO")){
                fragmentList = new ArrayList<>();
                Source sourceNoticia = new Source(-400,0, "Lo último", "", "");
                fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));

            }else{

                DAOSQLite DAOSQLite = new DAOSQLite(context);
                List<Source> listaCanales = DAOSQLite.getListCanales();
                fragmentList = new ArrayList<>();
                for(Source canal: listaCanales) {
                    Source sourceNoticia = new Source(canal.getNombre(),canal.getUrl(),canal.getClase(),canal.getLogo(),canal.getCodigo(),canal.getObjCategoria());
                    fragmentList.add((FragmentRecyclerNews) FragmentRecyclerNews.DameUnFragment(sourceNoticia));
                }

            }


        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getCanalFiltro();
    }
}
