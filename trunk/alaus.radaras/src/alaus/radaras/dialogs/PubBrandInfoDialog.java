package alaus.radaras.dialogs;

import alaus.radaras.R;
import alaus.radaras.service.BeerRadar;
import alaus.radaras.service.BeerRadarSqlite;
import alaus.radaras.submition.PubBrandInfo;
import alaus.radaras.submition.PubBrandStatus;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PubBrandInfoDialog extends Dialog implements OnClickListener  {

	public interface PubBrandInfoSubmited {
		public void pubInfoSubmited(PubBrandInfo brandInfo);
	}
	
	private PubBrandInfoSubmited submitedListener;

	private final Context context;
	private final View layout;
	private final RadioGroup statusView; 
	private final String pubId;
	private final String brandId;
	private BeerRadar beerRadar;
	public  PubBrandInfoDialog(Context context, String pubId, String brandId) {
		super(context);
		
		this.beerRadar = new BeerRadarSqlite(context);
		this.context = context;
		this.pubId = pubId;
		this.brandId = brandId;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = inflater.inflate(R.layout.dialog_report_pub_brand,null);
		setContentView(layout);
		this.statusView = (RadioGroup) layout.findViewById(R.id.rbgPubStatus);
		
		Button okButton = (Button) layout.findViewById(R.id.btnSubmitPubBrandInfo);
		okButton.setOnClickListener(this);
		Button cancelButton = (Button) layout.findViewById(R.id.btnCancelSubmitPubBrandInfo);
		cancelButton.setOnClickListener(this);
		
		bindView();
		this.setTitle(beerRadar.getBrand(brandId).getTitle());			
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

		for(PubBrandStatus statusType : PubBrandStatus.values()) {
			
			RadioButton rb = new RadioButton(context);
			rb.setId(statusType.ordinal());
			rb.setText(getText(statusType));
			rb.setTag(statusType);
			
			statusView.addView(rb);
		}
		statusView.check(statusView.getChildAt(0).getId());
		
	}

	private String getText(PubBrandStatus statusType) {
		switch(statusType) {

		case EXISTS:
			return context.getString(R.string.pub_brand_status_exists);
		case TEMPORARY_SOLD_OUT:
			return context.getString(R.string.pub_brand_status_temporary);
		case DISCONTINUED:
			return context.getString(R.string.pub_brand_status_discontinued);
		default:
			return context.getString(R.string.pub_brand_status_exists);
		}
	}
	
	public void setOnSubmitedListener(PubBrandInfoSubmited listener) {
		this.submitedListener = listener;
	}



	private void submitData() {
		
		EditText input = (EditText)layout.findViewById(R.id.txtPubBrandMessage);
		int reason = statusView.getCheckedRadioButtonId();
		PubBrandStatus status = PubBrandStatus.EXISTS;
		for(PubBrandStatus statusType : PubBrandStatus.values()) {
			if(statusType.ordinal() == reason) {
				status = statusType;
				break;
			}
		}
		
		PubBrandInfo brandInfo = new PubBrandInfo();
		brandInfo.setMessage(input.getText().toString());
		brandInfo.setStatus(status);
		brandInfo.setPubId(pubId);
		brandInfo.setBrandId(brandId);
		submitedListener.pubInfoSubmited(brandInfo);
		


	
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnSubmitPubBrandInfo) {
			submitData();
		} else {
			this.dismiss(); 
		}
		
	}

	public void setErrorState(boolean isError) {
		View errorDisplay = layout.findViewById(R.id.txtSubmitPubBrandError);
		errorDisplay.setVisibility(isError ? View.VISIBLE : View.INVISIBLE);
		
	}
	


}
