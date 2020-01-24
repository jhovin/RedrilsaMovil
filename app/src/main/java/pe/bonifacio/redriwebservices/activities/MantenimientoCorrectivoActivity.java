package pe.bonifacio.redriwebservices.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import pe.bonifacio.redriwebservices.R;

public class MantenimientoCorrectivoActivity extends AppCompatActivity {

    EditText EditTextFecha;
    EditText EditTextHora;
    Context mContext= this;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    final int hour = C.get(Calendar.HOUR_OF_DAY);
    final int minute = C.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_correctivo);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        EditTextFecha=findViewById(R.id.fecha__ejecucion_input);
        int textLength = EditTextFecha.getText().length();
        EditTextFecha.setSelection(textLength,textLength);

        EditTextHora=findViewById(R.id.hora_dato_input);
        EditTextHora.setSelection(EditTextHora.getText().length());

        EditTextHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EditTextHora.setText(hourOfDay+":"+minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();
            }
        });

        EditTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
            }
        });
    }
    private void colocar_fecha() {
        EditTextFecha.setText (mDayIni + "-" + (mMonthIni + 1) + "-" + mYearIni+" ");
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }


        return null;
    }
}
