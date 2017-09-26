package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdapterListaSelectorCanales;

public class Seleccion_Canal_Activity extends AppCompatActivity   {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion__canal);

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipalSelector);
        // TOOLBAR

        Toolbar miToolbar = (Toolbar) findViewById(R.id.toolbarNews);
        miToolbar.setTitle("Koala News");
        setSupportActionBar(miToolbar);

        // HUMBERGER BUTTON

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, miToolbar, R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        DAOSQLite daosLite = new DAOSQLite(this);
        ListView lstCanales = (ListView) findViewById(R.id.ListaSeleccionCanales);
        AdapterListaSelectorCanales miAdaptadorCanales;
        miAdaptadorCanales = new AdapterListaSelectorCanales(this, daosLite.getListAllCanales(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOSQLite daoCanal = new DAOSQLite(Seleccion_Canal_Activity.this);
                SwitchCompat unCheckBox = (SwitchCompat) v.findViewById(R.id.CheckCanal);
                if(unCheckBox.isChecked()){
                    daoCanal.MarcarCanal(unCheckBox.getTag().toString(), 1);
                }else{
                    daoCanal.MarcarCanal(unCheckBox.getTag().toString(), 0);
                }
            }
        });
        lstCanales.setAdapter(miAdaptadorCanales);

    }

    public void FinalizarSeleccion (View view){
        DAOSQLite DAOSQLite = new DAOSQLite(this);
        List<Source> listaCanales = DAOSQLite.getListCanales();
        if(listaCanales.size()==0){

            Toast.makeText(this, "Debe seleccionar Canales", Toast.LENGTH_SHORT).show();

        }else {
            Intent unIntent = new Intent(Seleccion_Canal_Activity.this, MainActivity.class);
            startActivity(unIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            this.finish();
        }

    }



}
