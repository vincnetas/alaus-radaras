package alaus.radaras.dialogs;

import alaus.radaras.R;
import alaus.radaras.submition.DataPublisher;
import alaus.radaras.submition.NewPub;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewPubReportDialog extends Dialog implements android.view.View.OnClickListener {

	private final Context context;
	private final View layout;
	private Location location;
	public NewPubReportDialog(Context context) {
		super(context);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.dialog_submit_pub,null);
		setContentView(layout);
		
		Button okButton = (Button)layout.findViewById(R.id.btnSubmitPub);
		okButton.setOnClickListener(this);
		Button cancelButton = (Button)layout.findViewById(R.id.btnSubmitPubCancel);
		cancelButton.setOnClickListener(this);
		
		this.setTitle(context.getString(R.string.submit_pub_header));
		
	
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        LayoutParams params = getWindow().getAttributes(); 
                params.width = LayoutParams.FILL_PARENT; 
                 getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); 
    }

	
	public void display(Location location) {
		this.location = location;
		this.show();
		
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnSubmitPub) {
			submitData();
		} else {
			this.dismiss(); 
		}
		
	}
	
	private void submitData() {
		
		EditText input = (EditText)layout.findViewById(R.id.txtSubmitPubInfo);
		CheckBox near = (CheckBox)layout.findViewById(R.id.cbSubmitPubNear);

		NewPub newpub = new NewPub();
		newpub.setLocation(location);
		newpub.setText(input.getText().toString());
		newpub.setNear(near.isChecked());
		
		DataPublisher publisher = new DataPublisher();
		if(publisher.submitNewPub(newpub)) {
			Toast.makeText(context, context.getString(R.string.submit_pub_thanks), Toast.LENGTH_SHORT).show();
			this.dismiss();
		} else {
			setErrorState(true);
		}
		
	}

	public void setErrorState(boolean isError) {
		View errorDisplay = layout.findViewById(R.id.txtSubmitPubError);
		errorDisplay.setVisibility(isError ? View.VISIBLE : View.INVISIBLE);
		
	}

}
