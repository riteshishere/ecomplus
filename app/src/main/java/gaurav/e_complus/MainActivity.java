package gaurav.e_complus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button signup,login,skip;
    static final int REQUEST_CODE=1;

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Homepage.class);
                startActivity(intent);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        +ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.SEND_SMS,Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
                    }
            }
        });

    }

}
