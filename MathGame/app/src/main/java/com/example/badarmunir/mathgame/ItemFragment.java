package com.example.badarmunir.mathgame;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.badarmunir.mathgame.dummy.DummyContent.DummyItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;




    // this arrayList is used to store player objects
    private ArrayList<Player> players = new ArrayList<>();

    // Spinner
  //  Spinner spinner4;

    // this is used to set players
    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    // this is used to add players to the arraylist
    public void addPlayers(Player player)
    {
        players.add(player);
    }


    public void clearPlayerArrayList()
    {
        players.clear();
    }
    // this is used to return the highest score.
    public int getHighestScore()
    {
        if (players.size() == 0)
        {
            return 0;
        }
        else {
            Player player = players.get(0);
            return player.getScore();
        }
    }



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main_drawer, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);





        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPlayerAdapter(players));
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        //Log.d("blah", "got here" );
        // every time this fragment is loaded it clears the array list and read the data from file.
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }




    // this saves data to a file
    // it takes 3 parameters
    // first it takes the content which is the user name, and score which is comma separated
    // second it takes the filename of the file where the data is going to be stored
    // finally it takes the context
    public void saveToFile2(String fileContents, String fileName, Context context) {
      //  Context context = getActivity();
        Log.d("ID","file dir = " + context.getFilesDir());
        try {
            File fp = new File(context.getFilesDir(), fileName);
            FileWriter out = new FileWriter(fp, true);
            out.write(fileContents + System.getProperty("line.separator"));
            out.close();
        } catch (IOException e) {
            Log.d("Me","file error:" + e);
        }
    }

    // this function is used to sort the data so the recycler view show the player with highest score at the top
    public void sortData()
    {
        Collections.sort(players, Collections.reverseOrder(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if (o1.getScore() > o2.getScore())
                {
                    return 1;
                }
                else if (o1.getScore() < o2.getScore())
                {
                    return -1;
                }


                return 0;
            }
        }));
    }





    // this reads from a file
    // first it takes the filename to load the data from.
    // then it takes the context.
    public String readFromFile(String fileName, Context context) {
      //  Context context = getActivity();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;
        try {
            File fp = new File(context.getFilesDir(), fileName);

            //Log.d("asd","asda");
            in = new BufferedReader(new FileReader(fp));
            // we read a line and then split it into tokens which is store in a local area
            // and then they are used to create Player objects
            String[] tokens;
            while ((line = in.readLine()) != null) {
                // the split function separate the data and assign it to tokens
                 tokens = line.split(",");
                 // this adds it to the arraylist
                addPlayers(new Player(tokens[0],Integer.parseInt(tokens[1])));
                //stringBuilder.append(line);
            }
        }
        catch (FileNotFoundException e) {
            Log.d("ID", e.getMessage());
        }
        catch (IOException e) {
            Log.d("ID", e.getMessage());
        }

        Log.d("ID", stringBuilder.toString());
        // calls sort function to sort the data every time data is loaded.
        sortData();
        return stringBuilder.toString();
    }
}


// this is the adapter class that extends the recycler view.
class MyPlayerAdapter extends RecyclerView.Adapter {

ArrayList<Player> players;


    // this constructor set up the array list.
    public MyPlayerAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_layout,viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    // this is used to show data for each player.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.name.setText(players.get(i).getName() );
        // depending on the position you get a different trophy
        if (i == 0)
        {
            myViewHolder.imageView.setImageResource(R.drawable.first);
        }
        else if (i == 1)
        {
            myViewHolder.imageView.setImageResource(R.drawable.second);
        }
        else if (i == 2)
        {
            myViewHolder.imageView.setImageResource(R.drawable.third);
        }
        else
        {
            myViewHolder.imageView.setImageResource(R.drawable.fourth);
        }
        myViewHolder.score.setText(players.get(i).getScore() + "" );

    }

    @Override
    public int getItemCount() {
        return players.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView score;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Name);
            score =  (TextView) itemView.findViewById(R.id.Score);
            imageView = (ImageView) itemView.findViewById(R.id.trophyImage);
        }
    }
}










