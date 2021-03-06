package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.User;
import project.topmovies.logic.statusApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp_Activity extends AppCompatActivity {

    // VARIABLES //

    EditText
            editText_Name,
            editText_LastName,
            editText_Email,
            editText_Password,
            editText_ConfirmPassword;

    Button button_SignUp;

    ProgressBar progressBar_SignUp;


    private FirebaseAuth mAuth;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Access to views
        editText_Name = findViewById(R.id.editText_SU_Name);
        editText_LastName = findViewById(R.id.editText_SU_LastName);

        editText_Email = findViewById(R.id.editText_SU_Email);
        editText_Password = findViewById(R.id.editText_SU_Password);
        editText_ConfirmPassword = findViewById(R.id.editText_SU_ConfirmPassword);

        button_SignUp = findViewById(R.id.button_SU_SignUp);

        progressBar_SignUp = findViewById(R.id.progressBar_SignUp);


        // Action Sign Up
        button_SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = editText_Name.getText().toString();
                String lastName = editText_LastName.getText().toString();

                String email = editText_Email.getText().toString();
                String password = editText_Password.getText().toString();
                String confirmPassword = editText_ConfirmPassword.getText().toString();


                // Validation of the fields

                if (name.isEmpty()) {

                    editText_Name.setError(getString(R.string.etRequired));
                    editText_Name.requestFocus();
                    return;

                }

                else if (lastName.isEmpty()) {

                    editText_LastName.setError(getString(R.string.etRequired));
                    editText_LastName.requestFocus();
                    return;

                }

                else if (email.isEmpty()) {

                    editText_Email.setError(getString(R.string.etRequired));
                    editText_Email.requestFocus();
                    return;

                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    editText_Email.setError(getString(R.string.etFormatInvalid));
                    editText_Email.requestFocus();
                    return;

                }

                else if (password.isEmpty()) {

                    editText_Password.setError(getString(R.string.etRequired));
                    editText_Password.requestFocus();
                    return;

                }

                else if (password.length() < 6) {

                    editText_Password.setError(getString(R.string.etMinPassword));
                    editText_Password.requestFocus();
                    return;

                }

                else if (!password.equals(confirmPassword)) {

                    Toast.makeText(SignUp_Activity.this, R.string.problemMismatchPassword, Toast.LENGTH_LONG).show();

                }


                // After validation, the registration of the user start

                else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {        // Check if the registration is successful

                                    if (task.isSuccessful()) {

                                        progressBar_SignUp.setVisibility(View.VISIBLE);

                                        User userCreated = new User(name, lastName, email);

                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(mAuth.getCurrentUser().getUid())
                                                .setValue(userCreated).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {      // Check if the user information was stored in the DB

                                                if (task.isSuccessful()) {

                                                    // Save login
                                                    statusApp.getInstance().loggedIn = true;


                                                    // Return to HomeScreen_Activity

                                                    Toast.makeText(SignUp_Activity.this, R.string.signedUpSuccessfully, Toast.LENGTH_LONG).show();

                                                    finish();

                                                }

                                                else {

                                                    Toast.makeText(SignUp_Activity.this, R.string.problemStoringUserData, Toast.LENGTH_LONG).show();

                                                }

                                            }

                                        });

                                    }

                                    else {

                                        // Alert dialog builder

                                        AlertDialog.Builder builder_ProblemSigningUp = new AlertDialog.Builder(SignUp_Activity.this);

                                        builder_ProblemSigningUp.setTitle(R.string.problemSigningUpTitle);

                                        builder_ProblemSigningUp.setMessage(R.string.problemSigningUp);

                                        builder_ProblemSigningUp.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {

                                                // Executed code when "OK" button is pressed

                                            }
                                        });

                                        builder_ProblemSigningUp.setIcon(android.R.drawable.ic_dialog_alert);


                                        // Alert dialog using the builder

                                        AlertDialog dialog_ProblemSigningUp = builder_ProblemSigningUp.create();

                                        dialog_ProblemSigningUp.show();

                                        dialog_ProblemSigningUp.setCancelable(false);

                                    }

                                }

                            });

                }

            }

        });

    }

}