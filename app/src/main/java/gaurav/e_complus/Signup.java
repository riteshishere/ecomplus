package gaurav.e_complus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private static final String TAG="Signup";
    private EditText name,email,pass,phone;
    private TextView dob;
    private ImageView today;
    private Button btn;
    private ProgressDialog loadingBar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("SignUp");
        actionBar.setDisplayShowHomeEnabled(true);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        phone=findViewById(R.id.phone);
        dob=findViewById(R.id.dob);
        today=findViewById(R.id.today);
        btn=findViewById(R.id.btn);
        loadingBar = new ProgressDialog(this);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String passwordPattern ="[A-Za-z0-9]+";

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Signup.this,
                        android.R.style.Theme_Holo_Light_Dialog,date,day,month,year);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month+=1;
                Log.d(TAG,"onDateSet: dd/mm/yyyy"+ day+"-"+month+"-"+year);
                String date=day+"-"+month+"-"+year;
                dob.setText(date);
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((name.getText().toString().isEmpty())||(email.getText().toString().isEmpty())||
                        (pass.getText().toString().isEmpty())||(phone.getText().toString().isEmpty())||
                        (dob.getText().toString().isEmpty())) {
                    Toast.makeText(Signup.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.getText().toString()).matches(emailPattern)){
                    Toast.makeText(Signup.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                }

                else if (pass.getText().toString().length()<8) {
                    Toast.makeText(getApplicationContext(), "Minimum password length should be 8.",
                            Toast.LENGTH_LONG).show();
                }

                else if(phone.getText().toString().length()!=10){
                    Toast.makeText(Signup.this, "Please enter valid mobile number.", Toast.LENGTH_SHORT).show();
                }
                else {
                    createAccout();
                }
            };
        });
    }

    private void createAccout() {
        String InputName = name.getText().toString();
        String InputEmail = email.getText().toString();
        String InputPass = pass.getText().toString();
        String InputPhone = phone.getText().toString();
        String InputDob = dob.getText().toString();

        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("Please wait while we checking the credentials");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        validatePhoneNumber(InputName, InputPass, InputEmail, InputPhone, InputDob);
    }

    private void validatePhoneNumber(final String inputName, final String inputPass, final String inputEmail, final String inputPhone, final String inputDob) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(inputPhone).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Name", inputName);
                    userDataMap.put("UserId", inputEmail);
                    userDataMap.put("Pass", inputPass);
                    userDataMap.put("Phone", inputPhone);
                    userDataMap.put("DOB", inputDob);

                    RootRef.child("Users").child(inputPhone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Signup.this, "Congratulation, your account has been created !", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(Signup.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(Signup.this, "Network error: Please try after sometime...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(Signup.this, "This " + inputPhone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Signup.this, "Please try again using another number.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
