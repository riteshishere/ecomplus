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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gaurav.e_complus.Model.Users;

public class Login extends AppCompatActivity {

    private EditText phone,pass;
    private Button btn;
    public static final String PREF="";
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayShowHomeEnabled(true);

        phone= (EditText) findViewById(R.id.phone);
        pass= (EditText) findViewById(R.id.pass);
        btn= (Button) findViewById(R.id.btn);
        loadingBar = new ProgressDialog(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please enter Phone Number.", Toast.LENGTH_SHORT).show();
                }
                else if (pass.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser();
                }
            }

            private void loginUser() {
                String inputPhone= phone.getText().toString();
                String inputPass = pass.getText().toString();

                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait while we checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                allowAccessToAccount(inputPhone, inputPass);
            }

            private void allowAccessToAccount(final String inputPhone, final String inputPass) {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(parentDbName).child(inputPhone).exists()){
                            Users userData = dataSnapshot.child(parentDbName).child(inputPhone).getValue(Users.class);

                            if(userData.getPhone().equals(inputPhone)){
                                if(userData.getPass().equals(inputPass)){
                                    Toast.makeText(Login.this, "Welcome "+userData.getName()+" !", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent=new Intent(Login.this,Homepage.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Credentials, Please try again ...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }
                        }
                        else{
                            Toast.makeText(Login.this, "Account with this "+inputPhone+" does not exist.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(Login.this, "Please create new account", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }
}
