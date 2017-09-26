package a3.n1mo.mobjav.a816.myapplication.View.Adapter;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.support.v7.widget.SwitchCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import a3.n1mo.mobjav.a816.myapplication.R;

import a3.n1mo.mobjav.a816.myapplication.Model.Source;


/**
 * Created by leona on 15/11/2016.
 */

public class AdapterListaSelectorCanales extends BaseAdapter {
    private Context context;
    private List<Source> listaCanales;
    private View.OnClickListener lisener;



    public AdapterListaSelectorCanales(Context context, List<Source> listaCanales, View.OnClickListener lisener) {
        this.context = context;
        this.listaCanales = listaCanales;
        this.lisener = lisener;
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
        convertView = unInflador.inflate(R.layout.detalle_lista_sel_canales, parent, false);

        SwitchCompat canalSel;
        canalSel = (SwitchCompat) convertView.findViewById(R.id.CheckCanal);
        canalSel.setOnClickListener(this.lisener);
        canalSel.setTag(listaCanales.get(position).getCodigo());
        if(listaCanales.get(position).getElegida() == 1){
            canalSel.setChecked(true);
        }else{
            canalSel.setChecked(false);
        }

        TextView nombreCanal;
        nombreCanal = (TextView) convertView.findViewById(R.id.NombreCanal);
        nombreCanal.setText(listaCanales.get(position).getNombre());

        return convertView;
    }
}
