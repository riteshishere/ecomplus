package gaurav.e_complus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText userid,pass;
    Button btn;
    public static final String PREF="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayShowHomeEnabled(true);

        userid=findViewById(R.id.userid);
        pass=findViewById(R.id.pass);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userid.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please enter user id.", Toast.LENGTH_SHORT).show();
                }
                else if (pass.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name=userid.getText().toString().trim();
                    Intent intent=new Intent(Login.this,Homepage.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Welcome "+name+" !", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor=getSharedPreferences(PREF,MODE_PRIVATE).edit();
                    editor.putString("user",name);
                    editor.commit();
                }
            }
        });

        SharedPreferences prefs=getSharedPreferences(PREF,MODE_PRIVATE);
        String user=prefs.getString("user","");
        userid.setText(user);
    }
}
