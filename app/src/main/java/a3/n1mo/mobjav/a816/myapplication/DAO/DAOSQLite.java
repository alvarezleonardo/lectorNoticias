package a3.n1mo.mobjav.a816.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.Model.Category;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;

/**
 * Created by leona on 25/10/2016.
 */

public class DAOSQLite extends SQLiteOpenHelper {

    private static final String DATABASENAME = "Noticia";
    private static final Integer DATABASEVERSION = 1;

    private static final String TABLECANAL = "Canal";
    private static final String TABLECATEGORIA = "Categoria";
    private static final String TABLENOTICIA = "Noticia";
    private static final String TABLECANALEUSUARIO = "CanalUsu";

    private Context context;

    public DAOSQLite(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLECANAL + "(id INTEGER PRIMARY KEY,  nombre "
                + " TEXT, "
                + " url TEXT, "
                + " logo INTEGER, "
                + " clase TEXT , "
                + " categoria INTEGER, codigo INTEGER, elegido INTEGER)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLECATEGORIA + "(codigo INTEGER PRIMARY KEY, nombre "
                + " TEXT)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLENOTICIA + "(url TEXT PRIMARY KEY, title "
                + " TEXT, url_foto TEXT, description TEXT, pubdate TEXT, canal INTEGER, favorita INTEGER, marcada INTEGER)";
        db.execSQL(createTable);

        // Lista de canales

        AgregarCanales(db, new Source(1, R.drawable.clarin_logo, "Clarin Politica", "http://www.clarin.com/rss/politica/", "CLARIN", 2));
        AgregarCanales(db, new Source(2, R.drawable.clarin_logo, "Clarin Sociedad", "http://www.clarin.com/rss/sociedad/", "CLARIN", 1));
        AgregarCanales(db, new Source(3, R.drawable.clarin_logo, "Clarin Deportes", "http://www.clarin.com/rss/deportes/futbol/", "CLARIN", 3));
        AgregarCanales(db, new Source(4, R.drawable.clarin_logo, "Clarin Lo último", "http://www.clarin.com/rss/lo-ultimo/", "CLARIN", 4));
        AgregarCanales(db, new Source(5, R.drawable.lanacion, "Nacion Politica", "http://contenidos.lanacion.com.ar/herramientas/rss/categoria_id=30", "NACION", 2));
        AgregarCanales(db, new Source(6, R.drawable.lanacion, "Nacion Socidedad", "http://contenidos.lanacion.com.ar/herramientas/rss/categoria_id=7773", "NACION", 1));
        AgregarCanales(db, new Source(7, R.drawable.lanacion, "Nacion Deportes", "http://contenidos.lanacion.com.ar/herramientas/rss/categoria_id=131", "NACION", 3));
        AgregarCanales(db, new Source(8, R.drawable.lanacion, "Nacion Lo último", "http://contenidos.lanacion.com.ar/herramientas/rss/origen=2", "NACION", 4));

        AgregarCategorias(db, new Category("Sociedad", 1));
        AgregarCategorias(db, new Category("Politica", 2));
        AgregarCategorias(db, new Category("Deportes", 3));
        AgregarCategorias(db, new Category("Lo último", 4));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void AgregarCanales (SQLiteDatabase dbLite, Source canal){

        ContentValues row = new ContentValues();

        row.put("nombre", canal.getNombre());
        row.put("url", canal.getUrl());
        row.put("logo", canal.getLogo());
        row.put("clase", canal.getClase());
        row.put("codigo", canal.getCodigo());
        row.put("categoria", canal.getCategory());
        row.put("elegido", 0);
        dbLite.insert(TABLECANAL, null, row);
    }

    public void AgregarNoticia (News news){
        SQLiteDatabase dblite = getWritableDatabase();

        if(ExisteNoticia(news) != true) {
            ContentValues row = new ContentValues();
            row.put("url", news.getUrl());
            row.put("url_foto", news.getUrl_foto());
            row.put("title", news.getTitle());
            row.put("description", news.getDescription());
            row.put("pubdate", news.getPubDate());
            row.put("canal", news.getSource().getCodigo());
            dblite.insert(TABLENOTICIA, null, row);
            dblite.close();
        }
    }

    public Boolean ExisteNoticia(News news){
        Boolean existe = true;
        SQLiteDatabase dblite = getReadableDatabase();

        String select = "SELECT * FROM " + TABLENOTICIA + " where url = '" + news.getUrl() + "'";
        Cursor cursor = dblite.rawQuery(select, null);
        if(cursor.getCount() < 1 ){
            existe = false;
        }
        return existe;
    }

    public void LimpiarNoticias(){
        SQLiteDatabase database = getReadableDatabase();
        String delete = "delete from Noticia where marcada <> 1 and favorita <> 1";
        database.execSQL(delete);
        database.close();
    }

    private void AgregarCategorias (SQLiteDatabase dbLite, Category categoria){
        ContentValues row = new ContentValues();
        row.put("codigo", categoria.getCodigo());
        row.put("nombre", categoria.getName());
        dbLite.insert(TABLECATEGORIA, null, row);
    }

    public List<Category> getListCategoria(){
        List<Category> listaresult = new ArrayList<>();
        String select = "SELECT * from categoria";

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(select, null);

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getString(cursor.getColumnIndex("nombre")), cursor.getInt(cursor.getColumnIndex("codigo")));
            listaresult.add(category);
        }

        cursor.close();
        database.close();
        return listaresult;
    }

    public List<Source> getListCanales(){
        List<Source> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT canal.*, categoria.codigo as codigocat, categoria.nombre as nomcat FROM canal " +
                "   left join categoria on categoria.codigo = canal.categoria where canal.elegido = 1";

        Category categoria = null;
        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            categoria = new Category(cursor.getString(cursor.getColumnIndex("nomcat")), cursor.getInt(cursor.getColumnIndex("codigocat")));
            Source source = new Source(cursor.getString(cursor.getColumnIndex("nombre")), cursor.getString(cursor.getColumnIndex("url")), cursor.getString(cursor.getColumnIndex("clase")), cursor.getInt(cursor.getColumnIndex("logo")), cursor.getInt(cursor.getColumnIndex("codigo")), categoria);
            source.setElegida(cursor.getInt(cursor.getColumnIndex("elegido")));
            listaresult.add(source);
        }

        cursor.close();
        database.close();
        return listaresult;
    }

    public List<Source> getListAllCanales(){
        List<Source> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT canal.*, categoria.codigo as codigocat, categoria.nombre as nomcat FROM canal " +
                "   left join categoria on categoria.codigo = canal.categoria";

        Category categoria = null;
        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            categoria = new Category(cursor.getString(cursor.getColumnIndex("nomcat")), cursor.getInt(cursor.getColumnIndex("codigocat")));
            Source source = new Source(cursor.getString(cursor.getColumnIndex("nombre")), cursor.getString(cursor.getColumnIndex("url")), cursor.getString(cursor.getColumnIndex("clase")), cursor.getInt(cursor.getColumnIndex("logo")), cursor.getInt(cursor.getColumnIndex("codigo")), categoria);
            source.setElegida(cursor.getInt(cursor.getColumnIndex("elegido")));
            listaresult.add(source);
        }

        cursor.close();
        database.close();
        return listaresult;
    }

    public void NoticiaFavorita (String codigoNoticia, Integer valor){
        SQLiteDatabase database = getReadableDatabase();
        String actualizarValor = "update noticia set  favorita = " + valor + " where url = '" + codigoNoticia + "'";
        database.execSQL(actualizarValor);
        database.close();
    }

    public void NoticiaMarcada (String codigoNoticia, Integer valor){
        SQLiteDatabase database = getReadableDatabase();
        String actualizarValor = "update noticia set  marcada = " + valor + " where url = '" + codigoNoticia + "'";
        database.execSQL(actualizarValor);
        database.close();
    }


    public void MarcarCanal (String codigoCanal, Integer valor){
        SQLiteDatabase database = getReadableDatabase();
        String actualizarValor = "update canal set  elegido = " + valor + " where codigo = " + codigoCanal;
        database.execSQL(actualizarValor);
        database.close();

    }


    public List<News> getListNoticiasDB(Source source){
        List<News> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT * from noticia where canal = " + source.getCodigo() + " order by pubDate" ;

        Category categoria = null;
        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            News news = new News(source);
            news.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            news.setPubDate(cursor.getString(cursor.getColumnIndex("pubdate")));
            news.setUrl_foto(cursor.getString(cursor.getColumnIndex("url_foto")));
            news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            news.setFavorita(cursor.getInt(cursor.getColumnIndex("favorita")));
            news.setMarcada(cursor.getInt(cursor.getColumnIndex("marcada")));
            listaresult.add(news);
        }

        cursor.close();
        database.close();
        return listaresult;
    }

    public List<News> getListNoticiasFavoritas(){
        List<News> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT noticia.*, canal.nombre as nomcanal, canal.url as canalurl, " +
                "canal.logo as canallogo, canal.clase as canalclase, canal.categoria as canalcategoria, " +
                "canal.codigo as canalcodigo, categoria.codigo as codigocat, categoria.nombre as nomcat " +
                "from noticia " +
                "left join canal on canal.codigo = noticia.canal " +
                "left join categoria on categoria.codigo = canal.categoria " +
                "where favorita = 1" ;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            Category categoria = new Category(cursor.getString(cursor.getColumnIndex("nomcat")), cursor.getInt(cursor.getColumnIndex("codigocat")));
            Source sourceNoticia = new Source(cursor.getString(cursor.getColumnIndex("nomcanal")), cursor.getString(cursor.getColumnIndex("canalurl")), cursor.getString(cursor.getColumnIndex("canalclase")), cursor.getInt(cursor.getColumnIndex("canallogo")), cursor.getInt(cursor.getColumnIndex("canalcodigo")), categoria);

            News news = new News(sourceNoticia);
            news.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            news.setPubDate(cursor.getString(cursor.getColumnIndex("pubdate")));
            news.setUrl_foto(cursor.getString(cursor.getColumnIndex("url_foto")));
            news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            news.setFavorita(cursor.getInt(cursor.getColumnIndex("favorita")));
            news.setMarcada(cursor.getInt(cursor.getColumnIndex("marcada")));
            listaresult.add(news);
        }

        cursor.close();
        database.close();
        return listaresult;
    }

    public List<News> getListNoticiasMarcadas(){
        List<News> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT noticia.*, canal.nombre as nomcanal, canal.url as canalurl, " +
                "canal.logo as canallogo, canal.clase as canalclase, canal.categoria as canalcategoria, " +
                "canal.codigo as canalcodigo, categoria.codigo as codigocat, categoria.nombre as nomcat " +
                "from noticia " +
                "left join canal on canal.codigo = noticia.canal " +
                "left join categoria on categoria.codigo = canal.categoria " +
                "where marcada = 1" ;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            Category categoria = new Category(cursor.getString(cursor.getColumnIndex("nomcat")), cursor.getInt(cursor.getColumnIndex("codigocat")));
            Source sourceNoticia = new Source(cursor.getString(cursor.getColumnIndex("nomcanal")), cursor.getString(cursor.getColumnIndex("canalurl")), cursor.getString(cursor.getColumnIndex("canalclase")), cursor.getInt(cursor.getColumnIndex("canallogo")), cursor.getInt(cursor.getColumnIndex("canalcodigo")), categoria);

            News news = new News(sourceNoticia);
            news.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            news.setPubDate(cursor.getString(cursor.getColumnIndex("pubdate")));
            news.setUrl_foto(cursor.getString(cursor.getColumnIndex("url_foto")));
            news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            news.setFavorita(cursor.getInt(cursor.getColumnIndex("favorita")));
            news.setMarcada(cursor.getInt(cursor.getColumnIndex("marcada")));
            listaresult.add(news);
        }
        cursor.close();
        database.close();
        return listaresult;
    }

    public List<News> getListNoticiasPorCat(Integer codigoCat){
        List<News> listaresult = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT noticia.*, canal.nombre as nomcanal, canal.url as canalurl, " +
                "canal.logo as canallogo, canal.clase as canalclase, canal.categoria as canalcategoria, " +
                "canal.codigo as canalcodigo, categoria.codigo as codigocat, categoria.nombre as nomcat " +
                "from noticia " +
                "left join canal on canal.codigo = noticia.canal " +
                "left join categoria on categoria.codigo = canal.categoria " +
                "where canal.categoria = " + codigoCat + " order by pubdate" ;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){
            Category categoria = new Category(cursor.getString(cursor.getColumnIndex("nomcat")), cursor.getInt(cursor.getColumnIndex("codigocat")));
            Source sourceNoticia = new Source(cursor.getString(cursor.getColumnIndex("nomcanal")), cursor.getString(cursor.getColumnIndex("canalurl")), cursor.getString(cursor.getColumnIndex("canalclase")), cursor.getInt(cursor.getColumnIndex("canallogo")), cursor.getInt(cursor.getColumnIndex("canalcodigo")), categoria);

            News news = new News(sourceNoticia);
            news.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            news.setPubDate(cursor.getString(cursor.getColumnIndex("pubdate")));
            news.setUrl_foto(cursor.getString(cursor.getColumnIndex("url_foto")));
            news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            news.setFavorita(cursor.getInt(cursor.getColumnIndex("favorita")));
            news.setMarcada(cursor.getInt(cursor.getColumnIndex("marcada")));
            listaresult.add(news);
        }
        cursor.close();
        database.close();
        return listaresult;
    }

}
