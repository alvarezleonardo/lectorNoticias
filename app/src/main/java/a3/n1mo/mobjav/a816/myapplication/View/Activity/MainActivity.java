package a3.n1mo.mobjav.a816.myapplication.View.Activity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Parcelable;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.twitter.sdk.android.Twitter;

import java.util.ArrayList;
import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.News;
import a3.n1mo.mobjav.a816.myapplication.R;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdaptadorViewPager;
import pl.aprilapps.easyphotopicker.EasyImage;

import static a3.n1mo.mobjav.a816.myapplication.R.layout.header;
import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

public class MainActivity extends AppCompatActivity implements FragmentRecyclerNews.ComunicadorFragmentActivity {

    private FragmentManager miFragmentManager;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private Boolean filtroClarin, filtroNacion;
    private ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String queMuestro = "TODO";

        Bundle unbundle = getIntent().getExtras();
        if(unbundle != null) {
            queMuestro = unbundle.getString("QUE");
            //Toast.makeText(this, queMuestro, Toast.LENGTH_SHORT).show();
        }

         //CREO UN LISTENER DEL TIPO LISTENER NAVIGATION VIEW
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigateView);

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal);

        //ASIGNO AL NAVIGATION VIEW EL LISTENER
        NavigationViewListener navigationViewListener = new NavigationViewListener();
        navigationView.setNavigationItemSelectedListener(navigationViewListener);


        miFragmentManager = getSupportFragmentManager();
        AdaptadorViewPager unAdaptadorViewPager = new AdaptadorViewPager(miFragmentManager, this, queMuestro);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageTransformer(false, new FadePageTransformer());

        viewPager.setAdapter(unAdaptadorViewPager);

       //cambiar el viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Header del navigateView

        View imagenKoala = findViewById(R.id.idKoalaHeader);


        // TOOLBAR

        Toolbar miToolbar = (Toolbar) findViewById(R.id.toolbarNews);
        miToolbar.setTitle("Koala News");
        setSupportActionBar(miToolbar);

        // HUMBERGER BUTTON

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, miToolbar, R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }


    private class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.idSociedad) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "SOCI");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            } else if (item.getItemId() == R.id.idPolitica) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "POLI");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }  else if (item.getItemId() == R.id.idDeportes) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "DEPO");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            } else if (item.getItemId() == R.id.idUltimo) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "LOULTIMO");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }else if (item.getItemId() == R.id.misSuscripciones) {
                Intent unIntent = new Intent(MainActivity.this, Seleccion_Canal_Activity.class);
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }/*else if (item.getItemId() == R.id.misCanales) {
                Intent unIntent = new Intent(MainActivity.this, ActivityMisCanales.class);
                startActivity(unIntent);
            }*/ else if (item.getItemId() == R.id.marcadas) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "CHECK");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }else if (item.getItemId() == R.id.favoritas) {
                Intent unIntent = new Intent(MainActivity.this, MainActivity.class);
                unIntent.putExtra("QUE", "FAV");
                startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }else if (item.getItemId() == R.id.MenuSignOut) {
                if(AccessToken.getCurrentAccessToken() != null){
                    LoginManager unLogin = LoginManager.getInstance();
                    unLogin.logOut();
                }else if (Twitter.getSessionManager().getActiveSession() != null) {
                    Twitter.logOut();
                }
                MainActivity.this.finish();
            }

            drawerLayout.closeDrawers();
            return true;
        }
    }



    public void clickearonEstaNews(List<News> newsList, Integer posicionTocada){
        //CUANDO LA NOTIFICAN, COMIENZA A CORRER ESTE CODIGO

        //armo el bundle con el Canal
        Bundle unBundle = new Bundle();
        unBundle.putInt("Posicion", posicionTocada);
        unBundle.putParcelableArrayList("Lista", (ArrayList<? extends Parcelable>) newsList);

/*      Source canalNewsTocada = new Source(newsTocada.getSource().getCodigo(),newsTocada.getSource().getLogo(), newsTocada.getSource().getNombre(), newsTocada.getSource().getUrl(), newsTocada.getSource().getClase());
        unBundle.putParcelable("Source", canalNewsTocada);
*/

        //instancio el contenedor para reemplazar el fragment
        FragmentContenedorDetalle fragmentContenedorDetalle = new FragmentContenedorDetalle();
        fragmentContenedorDetalle.setArguments(unBundle);
        FragmentTransaction fragmentTransaction = miFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorPrincipal, fragmentContenedorDetalle).addToBackStack("tag");
        fragmentTransaction.commit();

    }


    public void NoticiaFavorita (View view){
        DAOSQLite daoCanal = new DAOSQLite(MainActivity.this);
        News unaNews = (News) view.getTag();
        ImageButton unBoton = (ImageButton) view;
        if(unaNews.getFavorita() != null && unaNews.getFavorita() == 1){
            unBoton.setImageResource(btn_star_big_off);
            daoCanal.NoticiaFavorita(unaNews.getUrl(), 0);
            unaNews.setFavorita(0);
            unBoton.refreshDrawableState();
        }else{
            daoCanal.NoticiaFavorita(unaNews.getUrl(), 1);
            unaNews.setFavorita(1);
            unBoton.setImageResource(btn_star_big_on);
            unBoton.refreshDrawableState();
        }


    }


    @Override
    public void onBackPressed() {
        if (!miFragmentManager.popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!miFragmentManager.popBackStackImmediate()) {
            super.onSupportNavigateUp();
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            view.setAlpha(1 - Math.abs(position));
            if (position < 0) {
                view.setScrollX((int)((float)(view.getWidth()) * position));
            } else if (position > 0) {
                view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
            } else {
                view.setScrollX(0);
            }
        }
    }

}