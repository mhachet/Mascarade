package com.mascarade.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.mascarade.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melanie on 3/27/16.
 */
public class RolesAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    public static final String ADAPTER_ROLES = "ROLES_ADAPTER";
    // Une liste de cartes
    private List<ListItem> listRoles = new ArrayList<>();

    //Le contexte dans lequel est présent notre adapter
    private Context rolesContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    //private LayoutInflater rolesInflater;

    public RolesAdapter(Context context, ArrayList<ListItem> listRoles) {
        this.rolesContext = context;
        this.listRoles = listRoles;
        //this.rolesInflater = LayoutInflater.from(context);
    }

    // retourne le nombre d'objet présent dans notre liste
    @Override
    public int getCount() {
        return listRoles.size();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public ListItem getItem(int position) {
        return listRoles.get(position);
    }

    // retourne l'id d'un élément de notre liste en fonction de sa position
    @Override
    public long getItemId(int position) {
        return listRoles.indexOf(getItem(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder = null;

        // au premier appel ConvertView est null, on inflate notre layout
        if (convertView == null) {

            //Log.d(ADAPTER_ROLES, "convert is null");
            LayoutInflater mInflater = (LayoutInflater) rolesContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.layout_image_cards, parent, false);

            // nous plaçons dans notre MyViewHolder les vues de notre layout
            mViewHolder = new MyViewHolder();
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageViewCardsLabel);

            // nous attribuons comme tag notre MyViewHolder à convertView
            convertView.setTag(mViewHolder);
        } else {
            //Log.d(ADAPTER_ROLES, "convert is not null");

            // convertView n'est pas null, nous récupérons notre objet MyViewHolder
            // et évitons ainsi de devoir retrouver les vues à chaque appel de getView
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        // nous récupérons l'item de la liste demandé par getView
        ListItem listItem = this.getItem(position);
        //Log.d(ADAPTER_ROLES, "listItem : " + listItem.getNameImage() + "  id : " + listItem.getImageId());
        mViewHolder.imageView.setImageResource(listItem.getImageId());

        // nous retournos la vue de l'item demandé
        return convertView;
    }

    // MyViewHolder va nous permettre de ne pas devoir rechercher
    // les vues à chaque appel de getView, nous gagnons ainsi en performance
    private class MyViewHolder {
        ImageView imageView;
    }

    // nous affichons un Toast à chaque clic sur un item de la liste
    // nous récupérons l'objet grâce à sa position
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(rolesContext, "Item " + (position + 1) + ": "
                + this.listRoles.get(position), Toast.LENGTH_SHORT);
        toast.show();

    }

}
