package com.example.jessezhou.DrawbotRemote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    // Bluetooth Components
    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> pairedDevices;

    private Button bList;
    private ListView listView;

    //Handler to handle background thread work
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init(){

        bList = (Button)findViewById(R.id.bList);
        listView = (ListView)findViewById(R.id.listView);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public void on(View view){
        if(!btAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on"
                    , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View view){
        btAdapter.disable();
        Toast.makeText(getApplicationContext(), "Bluetooth Off", Toast.LENGTH_LONG).show();
    }

    public void list(View view){
        pairedDevices = btAdapter.getBondedDevices();

        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices)
            list.add(bt.getName() + "\n" + bt.getAddress());

        final Object[] btArray = pairedDevices.toArray();

        Toast.makeText(getApplicationContext(),"Showing Paired Devices", Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openController = new Intent(MainActivity.this, ControllerActivity.class);
                openController.putExtra("DESIRED_DEVICE", (BluetoothDevice)btArray[position]);
                MainActivity.this.startActivity(openController);
            }
        });

    }

}
