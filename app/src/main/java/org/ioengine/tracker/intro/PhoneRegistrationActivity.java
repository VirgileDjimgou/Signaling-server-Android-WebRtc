package org.ioengine.tracker.intro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.ioengine.tracker.R;
import org.ioengine.tracker.intro.data.FriendDB;
import org.ioengine.tracker.intro.data.GroupDB;
import org.ioengine.tracker.intro.data.StaticConfig;
import org.ioengine.tracker.intro.model.User;
import org.ioengine.tracker.intro.service.ServiceUtils;


/**
 * Created by Djimgou Patrick
 * Created on 09-oct-17.
 */

public class PhoneRegistrationActivity extends AppCompatActivity {

    Button mStartButton ;
    private EditText  email_user , UserNameRegistration ;
    private Spinner DriverType;
    private FloatingActionButton CloseRegisterCarte;

    private static final String TAG = "PhoneAuthActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_registration);

        mStartButton = (Button) findViewById(R.id.bt_go);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String Driver_Type = DriverType.getSelectedItem().toString().trim();
                String Name = UserNameRegistration.getText().toString().trim();
                if(Name.length() < 3 ){
                    Toast.makeText(getApplicationContext(), "invalid Name  !" , Toast.LENGTH_LONG).show();

                }else {

                    User newUser = new User();
                    newUser.email = "no_emailPassenger@email.com";
                    newUser.phone = PhoneAuthActivity.user_Global.getPhoneNumber();
                    newUser.name = Name;
                    newUser.avata = StaticConfig.STR_DEFAULT_BASE64;
                    // FirebaseDatabase.getInstance().getReference().child("Driver/"+ user.getUid()).setValue(newUser);

                    FirebaseDatabase.getInstance().getReference().child("Users").child("Customers/"+ PhoneAuthActivity.user_Global.getUid()).setValue(newUser);
                    // save User Info and continue  normaly  ... user is already registerd
                    StaticConfig.UID = PhoneAuthActivity.user_Global.getUid();
                    Intent intent = new Intent(PhoneRegistrationActivity.this, MapActivity.class);
                    startActivity(intent);
                    PhoneRegistrationActivity.this.finish();

                }

            }
        });

        // Registration ...
        CloseRegisterCarte = (FloatingActionButton) findViewById(R.id.fab_close);
        CloseRegisterCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PhoneRegistrationActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Registration  Dialog")
                        .setMessage("Are you sure you want to stop the registration process and close the app ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try{

                                    FirebaseAuth.getInstance().signOut();
                                    FriendDB.getInstance(getApplicationContext()).dropDB();
                                    GroupDB.getInstance(getApplicationContext()).dropDB();
                                    ServiceUtils.stopServiceFriendChat(getApplicationContext(), true);
                                    finish();

                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                PhoneRegistrationActivity.this.finish();
            }
        });


        UserNameRegistration = (EditText) findViewById(R.id.username);
        email_user = (EditText) findViewById(R.id.email_user);

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(PhoneRegistrationActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Registration  Dialog")
                .setMessage("Are you sure you want to stop the registration process and close the app ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try{

                            FirebaseAuth.getInstance().signOut();
                            FriendDB.getInstance(getApplicationContext()).dropDB();
                            GroupDB.getInstance(getApplicationContext()).dropDB();
                            ServiceUtils.stopServiceFriendChat(getApplicationContext(), true);
                            PhoneRegistrationActivity.this.finish();

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
