package com.zennow.zennow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TabBooking extends Fragment {

    private Toolbar toolbar;
    private TextView title;
    private View booking1;
    private View booking2;
    private View booking3;
    private View booking4;
    //param Booking1
    private Button addInstrucion;
    private Calendar myCalendarStart = Calendar.getInstance();
    private Calendar myCalendarEnd = Calendar.getInstance();
    private Button start;
    private Button end;
    private Spinner guards;
    private AutoCompleteTextView adresse;
    private DatePickerDialog.OnDateSetListener dateStart;
    private TimePickerDialog.OnTimeSetListener timeStart;
    private DatePickerDialog.OnDateSetListener dateEnd;
    private TimePickerDialog.OnTimeSetListener timeEnd;
    private TextView price;

    //param Booking2
    private Button securityinscruction;
    private EditText instruction;
    private Spinner attire;
    private Spinner TypeEvent;

    //param Booking3
    private Button Addcard;
    private TextView ResumeAdresse;
    private TextView ResumeDate;
    private TextView ResumeGuards;
    private TextView ResumePrice;

    //param Booking4
    private Button AddCard2;
    private EditText CardOwner;
    private EditText CardExpiration;
    private EditText CardNumber;
    private TextView CardCVC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        booking1=(View)view.findViewById(R.id.booking1);
        booking2=(View)view.findViewById(R.id.booking2);
        booking3=(View)view.findViewById(R.id.booking3);
        booking4=(View)view.findViewById(R.id.booking4);
        addInstrucion=(Button)view.findViewById(R.id.booking1submit);
        adresse=(AutoCompleteTextView)view.findViewById(R.id.autocomplete);
        start=(Button) view.findViewById(R.id.start);
        end=(Button) view.findViewById(R.id.end);
        guards=(Spinner)view.findViewById(R.id.guards);
        price=(TextView) view.findViewById(R.id.price);

        ResumeAdresse=(TextView)view.findViewById(R.id.ResumeAdresse);
        ResumeDate=(TextView)view.findViewById(R.id.ResumeDate);
        ResumeGuards=(TextView)view.findViewById(R.id.ResumePrice);
        ResumePrice=(TextView)view.findViewById(R.id.ResumePriceTotal);
        Addcard=(Button)view.findViewById(R.id.booking3submit);


        adresse.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.autocomplete));

        instruction=(EditText)view.findViewById(R.id.instruction);
        attire=(Spinner)view.findViewById(R.id.attire);
        TypeEvent=(Spinner)view.findViewById(R.id.typeEvent);
        securityinscruction=(Button)view.findViewById(R.id.booking2submit);

        //param Booking4
        AddCard2=(Button)view.findViewById(R.id.booking4submit);
        CardOwner=(EditText)view.findViewById(R.id.NameOnCarte);
        CardExpiration=(EditText)view.findViewById(R.id.CardExpiration);
        CardNumber=(EditText)view.findViewById(R.id.CardNumber);
        CardCVC=(EditText)view.findViewById(R.id.CardCVC);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        title=(TextView)getActivity().findViewById(R.id.toolbar_title);
        intialeToolBar("Booking", false);

        addInstrucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((myCalendarEnd.getTimeInMillis()<=myCalendarStart.getTimeInMillis())||(end.getText().length()==0)||(start.getText().length()==0)){
                    Toast.makeText(getActivity(),"error:EndTime SHOULD BE > StartTime",Toast.LENGTH_LONG).show();
                }else {
                    booking2.setVisibility(View.VISIBLE);
                    booking1.setVisibility(View.GONE);
                    intialeToolBar("Booking Details", true);
                }
            }
        });

        securityinscruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking2.setVisibility(View.GONE);
                booking3.setVisibility(View.VISIBLE);
                intialeToolBar("Book Security", true);
                ResumeAdresse.setText(adresse.getText().toString());
                ResumeDate.setText(start.getText().toString()+" -> "+end.getText().toString());
                ResumeGuards.setText(guards.getSelectedItem().toString()+" Guards x "+getHoursFromMillis(myCalendarEnd.getTimeInMillis()-myCalendarStart.getTimeInMillis())+"hours($50/h)");
                ResumePrice.setText("Total: $"+String.valueOf(Integer.parseInt(getHoursFromMillis(myCalendarEnd.getTimeInMillis()-myCalendarStart.getTimeInMillis()))*50));
            }
        });

        Addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking3.setVisibility(View.GONE);
                booking4.setVisibility(View.VISIBLE);
                intialeToolBar("Add Card", true);
            }
        });

        CardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
                final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
                final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
                final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
                final char DIVIDER = ' ';

                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // chech that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrecntString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        CardExpiration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if((CardExpiration.getText().toString().length()==2)&&(!CardExpiration.getText().toString().contains("/"))){
                    CardExpiration.append("/");
                }
            }
        });

        dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        timeStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendarStart.set(Calendar.MINUTE, minute);
                updateLabel(start,myCalendarStart);
            }
        };
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =new TimePickerDialog(getActivity(),R.style.DarkDialog, timeStart, myCalendarStart.get(Calendar.HOUR_OF_DAY),
                        myCalendarStart.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Start Hour");
                timePickerDialog.show();

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.DarkDialog, dateStart, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Start Date");
                try {
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
                }
                catch (Exception e){}
                datePickerDialog.show();
            }
        });

        //here the end time and the pickers//
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =new TimePickerDialog(getActivity(),R.style.DarkDialog, timeEnd, myCalendarEnd.get(Calendar.HOUR_OF_DAY),
                        myCalendarEnd.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("End Hour");
                timePickerDialog.show();

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.DarkDialog, dateEnd, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("End Date");
                try {
                    datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());
                }
                catch (Exception e){}
                datePickerDialog.show();
            }
        });
        dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        timeEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendarEnd.set(Calendar.MINUTE, minute);
                updateLabel(end,myCalendarEnd);
            }
        };
    }

    private String getHoursFromMillis(long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60 * 60)));
    }

    private void updateLabel(Button editText,Calendar calendar) {
        String myFormat = "EEE, d MMM yyyy HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    private void intialeToolBar(String Title,Boolean BackButton){
        title.setText(Title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        if(BackButton){
            if(booking2.getVisibility()==View.VISIBLE){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booking1.setVisibility(View.VISIBLE);
                        booking2.setVisibility(View.GONE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        title.setText("Book Security");
                    }
                });
            }
            else if(booking3.getVisibility()==View.VISIBLE){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booking2.setVisibility(View.VISIBLE);
                        booking3.setVisibility(View.GONE);
                        intialeToolBar("Booking Details",true);                    }
                });
            }
            else if(booking4.getVisibility()==View.VISIBLE){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booking3.setVisibility(View.VISIBLE);
                        booking4.setVisibility(View.GONE);
                        intialeToolBar("Booking Details",true);                    }
                });
            }
        }else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private  ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(Config.GetInstance().PLACES_API_BASE +Config.GetInstance().TYPE_AUTOCOMPLETE + Config.GetInstance().OUT_JSON);
            sb.append("?key=" + Config.GetInstance().API_KEY);
            //sb.append("&components=country:gr");iici le pays
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (Exception e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (Exception e) {
        }
        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;
        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
        @Override
        public int getCount() {
            return resultList.size();
        }
        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

}

