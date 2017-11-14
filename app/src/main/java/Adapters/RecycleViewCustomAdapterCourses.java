package Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raul.photonotes.R;

import java.util.ArrayList;

import DB.Modelo.Cursando;
import DB.Modelo.Materia;
import DB.Modelo.PhotosCRUD;


/**
 * Created by Raul on 11/10/2017.
 */


public class RecycleViewCustomAdapterCourses extends RecyclerView.Adapter<RecycleViewCustomAdapterCourses.PerfilViewHolder>{

    private Context mContext;
    private ArrayList<Materia> materias;
    private RecyclerViewClickListener listener;
    private PhotosCRUD crud;

    public RecycleViewCustomAdapterCourses(Context c, ArrayList<Materia> materias, Adapters.RecyclerViewClickListener listener){
        mContext = c;
        this.listener = listener;
        this.materias = materias;
        crud = new PhotosCRUD(c);
    }

    @Override
    public PerfilViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_item, viewGroup, false);
        return new RowViewHolderCourses(v, listener);
    }

    @Override
    public void onBindViewHolder(PerfilViewHolder holder, int position) {
        //
        //holder.tvNameCourses.setText(materias.get(position).getNombre());
        //holder.tvDays.setText(materias.get(position).getId_materia());
       // holder.tvHours.setText(photos.get(position).getId());
        holder.tvNameCourses.setText(materias.get(position).getNombre());
        //.tvDays.setText("ID: "+materias.get(position).getId_materia());
        int id = materias.get(position).getId_materia();
        ArrayList<Cursando> curs = crud.getCursandos(id);
        holder.tvDays.setText(curs.get(0).getHorario());
    }


    @Override
    public int getItemCount() {
        return materias.size();
    }


    public static class PerfilViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameCourses, tvDays, tvHours;

        PerfilViewHolder(View vistaElemento){
            super(vistaElemento);
            tvNameCourses = (TextView) vistaElemento.findViewById(R.id.tvNameCourses);
            tvDays = (TextView) vistaElemento.findViewById(R.id.tvDays);
            tvHours = (TextView) vistaElemento.findViewById(R.id.tvHours);
        }
    }
}
