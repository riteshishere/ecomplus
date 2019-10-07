package gaurav.e_complus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private EditText name,userid,pass,phone,dob;
    private Button btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("SignUp");
        actionBar.setDisplayShowHomeEnabled(true);

        name= (EditText) findViewById(R.id.name);
        userid= (EditText) findViewById(R.id.userid);
        pass= (EditText) findViewById(R.id.pass);
        phone= (EditText) findViewById(R.id.phone);
        dob= (EditText) findViewById(R.id.dob);
        btn= (Button) findViewById(R.id.btn);
        loadingBar = new ProgressDialog(this);
/*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name.getText().toString().isEmpty())||(userid.getText().toString().isEmpty())||
                        (pass.getText().toString().isEmpty())||(phone.getText().toString().isEmpty())||
                        (dob.getText().toString().isEmpty())) {
                    Toast.makeText(Signup.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                else {


                }
            }
        });
*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name.getText().toString().isEmpty())||(userid.getText().toString().isEmpty())||
                        (pass.getText().toString().isEmpty())||(phone.getText().toString().isEmpty())||
                        (dob.getText().toString().isEmpty())) {
                    Toast.makeText(Signup.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    createAccount();
                }
            }
        });
    }

    private void createAccount() {
        String InputName = name.getText().toString();
        String InputUserId = userid.getText().toString();
        String InputPass = pass.getText().toString();
        String InputPhone = phone.getText().toString();
        String InputDob = dob.getText().toString();

        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("Please wait while we checking the credentials");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        validatePhoneNumber(InputName, InputPass, InputUserId, InputPhone, InputDob);
    }

    private void validatePhoneNumber(final String inputName, final String inputPass, final String inputUserId, final String inputPhone, final String inputDob) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(inputPhone).exists())  ){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Name", inputName);
                    userDataMap.put("UserId", inputUserId);
                    userDataMap.put("Pass", inputPass);
                    userDataMap.put("Phone", inputPhone);
                    userDataMap.put("DOB", inputDob);

                    RootRef.child("Users").child(inputPhone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "Congratulation, your account has been created !", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent=new Intent(Signup.this,Login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(Signup.this, "Network error: Please try after sometime...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(Signup.this, "This "+ inputPhone +" already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Signup.this, "Please try again using another combination.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
