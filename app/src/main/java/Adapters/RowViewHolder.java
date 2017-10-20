package Adapters;

import android.view.View;

/**
 * Created by Raul on 11/10/2017.
 */

public class RowViewHolder extends RecycleViewCustomAdapter.PerfilViewHolder implements View.OnClickListener {
    private RecyclerViewClickListener listener;

    public RowViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}