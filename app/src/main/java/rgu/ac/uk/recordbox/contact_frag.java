package rgu.ac.uk.recordbox;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rgu.ac.uk.recordbox.R;

//create contact frag class that will inherit from Fragment superclass and implements Viewclick listener
public class contact_frag extends Fragment implements View.OnClickListener
{
    //placeholder for a button that can be used in the entire scope of activity
    private Button send_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        send_button = v.findViewById(R.id.button);
        send_button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.button)
        {
            // Get the persons name from the message box on contact page
            EditText nameBox = getView().findViewById(R.id.nameBox);
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

            // Convert the message to lowercase except the first letter
            message = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();

            // Create an email intent to open external email app
            //Fill in the to address to the teams own email
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "team14coursework@gmail.com", null));
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);
            //Convert users name to uppercase
            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(R.string.emailMessage,message,name.toUpperCase())));
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