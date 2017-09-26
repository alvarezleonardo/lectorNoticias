package a3.n1mo.mobjav.a816.myapplication.DAO;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.Util.HTTPConnectionManager;
import a3.n1mo.mobjav.a816.myapplication.Util.ResultListener;

/**
 * Created by leona on 24/10/2016.
 */

public class ConcentratorNew {
    public void GetNews(Context context, Source canal, final ResultListener<List<News>> listenerFromView) {
        if (canal.getCodigo() == -1) {
            DAOSQLite daoBD = new DAOSQLite(context);
            List<News> unaListaNoticias = daoBD.getListNoticiasMarcadas();
            listenerFromView.finish(unaListaNoticias);

        }else if(canal.getCodigo() == -2) {
            DAOSQLite daoBD = new DAOSQLite(context);
            List<News> unaListaNoticias = daoBD.getListNoticiasFavoritas();
            listenerFromView.finish(unaListaNoticias);
        } else if(canal.getCodigo() <= -100){
                DAOSQLite daoBD = new DAOSQLite(context);
                List<News> unaListaNoticias = daoBD.getListNoticiasPorCat((canal.getCodigo() * -1) / 100);
                listenerFromView.finish(unaListaNoticias);

        } else {
            if (HTTPConnectionManager.isNetworkingOnline(context)) {
                DAOSQLite daosqLite = new DAOSQLite(context);
                //daosqLite.LimpiarNoticias();

                if (canal.getClase().equals("CLARIN")) {
                    NewsXML unaFuenteCL = new NewsClarin(canal, listenerFromView);
                    unaFuenteCL.ConsultarNoticia(context);
                } else if (canal.getClase().equals("NACION")) {
                    NewsXML unaFuenteCL = new NewsLaNacion(canal, listenerFromView);
                    unaFuenteCL.ConsultarNoticia(context);

                }
            } else {
                DAOSQLite daoBD = new DAOSQLite(context);

                List<News> unaListaNoticias = daoBD.getListNoticiasDB(canal);
                listenerFromView.finish(unaListaNoticias);
            }
        }
    }

}
