package rgu.ac.uk.recordbox.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import rgu.ac.uk.recordbox.R;

import static android.content.Context.MODE_PRIVATE;

//create contact frag class that will inherit from Fragment superclass and implements Viewclick listener
public class contact_frag extends Fragment implements View.OnClickListener
{
    //placeholder for a button that can be used in the entire scope of activity
    private Button send_button = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        //send_button = (Button) v.findViewById(R.id.button);
        Button send_button = (Button) v.findViewById(R.id.button);
        //send_button.setOnClickListener(this);

          final Switch switchButton = getView().findViewById(R.id.nameSave);
          final TextView nameBox = getView().findViewById(R.id.nameBox);

        SharedPreferences sp = this.getActivity().getSharedPreferences("contactname", MODE_PRIVATE);
        String sharedPrefName = sp.getString("name", "");
        String sharedPrefSwitch = sp.getString("switch", "");

        nameBox.setText(sharedPrefName);

        if(!sharedPrefSwitch.equals(""))
            switchButton.setChecked(true);
        else
            switchButton.setChecked(false);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.button)
        {
            // Open contactname shared preferences
            SharedPreferences.Editor editor = getContext().getSharedPreferences("contactname", MODE_PRIVATE).edit();


            // Get the persons name from the message box on contact page
            EditText nameBox = Objects.requireNonNull(getView()).findViewById(R.id.nameBox);
            String name = nameBox.getText().toString();

            // Get the email text
            EditText emailTextView = getView().findViewById(R.id.emailText);
            String email = emailTextView.getText().toString();

            // Get the subject text
            EditText subjectTextView = getView().findViewById(R.id.subjectText);
            String subject = subjectTextView.getText().toString();

            // Get the persons message from the message box on contact page
            EditText messageBox = getView().findViewById(R.id.messageBox);
            String message = messageBox.getText().toString();

            // Get the switch button on the page
            Switch saveName = getView().findViewById(R.id.nameSave);

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
                Toast.makeText(getContext(), "Fill in all of the fields", Toast.LENGTH_SHORT).show();
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

                PackageManager packageManager = getActivity().getPackageManager();
                boolean isIntentSafe = emailIntent.resolveActivity(packageManager) != null;

                // Start the email app if it is safe
                if (isIntentSafe)
                    startActivity(emailIntent);
                    //Tell user via a toast that no email app is installed
                else
                    Toast.makeText(getActivity(), getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();

            }
        }
    }

    //Function that will check ALL text boxes to see if the input is empty.
    private Boolean emptyEmail(String name, String email, String subject, String message)
    {
        return name.equals("") || email.equals("") || subject.equals("") || message.equals("");
    }

}