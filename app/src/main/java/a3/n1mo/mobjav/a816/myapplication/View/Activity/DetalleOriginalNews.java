package a3.n1mo.mobjav.a816.myapplication.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import a3.n1mo.mobjav.a816.myapplication.R;

public class DetalleOriginalNews extends AppCompatActivity {


    public DetalleOriginalNews() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_original_news);


        Intent intentRecibido = getIntent();

        Bundle bundleRecibido = intentRecibido.getExtras();

        String URLNewsTocada = bundleRecibido.getString("URL");

        WebView unWebView = (WebView) findViewById(R.id.webViewURLDetalle);

        unWebView.loadUrl(URLNewsTocada);

    }

}
