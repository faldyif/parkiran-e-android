package id.markirin.parkirane;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class MonitoringFragment extends Fragment {

    ArrayList<ParkirSlot> dataModels;
    ListView listView;
    private static SlotAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String myBooking;

    public MonitoringFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MonitoringFragment.
     */
    public static MonitoringFragment newInstance() {
        MonitoringFragment fragment = new MonitoringFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        database.getReference("users").child(auth.getCurrentUser().getUid()).child("booking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myBooking = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monitoring, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        dataModels = new ArrayList<>();

        mDatabase.child("slots").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: " + s);
                ParkirSlot slot = dataSnapshot.getValue(ParkirSlot.class);
                dataModels.add(slot);
                adapter = new SlotAdapter(dataModels, getActivity().getApplicationContext());
                listView.setAdapter(adapter);
                Log.d(TAG, "onChildAdded: " + slot.getName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: " + s);
                int num = 0;
                if(s != null) {
                    num = Integer.parseInt(s);
                }
                ParkirSlot slot = dataSnapshot.getValue(ParkirSlot.class);
                dataModels.set(num, slot);
                adapter = new SlotAdapter(dataModels, getActivity().getApplicationContext());
                listView.setAdapter(adapter);
                Log.d(TAG, "onChildChanged: " + slot.getName());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new SlotAdapter(dataModels, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setEmptyView(rootView.findViewById(R.id.emptyElement));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(myBooking == null) {
                    if(dataModels.get(i).getReservedBy().equals("default")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Apakah anda yakin?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        mDatabase.child("slots").child(String.valueOf(i+1)).child("reservedBy").setValue(auth.getCurrentUser().getUid());
                                        mDatabase.child("slots").child(String.valueOf(i+1)).child("countdown").setValue(120);
                                        mDatabase.child("users").child(auth.getCurrentUser().getUid()).child("booking").setValue(String.valueOf(i+1));
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .show();
                    } else {
                        Toast.makeText(getActivity(), "Tempat ini sudah dibooking!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Anda memiliki booking aktif!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
