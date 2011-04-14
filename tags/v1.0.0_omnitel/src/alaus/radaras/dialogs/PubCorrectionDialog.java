package alaus.radaras.dialogs;

import alaus.radaras.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import alaus.radaras.submition.PubCorrection;
import alaus.radaras.submition.PubCorrectionReason;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

public class PubCorrectionDialog extends Dialog implements OnClickListener  {

	public interface PubErrorSubmited {
		public void pubErrorSubmited(PubCorrection pubErroData);
	}
	
	private PubErrorSubmited submitedListener;

	private final Context context;
	private final View layout;
	private final RadioGroup errorReasonsView; 
	private final String pubId;
	
	public  PubCorrectionDialog(Context context, String pubId) {
		super(context);

			this.context = context;
			this.pubId = pubId;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = inflater.inflate(R.layout.dialog_report_pub_error,null);
			setContentView(layout);
			errorReasonsView = (RadioGroup) layout.findViewById(R.id.rbgErrorReasons);
			Button okButton = (Button)layout.findViewById(R.id.btnSubmitError);
			okButton.setOnClickListener(this);
			Button cancelButton = (Button)layout.findViewById(R.id.btnCancelSubmition);
			cancelButton.setOnClickListener(this);
			
			bindView();
			this.setTitle(context.getString(R.string.pub_error_header));
			
	}
	
	public void display() {
		
		this.show();
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        LayoutParams params = getWindow().getAttributes(); 
                params.width = LayoutParams.FILL_PARENT; 
                 getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); 
    }

	private void bindView() {

		for(PubCorrectionReason errorType : PubCorrectionReason.values()) {
			
			RadioButton rb = new RadioButton(context);
			rb.setId(errorType.ordinal());
			rb.setText(getText(errorType));
			rb.setTag(errorType);
			
			errorReasonsView.addView(rb);
		}
		errorReasonsView.check(errorReasonsView.getChildAt(0).getId());
		
	}

	private String getText(PubCorrectionReason errorType) {
		switch(errorType) {

		case INVALID_CONTACT_DATA:
			return context.getString(R.string.pub_error_contacts);
		case INCORRECT_BEER_LIST:
			return context.getString(R.string.pub_error_beers);
		case BANKRUPT:
			return context.getString(R.string.pub_error_bakrupt);
		default:
			return context.getString(R.string.pub_error_other);
		}
	}
	
	public void setOnSubmitedListener(PubErrorSubmited listener) {
		this.submitedListener = listener;
	}



	private void submitData() {
		
		EditText input = (EditText)layout.findViewById(R.id.txtErrorReason);
		int reason = errorReasonsView.getCheckedRadioButtonId();
		PubCorrectionReason errorReason = PubCorrectionReason.BANKRUPT;
		for(PubCorrectionReason errorType : PubCorrectionReason.values()) {
			if(errorType.ordinal() == reason) {
				errorReason = errorType;
				break;
			}
		}
		
		PubCorrection error = new PubCorrection();
		error.setMessage(input.getText().toString());
		error.setReason(errorReason);
		error.setPubId(pubId);
		
		submitedListener.pubErrorSubmited(error);
		


	
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnSubmitError) {
			submitData();
		} else {
			this.dismiss(); 
		}
		
	}

	public void setErrorState(boolean isError) {
		View errorDisplay = layout.findViewById(R.id.txtErrorState);
		errorDisplay.setVisibility(isError ? View.VISIBLE : View.INVISIBLE);
		
	}
	


}
