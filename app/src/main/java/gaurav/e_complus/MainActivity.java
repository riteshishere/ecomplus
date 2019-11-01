package gaurav.e_complus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gaurav.e_complus.Model.Users;
import gaurav.e_complus.Prevalent.Prevalent;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button signup,login,skip;
    static final int REQUEST_CODE=1;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setTitle("   E-com Plus");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        skip=findViewById(R.id.skip);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);
        String UserPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if(UserPhoneKey!="" && UserPasswordKey!=""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){
                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("Please wait ......");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccess(UserPhoneKey, UserPasswordKey);
            }
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Homepage.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                }
            }
        });

    }

    private void AllowAccess(final String inputPhone, final String inputPass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(inputPhone).exists()){
                    Users userData = dataSnapshot.child("Users").child(inputPhone).getValue(Users.class);

                    if(userData.getPhone().equals(inputPhone)){
                        if(userData.getPass().equals(inputPass)){
                            Toast.makeText(MainActivity.this, "Hey "+userData.getName()+", Welcome back", Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                            Intent intent=new Intent(MainActivity.this,Homepage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong Credentials, Please try again ...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Account with this "+inputPhone+" does not exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Please create new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
