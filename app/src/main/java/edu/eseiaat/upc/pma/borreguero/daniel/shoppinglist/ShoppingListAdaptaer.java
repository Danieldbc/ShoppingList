package edu.eseiaat.upc.pma.borreguero.daniel.shoppinglist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by PortatilDani on 20/10/2017.
 */

public class ShoppingListAdaptaer extends ArrayAdapter<ShoppingItem> {

    public ShoppingListAdaptaer(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result=convertView;
        if (result==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            result=inflater.inflate(R.layout.shopping_item,null);
        }
        CheckBox shoppingItem=(CheckBox)result.findViewById(R.id.ShoppingItem);
        ShoppingItem item=getItem(position);
        shoppingItem.setText(item.getTexto());
        shoppingItem.setChecked(item.isCheck());
        return result;

    }
}
