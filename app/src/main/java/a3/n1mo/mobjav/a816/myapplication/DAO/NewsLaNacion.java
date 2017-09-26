package a3.n1mo.mobjav.a816.myapplication.DAO;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.Util.HTTPConnectionManager;
import a3.n1mo.mobjav.a816.myapplication.Util.ResultListener;

/**
 * Created by leona on 24/10/2016.
 */

public class NewsLaNacion extends NewsXML
{
    private ResultListener<List<News>> listener;
    private Source sourceClarin;
    private Boolean enItem = false;
    private Context context;
    private DAOSQLite daosqLite;

    public NewsLaNacion(Source sourceClarin, final ResultListener<List<News>> listenerFromController) {
        this.sourceClarin = sourceClarin;
        this.listener =  listenerFromController;
    }

    @Override
    public void ConsultarNoticia(Context context) {
        this.context = context;
        this.daosqLite = new DAOSQLite(this.context);
        NewsLaNacion.ConsultaNoticiasAsync readFromNews = new NewsLaNacion.ConsultaNoticiasAsync(context, listener);
        readFromNews.execute();
    }


    private class ConsultaNoticiasAsync extends AsyncTask<List<News>, Void,  List<News>>{
        private Context context;
        private ResultListener<List<News>> listenerFromController;
        private List<News> unaListaDeNoticias;

        public ConsultaNoticiasAsync(Context context, final ResultListener<List<News>> listenerFromController) {
            this.context = context;
            this.listenerFromController = listenerFromController;
        }

        @Override
        protected List<News> doInBackground(List<News>... params) {
            List<News> unaListaDeNoticias = new ArrayList<>();
            XmlPullParser parser = Xml.newPullParser();
            News result = null;
            try{
                //AssetManager manager = context.getAssets();
               HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();
               InputStream input = httpConnectionManager.getRequestStream(sourceClarin.getUrl());

                parser.setInput(input, null);
                Integer status = parser.getEventType();
                while (status != XmlPullParser.END_DOCUMENT){
                    switch (status){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                           // Log.d("nota", parser.getName());
                            if (parser.getName().equals("entry")) {
                                result = new News(sourceClarin);
                                enItem = true;
                            }else if (parser.getName().equals("title") && enItem == true) {
                                result.setTitle(parser.nextText());
                            } else if (parser.getName().equals("link") && enItem == true) {
                                String url = parser.getAttributeValue(null, "href");
                                result.setUrl(url);
                             } else if (parser.getName().equals("div") && enItem == true){
                                String contenido = "";
                                if (parser.next() == XmlPullParser.TEXT) {
                                    contenido = parser.getText();
                                }

                                result.setDescription(contenido);
                            }else if (parser.getName().equals("updated") && enItem == true) {
                                result.setPubDate(parser.nextText());
                            }else if (parser.getName().equals("img") && enItem == true) {
                                result.setUrl_foto(parser.getAttributeValue(null, "src"));
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("entry")) {
                                unaListaDeNoticias.add(result);
                                daosqLite.AgregarNoticia(result);
                                enItem = false;
                            }
                            break;
                    }
                    status = parser.next();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return unaListaDeNoticias;

        }

        @Override
        protected void onPostExecute(List<News> newsLeida) {
            listenerFromController.finish(newsLeida);
        }

    }


}
