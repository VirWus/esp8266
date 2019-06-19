package fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import design.ws.com.Esp8266App.R;



public class Doors_Fragment extends Fragment {


    private View view;

    public static String responce,state1,state2;

    private CircleProgress circleProgress;
    private ArcProgress temp,hum,per;
    private Handler handler = new Handler();
    private int tempi,humi,perso;
    private final int f = 4000;
    private final int r = 500;
    private ToggleButton Door1,Door2;
    private GoogleApiClient client ;
    String temper = null,humid = null,pers = null;
    private String AdressIp = "192.168.1.8";
    private Boolean cancel ;
    private ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doors, container, false);

        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        Door1 = (ToggleButton) view.findViewById(R.id.toggleButton2);
        Door2 = (ToggleButton) view.findViewById(R.id.toggleButton);
        temp = (ArcProgress) view.findViewById(R.id.temperature);
        hum = (ArcProgress) view.findViewById(R.id.humidity);
        per = (ArcProgress) view.findViewById(R.id.person);

        dialog = new ProgressDialog(getContext());
        cancel = false;
        GetEtat();
        Gettemperatue();

        Door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String led1Status ;
                String serverAdresses =  AdressIp + ":" + "80";
                if(Door1.isChecked()){
                    led1Status = "door?state=open";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"on",Toast.LENGTH_SHORT).show();
                }
                else{
                    led1Status = "door?state=close";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String led1Status ;
                String serverAdresses =  AdressIp + ":" + "80";
                if(Door2.isChecked()){
                    led1Status = "door1?state=open";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"on",Toast.LENGTH_SHORT).show();
                }
                else{
                    led1Status = "door1?state=close";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"off",Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;



    }
    private void GetEtat() {
        handler.postDelayed(new Runnable() {
            public void run() {

                if ((state1 != null) && (!cancel)) {
                    dialog.hide();
                    if (state1.toString().equals("open")) {
                        state1 = "open";
                        Door1.setChecked(true);
                    } else {
                        state1 = "close";
                        Door1.setChecked(false);
                    }

                    if (state2 != null) {
                        if (state2.toString().equals("open")) {
                            state2 = "open";
                            Door2.setChecked(true);
                        } else {
                            state2 = "close";
                            Door2.setChecked(false);
                        }
                    }
                    cancel = true;
                }

            }
        }, f);


    }

    public void Gettemperatue() {
        handler.postDelayed(new Runnable() {
            public void run() {

                gettemp();
                Gettemperatue();
                GetEtat();

            }
        }, f);

    }

    private void gettemp() {

        String serverAdresses =  AdressIp + ":" + "80";

        HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
        String te = "data";
        requestTask.execute(te);
        try {


            if (responce != null) {

                String[] all = responce.split(" ");

                        temper = all[0];

                        humid = all[1];

                        pers = all[2];

                        state1 = all[5];

                        state2 = all[6];
                }

            tempi = Integer.parseInt(temper);
            humi = Integer.parseInt(humid);
            perso = Integer.parseInt(pers);

        }catch(NumberFormatException nfe) {

        }


        temp.setProgress(tempi);
        hum.setProgress(humi);
        per.setProgress(perso);


    }


    public static class HttpRequestTask extends AsyncTask<String,Void,String> {

        private String serverAddress;
        private String serverResponce;
        // private String LedNum;
        private AlertDialog dialog;

        public HttpRequestTask(String serverAddress){
            this.serverAddress = serverAddress;

        }

        @Override
        protected String doInBackground(String... params) {

            String val = params[0];

            final String myUrl = "http://"+serverAddress+"/"+val;
            try {

                URL url = new URL(myUrl);

                // create HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // make GET request to the given URL
                conn.connect();

                // receive response as inputStream
                InputStream inputStream = conn.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                String line = "";
                String result = "";


                while ((line = dataInputStream.readLine()) != null)
                {
                    result += line;
                }
                responce = result;
                dataInputStream.close();

                conn.disconnect();
                // convert inputstream to string
                if(inputStream != null)
                    serverResponce = convertInputStreamToString(inputStream);
                else
                    serverResponce = "Did not work!";
            }

            catch (IOException e)
            {
                e.printStackTrace();
                serverResponce = e.getMessage();
            }

            return serverResponce;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

    }




}
