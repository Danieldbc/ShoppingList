package edu.eseiaat.upc.pma.borreguero.daniel.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private static final String FILENAME="shoppingList.txt";
    private static final int MaxBites=8000;

    private ArrayList<ShoppingItem> itemlist;
    private ShoppingListAdaptaer adapter;
    private ListView list;
    private EditText editText;

    private void WriteItemList(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < itemlist.size(); i++) {
                ShoppingItem it = itemlist.get(i);
                String file = String.format("%s;%b", it.getTexto(), it.isCheck());
                fos.write(file.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.NoFile, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.NoWrite, Toast.LENGTH_SHORT).show();
        }
    }
    private void ReadItemList(){
        itemlist=new ArrayList<>();
        try {
            FileInputStream fis=openFileInput(FILENAME);
            byte[] bufer=new byte[MaxBites];
            int nread=fis.read(bufer);
            String content=new String(bufer,0,nread);
            String[] lines=content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(";");
                itemlist.add(new ShoppingItem(parts[0], parts[1].equals("true")));
            }
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            Toast.makeText(this, R.string.NoRead, Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        WriteItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        list = (ListView)findViewById(R.id.List);
        editText = (EditText)findViewById(R.id.EditText);

        itemlist=new ArrayList<>();
        adapter=new ShoppingListAdaptaer(this,R.layout.shopping_item,itemlist);
        //ReadItemList();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearCheck:
                clearcheched();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void clearcheched() {
        for (int i=0;i<itemlist.size();i++){
            ShoppingItem item=itemlist.get(i);
            if (itemlist.get(i).isCheck()){
                itemlist.remove(i);
                i--;
            }
        }
        adapter.notifyDataSetChanged();
    }
}
