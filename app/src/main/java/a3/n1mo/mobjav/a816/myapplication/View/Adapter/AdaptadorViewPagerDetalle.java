package a3.n1mo.mobjav.a816.myapplication.View.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.View.Activity.DetalleFragment;

/**
 * Created by Exelsum on 10/11/2016.
 */

public class AdaptadorViewPagerDetalle extends FragmentStatePagerAdapter {

    private List<News> listaDeNoticias;
    private List<DetalleFragment> detalleFragmentList;


    public AdaptadorViewPagerDetalle(FragmentManager fm, Context context, List<News> newsList) {
        super(fm);

//        DAOSQLite DAOSQLite = new DAOSQLite(context);
//        List<News> listaDeNoticias = DAOSQLite.getListNoticiasDB(canalNewsTocada);

        listaDeNoticias = new ArrayList<>();
        List<News> listaDeNoticias = (List<News>) newsList;

        detalleFragmentList = new ArrayList<>();
        for (News newsTocada : listaDeNoticias) {
            detalleFragmentList.add(DetalleFragment.dameUnFragment(newsTocada));
        }

    }

    @Override
    public Fragment getItem(int position) {
        return detalleFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return detalleFragmentList.size();
    }
}