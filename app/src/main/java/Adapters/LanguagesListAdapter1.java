package Adapters;

/**
 * Created by pacod on 17/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.raul.photonotes.R;

import java.util.ArrayList;


public class LanguagesListAdapter1 extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> result2;
    ArrayList<String> result3;
    Context context;

    private static LayoutInflater inflater=null;

    public LanguagesListAdapter1(Context c, ArrayList<String> nombre, ArrayList<String>  fecha, ArrayList<String> horario) {
// TODO Auto-generated constructor stub
        result=nombre;
        result2=fecha;
        result3=horario;

        context=c;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv_nombre;
        TextView tv_horario;
        TextView tv_fecha;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        final View view;
        view = inflater.inflate(R.layout.layout_language_list_item1, null);

        holder.tv_nombre=(TextView) view.findViewById(R.id.txt_language);
        holder.tv_nombre.setText(result.get(position));

        holder.tv_horario=(TextView) view.findViewById(R.id.txt_language1);
        holder.tv_horario.setText(result2.get(position));

        holder.tv_fecha=(TextView) view.findViewById(R.id.txt_language2);
        holder.tv_fecha.setText(result3.get(position));

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                String nombre= result.get(position).toString();
                //Toast.makeText(context, "You Clicked " +result.get(position), Toast.LENGTH_SHORT).show();
                //Intent intent= new Intent(context, gallery_course.class);
                //intent.putextra("your_extra","your_class_value");
                //intent.putExtra("Nombre",nombre);
                //context.startActivity(intent);

            }
        });
        return view;
    }

}
