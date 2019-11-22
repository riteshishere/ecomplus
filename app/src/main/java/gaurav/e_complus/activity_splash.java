package gaurav.e_complus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gaurav.e_complus.Model.Users;
import gaurav.e_complus.Prevalent.Prevalent;
import io.paperdb.Paper;

public class activity_splash extends AppCompatActivity {
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Paper.init(this);
        String UserPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                AllowAccess(UserPhoneKey, UserPasswordKey);
            }
            else{
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(activity_splash.this,MainActivity.class);
                        startActivity(intent);
                    }
                },4000);
            }
        }
        else{
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(activity_splash.this,MainActivity.class);
                    startActivity(intent);
                }
            },4000);
        }
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
    }

    private void AllowAccess(final String inputPhone, final String inputPass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(inputPhone).exists()) {
                    Users userData = dataSnapshot.child("Users").child(inputPhone).getValue(Users.class);

                    if (userData.getPhone().equals(inputPhone)) {
                        if (userData.getPass().equals(inputPass)) {
                            Toast.makeText(activity_splash.this, "Hey " + userData.getName() + ", Welcome back", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity_splash.this, Homepage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activity_splash.this, "Wrong Credentials, Please try again ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(activity_splash.this, "Account with this " + inputPhone + " does not exist.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity_splash.this, "Please create new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}