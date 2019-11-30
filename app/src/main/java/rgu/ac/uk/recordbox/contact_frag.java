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

public class contact_frag extends Fragment implements View.OnClickListener
{
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
            // Get the persons name
            EditText nameBox = getView().findViewById(R.id.nameBox);
            String name = nameBox.getText().toString();

            // Get the persons email
            EditText emailBox = getView().findViewById(R.id.emailBox);
            String email = emailBox.getText().toString();

            // Get the persons message
            EditText messageBox = getView().findViewById(R.id.messageBox);
            String message = messageBox.getText().toString();

            // Convert the message to lowercase except the first letter
            message = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();

            // Create an email intent to open external email app
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "team14coursework@gmail.com", null));
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);
            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(getString(R.string.emailMessage,message,name.toUpperCase())));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));

            PackageManager packageManager = getActivity().getPackageManager();
            boolean isIntentSafe = emailIntent.resolveActivity(packageManager) != null;

            // Start the email app if it is safe otherwise tell them there's no email app
            if (isIntentSafe)
                startActivity(emailIntent);
            else
                Toast.makeText(getActivity(), getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();
        }
    }
}