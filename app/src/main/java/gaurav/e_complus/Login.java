package gaurav.e_complus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gaurav.e_complus.Model.Users;
import gaurav.e_complus.Prevalent.Prevalent;
import gaurav.e_complus.Prevalent.RememberUser;
import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private EditText phone,pass,pass1,phone1;
    private Button btn;
    public static final String PREF="";
    private ProgressDialog loadingBar;
    private CheckBox chkBoxRememberMe ;
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
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chk);
        Paper.init(this);
        previousUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((phone.getText().toString().isEmpty())||(pass.getText().toString().isEmpty())) {
                    Toast.makeText(Login.this, "Please fill all details.", Toast.LENGTH_SHORT).show();
                }
                else if(phone.getText().toString().length()!=10){
                    Toast.makeText(Login.this, "Please enter valid mobile number.", Toast.LENGTH_SHORT).show();
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

                if (chkBoxRememberMe.isChecked()){
                    Paper.book().write(RememberUser.userPhone,inputPhone);
                    Paper.book().write(RememberUser.userPassword,inputPass);
                }
                else{
                    /* Do nothing for now */
                }

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(parentDbName).child(inputPhone).exists()){
                            Users userData = dataSnapshot.child(parentDbName).child(inputPhone).getValue(Users.class);

                            if(userData.getPhone().equals(inputPhone)){
                                if(userData.getPass().equals(inputPass)){

                                    Paper.book().write(Prevalent.userPhoneKey,inputPhone);
                                    Paper.book().write(Prevalent.userPasswordKey,inputPass);
                                    Paper.book().write(Prevalent.userName,userData.getName());

                                    Toast.makeText(Login.this, "Welcome "+userData.getName()+" !", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();


                                    Intent intent=new Intent(Login.this,Homepage.class);
                                    intent.putExtra("user",userData.getName());
                                    intent.putExtra("phone",userData.getPhone());
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

    private void previousUser() {
        phone1= (EditText) findViewById(R.id.phone);
        pass1= (EditText) findViewById(R.id.pass);

        Paper.init(this);
        String userPhone = Paper.book().read(RememberUser.userPhone);
        String userPass = Paper.book().read(RememberUser.userPassword);

        phone1.setText(userPhone);
        pass1.setText(userPass);
    }
}
