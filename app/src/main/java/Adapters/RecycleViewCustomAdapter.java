package Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.raul.photonotes.EditActivity;
import com.example.raul.photonotes.FullscreenPhotoActivity;
import com.example.raul.photonotes.R;
import com.example.raul.photonotes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import DB.Modelo.Materia;
import DB.Modelo.Photo;
import DB.Modelo.PhotosCRUD;

/**
 * Created by Raul on 11/10/2017.
 */


public class RecycleViewCustomAdapter extends RecyclerView.Adapter<RecycleViewCustomAdapter.PerfilViewHolder>{

    private Context mContext;
    private ArrayList<Photo> photos;
    private Adapters.RecyclerViewClickListener listener;
    private PhotosCRUD crud;
    private ShareActionProvider mShareActionProvider;

    public RecycleViewCustomAdapter(Context c, ArrayList<Photo> photos, Adapters.RecyclerViewClickListener listener){
        mContext = c;
        this.listener = listener;
        this.photos = photos;
        crud = new PhotosCRUD(c);
    }

    @Override
    public PerfilViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new RowViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final PerfilViewHolder holder, final int position) {

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(mContext,photos.get(position).getPath(),Toast.LENGTH_SHORT);
                Log.d("LOng click: ", "true");
                /*Share INtent*/
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                Uri photoUri = Uri.parse(photos.get(position).getPath());
                shareIntent.setData(photoUri);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
                mContext.startActivity(Intent.createChooser(shareIntent, "Share using: "));
                /**/
                return true;
            }
        });

        ArrayList<Materia> materia;
        materia = crud.getMaterias(photos.get(position).getId_materia());
        Materia aux = materia.get(0);

        holder.tvMater.setText(photos.get(position).getId_materia() + " " +aux.getNombre());
        //holder.tvMater.setText(photos.get(position).getId_photos());
        //GlideApp.with().load(url).centerCrop().into(holder.ivPhoto);
        Glide.with(this.mContext).load(photos.get(position).getPath()).into(holder.ivPhoto);

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.ivMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_product);
               // crud = new ProductCRUD(view.getContext());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit_product:
                                //handle menu1 click
                                // Toast.makeText(getContext(), position, Toast.LENGTH_LONG).show();
                                Intent inten = new Intent(view.getContext(), EditActivity.class);
                                inten.putExtra("materia", ""+photos.get(position).getId_materia());
                                Log.d("ID:",""+photos.get(position).getId_materia());
                                inten.putExtra("url",photos.get(position).getPath());
                                view.getContext().startActivity(inten);
                                break;

                            case R.id.action_delete_product:
                                crud.deletePhoto(photos.get(position));
                                photos.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });


    }



    @Override
    public int getItemCount() {
        return photos.size();
    }




    public static class PerfilViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPhoto, ivMenu;
        private TextView tvDate;
        private TextView tvMater;

        PerfilViewHolder(View vistaElemento){
            super(vistaElemento);
            ivPhoto = (ImageView) vistaElemento.findViewById(R.id.product_photo);
            ivMenu = (ImageView) vistaElemento.findViewById(R.id.overflow);
            tvMater = (TextView) vistaElemento.findViewById(R.id.product_name);
            tvDate = (TextView) vistaElemento.findViewById(R.id.product_price);
        }
    }

}
