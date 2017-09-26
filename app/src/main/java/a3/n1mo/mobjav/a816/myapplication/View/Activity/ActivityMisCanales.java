package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.DAO.DAOSQLite;
import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdaptadorMisCanales;
import a3.n1mo.mobjav.a816.myapplication.View.Adapter.AdapterListaSelectorCanales;

public class ActivityMisCanales extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscanales);

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipalMisCanales);
        // TOOLBAR

        Toolbar miToolbar = (Toolbar) findViewById(R.id.toolbarNews);
        miToolbar.setTitle("Koala News");
        setSupportActionBar(miToolbar);

        // HUMBERGER BUTTON

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, miToolbar, R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        DAOSQLite daosLite = new DAOSQLite(this);
        List<Source> listaCanales = daosLite.getListCanales();
        ListView lstCanales = (ListView) findViewById(R.id.ListaMisCanales);
        AdaptadorMisCanales miAdaptadorCanales;
        miAdaptadorCanales = new  AdaptadorMisCanales(this, listaCanales);
        lstCanales.setAdapter(miAdaptadorCanales);

    }
}
