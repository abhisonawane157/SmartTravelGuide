package com.example.smarttravelguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SpotDetailActivity extends AppCompatActivity {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
    private TextView name,category,address, spotdesc;
    private ImageView spotimg;
    private FloatingActionButton _fab;
    private SharedPreferences file;
    private AlertDialog.Builder dialog;

    //Database
    public final int REQ_CD_CAMERA = 101;
    private HashMap<String, Object> usermaps = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private ArrayList<HashMap<String, Object>> spotlistmap = new ArrayList<>();
    private ArrayList<String> spotliststring = new ArrayList<>();

    private DatabaseReference spot = _firebase.getReference("spot");
    private ChildEventListener _spot_child_listener;
    private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_camera;
//    private DatabaseReference users = _firebase.getReference("users");
//    private ChildEventListener _users_child_listener;

    private FirebaseAuth fauth;
    private OnCompleteListener<AuthResult> _fauth_create_user_listener;
    private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
    private OnCompleteListener<Void> _fauth_reset_password_listener;

    Intent intent = new Intent();
    private String spotid ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);

        name = findViewById(R.id.name);
        spotdesc = findViewById(R.id.spotdesc);
        spotimg = findViewById(R.id.imageview1);
        category = findViewById(R.id.spotcategory);
        address = findViewById(R.id.address);

        file = getSharedPreferences("file", Activity.MODE_PRIVATE);
        spotid = file.getString("spotid","");


        _spot_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.get("spotid").toString().equals(spotid))
                {
                    name.setText(_childValue.get("name").toString());
                    category.setText(_childValue.get("spotcategory").toString());
                    address.setText(_childValue.get("address").toString());
                    spotdesc.setText(_childValue.get("spotdesc").toString());

                    if (_childValue.get("spotimgurl").toString().equals("")) {
                        spotimg.setVisibility(View.GONE);
                    }
                    else {
                        spotimg.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("spotimgurl").toString())).into(spotimg);
                    }
                } else {
                }

            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.get("spotid").toString().equals(spotid))
                {
                    name.setText(_childValue.get("name").toString());
                    category.setText(_childValue.get("spotcategory").toString());
                    address.setText(_childValue.get("address").toString());
                    spotdesc.setText(_childValue.get("spotdesc").toString());
                    if (_childValue.get("spotimgurl").toString().equals("")) {
                        spotimg.setVisibility(View.GONE);
                    }
                    else {
                        spotimg.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("spotimgurl").toString())).into(spotimg);
                    }
                } else {
                }
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }


            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                if (_childValue.get("spotid").toString().equals(spotid))
                {
                    name.setText(_childValue.get("name").toString());
                    category.setText(_childValue.get("spotcategory").toString());
                    address.setText(_childValue.get("address").toString());
                    spotdesc.setText(_childValue.get("spotdesc").toString());
                    if (_childValue.get("spotimgurl").toString().equals("")) {
                        spotimg.setVisibility(View.GONE);
                    }
                    else {
                        spotimg.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("spotimgurl").toString())).into(spotimg);
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        spot.addChildEventListener(_spot_child_listener);
    }
}