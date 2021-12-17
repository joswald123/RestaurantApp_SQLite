package usa.sesion1.restaurantappreto3.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.model.Sucursal;

public class AdaptadorSucursales
        extends RecyclerView.Adapter<AdaptadorSucursales.ViewHolderSucursales>
        implements View.OnClickListener{

    ArrayList<Sucursal> sucursales;
    private View.OnClickListener listener;

    public AdaptadorSucursales(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }


    @Override
    public ViewHolderSucursales onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sucursal, null, false);
        vista.setOnClickListener(this);
        return new ViewHolderSucursales(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorSucursales.ViewHolderSucursales holder, int position) {
        Picasso.get().load(sucursales.get(position).getImagen()).into(holder.imvImagenSuc);
        //holder.imvImagenSuc.setImageResource(sucursales.get(position).getImagen());
        holder.tvwNombreSuc.setText(sucursales.get(position).getNombre());
        holder.tvwDireccionSuc.setText(sucursales.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return sucursales.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderSucursales extends RecyclerView.ViewHolder{

        ImageView imvImagenSuc;
        TextView tvwNombreSuc;
        TextView tvwDireccionSuc;

        public ViewHolderSucursales(@NonNull View itemView) {
            super(itemView);
            imvImagenSuc = (ImageView) itemView.findViewById(R.id.itemImageSuc);
            tvwNombreSuc = (TextView) itemView.findViewById(R.id.itemNombreSuc);
            tvwDireccionSuc = (TextView) itemView.findViewById(R.id.itemDireccionSuc);
        }
    }
}