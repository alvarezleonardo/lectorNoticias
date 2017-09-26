package a3.n1mo.mobjav.a816.myapplication.Controller;

import android.content.Context;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.ConcentratorNew;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.Util.ResultListener;

/**
 * Created by leona on 24/10/2016.
 */

public class ControllerNews {
    private ResultListener<List<News>> listener;

    public void GetNews (Context context, Source canal, final ResultListener<List<News>> listenerFromView){
        listener = listenerFromView;
        ConcentratorNew concentradorNoticias = new ConcentratorNew();
        concentradorNoticias.GetNews(context, canal, new ResultListenerNewsController());
    }



    private class ResultListenerNewsController implements ResultListener<List<News>>{
        @Override
        public void finish(List<News> resultado) {
            //CUANDO EL DAO LE AVISA QUE TERMINO, EL CONTROLLER NOTIFICA A LA VIEW.
            listener.finish(resultado);
        }
    }


}
