package com.example.badarmunir.mathgame;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton submitButton;
    // this is to get the user name
    EditText userName;
    // this is used to display text to the user.
    TextView textView;

    TextView welcomeMessage;
    ConstraintLayout coordinatorLayout;


    // sensor
    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyroscopeListener;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //  test = findViewById(R.id.content_main);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null)
        {
            Toast.makeText(getContext(),"this mobile phone has haha no gyroscope",Toast.LENGTH_SHORT).show();
        }



        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[2] > 0.5f)
                {
                    Log.d("g","is here");
                    startNewActivity();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };







        coordinatorLayout = getView().findViewById(R.id.home_layout);
        final Snackbar snackbar = Snackbar.make(coordinatorLayout,"press button or tilt left to go next",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        snackbar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

     //   welcomeMessage = view.findViewById(R.id.titleName);

        // get reference to the attributes
        submitButton = view.findViewById(R.id.saveButton);
        userName = view.findViewById(R.id.readName);
        textView = view.findViewById(R.id.showMessage);

        // this click listener is used to check when the user click the save button

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity();
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }





    public void startNewActivity()
    {
        // if statement to check the user has typed in a appropriate name
        // if he has then creates an intent object and start the next activity
        // it also finish this activity
        if (validateUserInput() >= 3)
        {
          //  welcomeMessage.setText(userName.getText().toString());
            Intent selectGameModePage = new Intent(getActivity(),GameMode.class);
            selectGameModePage.putExtra("name",userName.getText().toString());
            startActivity(selectGameModePage);
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else
        {
            // shows this error if the name is 2 characters or less
            userName.setError("please type your name");
        }
    }


    // this is to get the length of the edit text page
    public int validateUserInput()
    {
        return userName.getText().toString().length();
    }




    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeListener);
    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
