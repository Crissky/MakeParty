package com.inovaufrpe.makeparty.user.gui.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarioDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c  = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int mouth = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,mouth,day);
    }
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        //linha abaixo errada pq n ta numa activity, mas deixando aq p caso leitura futura
        //edittext.setText(sdf.format(myCalendar.getTime()));
    }

}
