package edu.eseiaat.upc.pma.borreguero.daniel.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private ArrayList<ShoppingItem> itemlist;
    private ShoppingListAdaptaer adapter;
    private ListView list;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        list = (ListView)findViewById(R.id.List);
        editText = (EditText)findViewById(R.id.EditText);

        itemlist=new ArrayList<>();
        adapter=new ShoppingListAdaptaer(this,R.layout.shopping_item,itemlist);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                itemlist.get(pos).changecheck();
                adapter.notifyDataSetChanged();

            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                AddItem();
                return true;
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> List, View view, int pos, long id) {
                mayberemoveitem(pos);
                return true;
            }
        });
    }

    private void mayberemoveitem(final int pos) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String sure=getResources().getString(R.string.sure);
        String item=itemlist.get(pos).getTexto();
        builder.setMessage(sure+" "+item);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemlist.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create().show();

    }

    public void add(View view) {
        AddItem();
    }

    private void AddItem() {
        String item=editText.getText().toString();
        if (!item.isEmpty()){
            itemlist.add(new ShoppingItem(item));
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
        list.smoothScrollToPosition(itemlist.size()-1);
    }
}
