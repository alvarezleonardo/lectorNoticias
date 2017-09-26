package a3.n1mo.mobjav.a816.myapplication.View.Adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a3.n1mo.mobjav.a816.myapplication.Model.Source;
import a3.n1mo.mobjav.a816.myapplication.R;

/**
 * Created by leona on 22/11/2016.
 */

public class AdaptadorMisCanales extends BaseAdapter {
    private Context context;
    private List<Source> listaCanales;

    public AdaptadorMisCanales(Context context, List<Source> listaCanales) {
        this.context = context;
        this.listaCanales = listaCanales;
    }

    @Override
    public int getCount() {
        return listaCanales.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCanales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater unInflador;

        unInflador = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = unInflador.inflate(R.layout.detalle_lista_miscanales, parent, false);

        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.misCanalesImagenCanal);
        imageView.setImageResource(listaCanales.get(position).getLogo());

        TextView nombreCanal;
        nombreCanal = (TextView) convertView.findViewById(R.id.misCanalesNombreCanal);
        nombreCanal.setText(listaCanales.get(position).getNombre());

        return convertView;
    }
}
