package gaurav.e_complus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText name,userid,pass,phone,dob;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("SignUp");
        actionBar.setDisplayShowHomeEnabled(true);

        name=findViewById(R.id.name);
        userid=findViewById(R.id.userid);
        pass=findViewById(R.id.pass);
        phone=findViewById(R.id.phone);
        dob=findViewById(R.id.dob);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name.getText().toString().isEmpty())||(userid.getText().toString().isEmpty())||
                        (pass.getText().toString().isEmpty())||(phone.getText().toString().isEmpty())||
                        (dob.getText().toString().isEmpty())) {
                    Toast.makeText(Signup.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String n=name.getText().toString().trim();

                    Toast.makeText(Signup.this, "User registered successfully.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
