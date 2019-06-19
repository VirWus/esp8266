package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;

import design.ws.com.Esp8266App.R;



public class Remote_Fragment extends Fragment {


    private View view;


    private CircleProgress circleProgress;
    private ArcProgress arcProgress;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_remote, container, false);




        return view;



    }



}
