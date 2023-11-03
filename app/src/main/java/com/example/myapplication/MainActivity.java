package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static String BROKER_URL = "tcp://10.4.116.3:1883";
    private static final String CLIENT_ID = "1";

    private static String TOPIC = "topic";
    private MqttHandler mqttHandler;

    private Button setIpButton;
    private Button setTopicButton;
    private Button sendMessageButton;
    private EditText ipEditText;
    private EditText topicEditText;
    private EditText messageEditText;
    private TextView ipTextView;
    private TextView topicTextView;


    CustomMqttCallback mqttCallback = new CustomMqttCallback(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIpButton = findViewById(R.id.ip_button);
        setTopicButton = findViewById(R.id.topic_button);
        sendMessageButton = findViewById(R.id.message_button);
        ipEditText = findViewById(R.id.ip_edittext);
        topicEditText = findViewById(R.id.topic_editText);
        messageEditText = findViewById(R.id.message_edittext);
        ipTextView = findViewById(R.id.ip_textview);
        topicTextView = findViewById(R.id.topic_textview);

        /*
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL, CLIENT_ID);

        if (mqttHandler.isConnected()) {
            Toast.makeText(this, "Connected to MQTT broker", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to MQTT broker", Toast.LENGTH_SHORT).show();
        }
        publishMsg("topic", "Connected from mobile");

         */

        ipTextView.setText("Current IP: " + BROKER_URL.substring(6));

        setIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipTemp = ipEditText.getText().toString();
                if(!ipTemp.equals("")){
                    BROKER_URL = "tcp://" + ipTemp;
                    ipTextView.setText("Current IP: " + BROKER_URL.substring(6));
                    if(mqttHandler == null || mqttHandler.isConnected()){
                        mqttHandler.disconnect();
                    }
                    mqttHandler = new MqttHandler();
                    mqttHandler.connect(BROKER_URL, CLIENT_ID);
                    if (mqttHandler.isConnected()) {
                        Toast.makeText(MainActivity.this, "Connected to MQTT broker", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Not connected to MQTT broker", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    setIpButton.setError("IP Field Empty");
                }
            }
        });

        topicTextView.setText("Current topic: " + TOPIC);
        setTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topicTemp = topicEditText.getText().toString();
                if (!topicTemp.equals("")) {
                    TOPIC = topicTemp;
                    topicTextView.setText("Current topic: " + TOPIC);
                }
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTemp = messageEditText.getText().toString();
                if(!messageTemp.equals("")){
                    publishMsg(TOPIC, messageTemp);
                }else{
                    sendMessageButton.setError("Message field blank");
                }
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