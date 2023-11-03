package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String BROKER_URL = "tcp://10.4.116.3:1883";
    private static final String CLIENT_ID = "1";
    private MqttHandler mqttHandler;

    private Button test_button;

    CustomMqttCallback mqttCallback = new CustomMqttCallback(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_button = findViewById(R.id.test_button);

        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL, CLIENT_ID);

        if (mqttHandler.isConnected()) {
            Toast.makeText(this, "Connected to MQTT broker", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to MQTT broker", Toast.LENGTH_SHORT).show();
        }
        publishMsg("topic", "ON MOBILE");

        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMsg("pingpong/primitive", "69");
            }
        });
    }
    @Override
    protected void onDestroy(){
        mqttHandler.disconnect();
        super.onDestroy();
    }

    private void publishMsg(String topic, String msg){
        Toast.makeText(this, "Publishing Message: " + msg, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic, msg);
    }

    public void subscribeToTopic(String topic, CustomMqttCallback callback) {
        Toast.makeText(this, "Subscribing to: " + topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic, callback);
    }



}