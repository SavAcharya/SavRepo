package com.ridenow.fragments;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.actionbarsherlock.app.SherlockFragment;
import com.ridenow.R;
import com.ridenow.utils.AlertUtility;
import com.ridenow.utils.AppConstants;
import com.ridenow.utils.StringUtility;
import com.ridenow.utils.Utils;

public class RideFragment extends SherlockFragment implements OnClickListener {
	Button offerButton;
	Button requestButton;
	Button dateButton;
	Button createRideButton;
	Button timeButton;
	AutoCompleteTextView fromEditText;
	AutoCompleteTextView toEditText;
	EditText seatEditText;
	RadioGroup privicyGroup;
	RadioButton privacyPrivate;
	RadioButton privacyPublic;

	// page data variables
	private String privacyMode;
	private int rideType;
	private String dateOfRide;
	private String fromRide;
	private String toRide;
	private String seats;
	private String timeOfRide;

	// Places search variables
	String[] search_text;
	String placesSearchUrl;
	ArrayList<String> names;
	ArrayAdapter<String> adp;
	JSONObject json;
	JSONArray contacts = null;
	private static final String PLACES_RESULT = "predictions";

	private Activity context;
	private String TAG = getClass().getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_ride, container, false);
		/* Buttons --------------------------- */
		offerButton = (Button) view.findViewById(R.id.btn_offer);
		offerButton.setOnClickListener(this);

		requestButton = (Button) view.findViewById(R.id.btn_request);
		requestButton.setOnClickListener(this);

		dateButton = (Button) view.findViewById(R.id.btn_date);
		dateButton.setOnClickListener(this);

		timeButton = (Button) view.findViewById(R.id.btn_time);
		timeButton.setOnClickListener(this);

		createRideButton = (Button) view.findViewById(R.id.btn_createRide);
		createRideButton.setOnClickListener(this);
		/* EditText --------------------------- */

		seatEditText = (EditText) view.findViewById(R.id.edt_seats);

		/* RadioGroup ------------------------- */
		privacyPrivate = (RadioButton) view
				.findViewById(R.id.radioButton_private);

		privacyPublic = (RadioButton) view
				.findViewById(R.id.radioButton_public);
		privicyGroup = (RadioGroup) view.findViewById(R.id.privacyGroup);
		toEditText = (AutoCompleteTextView) view.findViewById(R.id.edt_to);
		fromEditText = (AutoCompleteTextView) view.findViewById(R.id.edt_from);
		radioButtonListner();
		autoCompletePlacesListner();
		setRideType(AppConstants.rideTypeOffer);
		return view;
	}

	private void autoCompletePlacesListner() {
		toEditText.setThreshold(1);
		fromEditText.setThreshold(1);

		toEditText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				search_text = toEditText.getText().toString().split(",");
				placesSearchUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
						+ URLEncoder.encode(search_text[0])
						+ "&location=37.76999,-122.44696&radius=500&sensor=true&key=AIzaSyC1CHljfuBrSeTgXmm_NuJ1HZC6moNRRPE";
				if (search_text.length <= 1) {
					names = new ArrayList<String>();
					Log.d("URL", placesSearchUrl);
					/*
					 * paserdata parse=new paserdata(true); parse.execute();
					 */
				}

			}
		});

		fromEditText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				search_text = fromEditText.getText().toString().split(",");
				placesSearchUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
						+ URLEncoder.encode(search_text[0])
						+ "&location=37.76999,-122.44696&radius=500&sensor=true&key=AIzaSyC1CHljfuBrSeTgXmm_NuJ1HZC6moNRRPE";
				if (search_text.length <= 1) {
					names = new ArrayList<String>();
					Log.d("URL", placesSearchUrl);
					/*
					 * paserdata parse=new paserdata(false); parse.execute();
					 */
				}

			}
		});

	}

	private void radioButtonListner() {

		int checked = privicyGroup.getCheckedRadioButtonId();
		// Check which radio button was clicked
		switch (checked) {
		case R.id.radioButton_private:
			Log.i(TAG, "is Private");
			break;
		case R.id.radioButton_public:
			Log.i(TAG, "is Public");
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	@SuppressLint("ResourceAsColor")
	public void setRideType(int ridetypeoffer) {
		this.rideType = ridetypeoffer;

		switch (ridetypeoffer) {
		case AppConstants.rideTypeOffer:
			offerButton.setTextColor(getResources().getColor(
					R.color.text_color_light));
			offerButton.setBackgroundColor(getResources().getColor(
					R.color.base_color_dark));

			requestButton.setTextColor(getResources().getColor(
					R.color.text_color));
			requestButton.setBackgroundColor(getResources().getColor(
					R.color.base_color_lite));

			break;
		case AppConstants.rideTypeRequest:
			offerButton.setTextColor(getResources()
					.getColor(R.color.text_color));
			offerButton.setBackgroundColor(getResources().getColor(
					R.color.base_color_lite));

			requestButton.setTextColor(getResources().getColor(
					R.color.text_color_light));
			requestButton.setBackgroundColor(getResources().getColor(
					R.color.base_color_dark));

			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		switch (id) {
		case R.id.btn_createRide:
			createride();

			break;
		case R.id.btn_date:
			showDateUI();
			break;
		case R.id.btn_offer:
			setRideType(AppConstants.rideTypeOffer);
			break;
		case R.id.btn_request:
			setRideType(AppConstants.rideTypeRequest);
			break;
		case R.id.btn_time:
			showTimeUI();
			break;

		default:

			break;
		}

	}

	private void showTimeUI() {
		// TODO Auto-generated method stub
		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(getActivity(),
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker,
							int selectedHour, int selectedMinute) {
						timeButton.setText(selectedHour + ":" + selectedMinute);
					}
				}, hour, minute, true);// Yes 24 hour time
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();

	}

	private void showDateUI() {
		final Calendar myCalendar = Calendar.getInstance();

		DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				dateOfRide = (new StringBuilder().append(monthOfYear + 1)
						.append("-").append(dayOfMonth).append("-")
						.append(year).append(" ")).toString();
				Log.i(TAG, dateOfRide);
				dateButton.setText(dateOfRide);

			}

		};

		DatePickerDialog dialog = new DatePickerDialog(getActivity(), date,
				myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
				myCalendar.get(Calendar.DAY_OF_MONTH));
		dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
		dialog.show();
	}

	private boolean isValidInput() {
		boolean isValid = true;

		fromRide = fromEditText.getText().toString();
		toRide = toEditText.getText().toString();
		dateOfRide = dateButton.getText().toString();
		timeOfRide = timeButton.getText().toString();
		seats = seatEditText.getText().toString();

		if (!StringUtility.isNotNullOrEmpty(fromRide)) {
			isValid = false;
			AlertUtility.showToast(context,
					getString(R.string.from_field_empty));
		} else if (!StringUtility.isNotNullOrEmpty(toRide)) {
			isValid = false;
			AlertUtility.showToast(context, getString(R.string.to_field_empty));
		} else if (!StringUtility.isNotNullOrEmpty(dateOfRide)) {
			isValid = false;
			AlertUtility.showToast(context,
					getString(R.string.date_field_empty));
		} else if (!StringUtility.isNotNullOrEmpty(timeOfRide)) {
			isValid = false;
			AlertUtility.showToast(context,
					getString(R.string.time_field_empty));
		} else if (!StringUtility.isNotNullOrEmpty(seats)) {
			isValid = false;
			AlertUtility.showToast(context,
					getString(R.string.seats_field_empty));
		} else if ((!privacyPrivate.isChecked())
				&& (!privacyPublic.isChecked())) {
			System.out.println("Private Check - " + privacyPrivate.isChecked()
					+ ", Public - " + privacyPublic.isChecked());
			isValid = false;
			AlertUtility.showToast(context,
					getString(R.string.privacy_field_empty));
		}

		return isValid;
	}

	private void createride() {
		// TODO Auto-generated method stub
		if (isValidInput()) {
			// new CreateRideAsyncTask().execute();
			Utils.showToast(getSherlockActivity(), "Calling HTTP Post URL");
		}
	}

}
