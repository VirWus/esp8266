package fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import design.ws.com.Esp8266App.JSONParser;
import design.ws.com.Esp8266App.R;

import static com.google.android.gms.internal.zzahn.runOnUiThread;


public class Light_Fragment extends Fragment {


    private View view;

    public static String responce,state1,state2;

    private CircleProgress circleProgress;
    private ArcProgress temp,hum,per;
    private Handler handler = new Handler();
    private int tempi,humi,perso;
    private final int f = 4000;
    private final int r = 1000;
    private ToggleButton Led1,Led2;
    private GoogleApiClient client ;
    String temper = null,humid = null,pers = null;
    private String AdressIp = "192.168.1.8";
    private Boolean cancel ;
    private ProgressDialog pDialog;

    String pid;
    JSONParser jsonParser = new JSONParser();
    private static final String url_pendaftaran_details = "http://192.168.1.7/lab-tutor/pendaftaran/get_details.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PENDAFTARAN = "test";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.clean_fragment, container, false);
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Led1 = (ToggleButton) view.findViewById(R.id.toggleButton2);
        Led2 = (ToggleButton) view.findViewById(R.id.toggleButton);
        temp = (ArcProgress) view.findViewById(R.id.temperature);
        hum = (ArcProgress) view.findViewById(R.id.humidity);
        per = (ArcProgress) view.findViewById(R.id.person);
        pDialog = new ProgressDialog(getContext());
        cancel = false;
        GetEtat();
        Gettemperatue();


        Led1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String led1Status ;
                String serverAdresses =  AdressIp + ":" + "80";
                if(Led1.isChecked()){
                    led1Status = "led?state=on";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"on",Toast.LENGTH_SHORT).show();
                }
                else{
                    led1Status = "led?state=off";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"off",Toast.LENGTH_SHORT).show();
                }


            }
        });

        Led2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String led1Status ;
                String serverAdresses =  AdressIp + ":" + "80";
                if(Led2.isChecked()){
                    led1Status = "led1?state=on";
                    HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
                    requestTask.execute(led1Status);
                    Toast.makeText(getContext(),"on",Toast.LENGTH_SHORT).show();
                }
                else{
                    led1Status = "led1?state=off";
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

                    if (state1.toString().equals("HIGH")) {
                        state1 = "HIGH";
                        Led1.setChecked(true);
                    } else {
                        state1 = "LOW";
                        Led1.setChecked(false);
                    }

                    if (state2 != null) {
                        if (state2.toString().equals("HIGH")) {
                            state2 = "HIGH";
                            Led2.setChecked(true);
                        } else {
                            state2 = "LOW";
                            Led2.setChecked(false);
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

            String serverAdresses = AdressIp + ":" + "80";

            HttpRequestTask requestTask = new HttpRequestTask(serverAdresses);
            String te = "data";
            requestTask.execute(te);
            try {


                if (responce != null) {
                    String[] all = responce.split(" ");
                    temper = all[0];
                    humid = all[1];
                    pers = all[2];
                    state1 = all[3];
                    state2 = all[4];
                }

                tempi = Integer.parseInt(temper);
                humi = Integer.parseInt(humid);
                perso = Integer.parseInt(pers);

            } catch (NumberFormatException nfe) {

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
                /*if(inputStream != null)
                    serverResponce = convertInputStreamToString(inputStream);
                else
                    serverResponce = "Did not work!";*/
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



    /**
     * run get all product in background
     * */
    class GetDetails extends AsyncTask<String, String, String> {

        /**
         * if start get activity then run progress dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Retrieving Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * start run get all list and run in background
         * */
        protected String doInBackground(String... params) {

            // ui updates from threads executed
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check tag success
                    int success;
                    try {
                        // create paramater
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // get details from the list using http request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_pendaftaran_details, "GET", params);

                        // check our logs with json response
                        Log.d("Single Product Details", json.toString());

                        // tag success json
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // success gets detail list
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PENDAFTARAN); // JSON
                            // Array

                            // get first list object from json array
                            JSONObject pendaftaran = productObj
                                    .getJSONObject(0);

                            // display in edit text
                            txtName.setText(pendaftaran.getString(TAG_NAME));
                            txtEmail.setText(pendaftaran.getString(TAG_EMAIL));
                            txtDesc.setText(pendaftaran
                                    .getString(TAG_DESCRIPTION));

                        } else {
                            // pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * If the job in the background is complete then stop progress reply
         * running
         * **/
        protected void onPostExecute(String file_url) {
            // stop progress dialog for get
            pDialog.dismiss();
        }
    }


}
