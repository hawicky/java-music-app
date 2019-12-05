package rgu.ac.uk.recordbox.OurActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import rgu.ac.uk.recordbox.R;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    //placeholder for a button that can be used in the entire scope of activity
    private Button send_button = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Logo Text colorSpan
        //select the word box and make it orange
        String app = getApplicationInfo().loadLabel(getPackageManager()).toString();
        SpannableString ss = new SpannableString(app);
        ForegroundColorSpan fcsOrange = new ForegroundColorSpan(getResources().getColor(R.color.colorOrange));
        ss.setSpan(fcsOrange,6,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(ss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        send_button = (Button) findViewById(R.id.button);
        send_button.setOnClickListener(this);

        final Switch switchButton = findViewById(R.id.nameSave);
        final TextView nameBox = findViewById(R.id.nameBox);

        SharedPreferences sp = this.getSharedPreferences("contactname", MODE_PRIVATE);
        String sharedPrefName = sp.getString("name", "");
        String sharedPrefSwitch = sp.getString("switch", "");


        nameBox.setText(sharedPrefName);

        if(!sharedPrefSwitch.equals("")){
            switchButton.setChecked(true);
        }
        else {
            switchButton.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button)
        {
            // Open contactname shared preferences
            SharedPreferences.Editor editor = getSharedPreferences("contactname", MODE_PRIVATE).edit();


            // Get the persons name from the message box on contact page
            EditText nameBox = findViewById(R.id.nameBox);
            String name = nameBox.getText().toString();

            // Get the email text
            EditText emailTextView = findViewById(R.id.emailText);
            String email = emailTextView.getText().toString();

            // Get the subject text
            EditText subjectTextView = findViewById(R.id.subjectText);
            String subject = subjectTextView.getText().toString();

            // Get the persons message from the message box on contact page
            EditText messageBox = findViewById(R.id.messageBox);
            String message = messageBox.getText().toString();

            // Get the switch button on the page
            Switch saveName = findViewById(R.id.nameSave);

            // if the switch button is checked
            if(saveName.isChecked())
            {
                // put the name and switch status in shared preferences
                editor.putString("name", name).apply();
                editor.putString("switch", "enabled").apply();
            }

            // if the switch button is unchecked
            else
            {
                // put the name as blank and switch status as blank in shared preferences
                editor.putString("name", "").apply();
                editor.putString("switch", "").apply();
            }

            //if statement to see if any of the strings are empty, if they are create toast to inform user
            //If none are empty, open email and fill in fields
            if (emptyEmail(name, email, subject, message))
                //Toast to inform user of empty feel
                Toast.makeText(this, "Fill in all of the fields", Toast.LENGTH_SHORT).show();
            else
            {
                // Convert the message to lowercase except the first letter
                message = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();
                // Create an email intent to open external email app
                //Fill in the to address to the teams own email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "team14coursework@gmail.com", null));
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);
                //Convert users name to uppercase
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(R.string.emailMessage, message, name.toUpperCase())));
                //Set the subject to variable email subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, subject));

                PackageManager packageManager = getPackageManager();
                boolean isIntentSafe = emailIntent.resolveActivity(packageManager) != null;

                // Start the email app if it is safe
                if (isIntentSafe)
                    startActivity(emailIntent);
                    //Tell user via a toast that no email app is installed
                else
                    Toast.makeText(this, getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();

            }
        }

    }


    //Function that will check ALL text boxes to see if the input is empty.
    private Boolean emptyEmail(String name, String email, String subject, String message)
    {
        return name.equals("") || email.equals("") || subject.equals("") || message.equals("");
    }
}
