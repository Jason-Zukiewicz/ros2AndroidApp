package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private Button forwardButton;
    private Button backButton;
    private Button leftButton;
    private Button rightButton;


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

        forwardButton = findViewById(R.id.forward_button);
        backButton = findViewById(R.id.back_button);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);



        mqttHandler = new MqttHandler();
        /*
        mqttHandler.connect(BROKER_URL, CLIENT_ID);

        if (mqttHandler.isConnected()) {
            Toast.makeText(this, "Connected to default MQTT broker", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to MQTT broker", Toast.LENGTH_SHORT).show();
        }
        */

        ipTextView.setText("Current IP: N/A");

        setIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipTemp = ipEditText.getText().toString();
                if(!ipTemp.equals("")){
                    BROKER_URL = "tcp://" + ipTemp;
                    if(!mqttHandler.isConnected()){
                        mqttHandler.connect(BROKER_URL, CLIENT_ID);
                    }
                    else if (mqttHandler.updateBrokerUrl(BROKER_URL, CLIENT_ID)) {
                        Toast.makeText(MainActivity.this, "Connected to MQTT broker", Toast.LENGTH_SHORT).show();
                        ipTextView.setText(String.format("Current IP: %s", ipTemp));
                        setIpButton.setError(null);
                    } else {
                        //Toast.makeText(MainActivity.this, "Not connected to MQTT broker", Toast.LENGTH_SHORT).show();
                        setIpButton.setError("Unable to connect to broker");
                        ipTextView.setText("Current IP: N/A");
                    }
                }else{
                    setIpButton.setError("IP Field Empty");
                }
            }
        });

        topicTextView.setText(String.format("Current topic: " + TOPIC));
        setTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topicTemp = topicEditText.getText().toString();
                if (!topicTemp.equals("")) {
                    TOPIC = topicTemp;
                    topicTextView.setText(String.format("Current topic: " + TOPIC));
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

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                publishMsg("mqtt_topic", "12");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                publishMsg("mqtt_topic", "6");
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                publishMsg("mqtt_topic", "9");
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                publishMsg("mqtt_topic", "3");
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