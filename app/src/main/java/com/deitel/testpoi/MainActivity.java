package com.deitel.testpoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    CalendarView mCalendarView;
    String eventYear;
    private String ChangeEventForm(String eventForm, String type){ //Склонение по падежам
        if(type.equals("Pred")){
            if(eventForm.equals("Семинар")) return("семинаре");
            else if(eventForm.equals("Конференция")) return("конференции");
            else if(eventForm.equals("Симпозиум")) return("симпозиуме");
            else if(eventForm.equals("Круглый стол")) return("круглом столе");
            else if(eventForm.equals("Конгресс")) return("конгрессе");
        }

        if(type.equals("Rod")){
            if(eventForm.equals("Семинар")) return("семинара");
            else if(eventForm.equals("Конференция")) return("конференции");
            else if(eventForm.equals("Симпозиум")) return("симпозиума");
            else if(eventForm.equals("Круглый стол")) return("круглого стола");
            else if(eventForm.equals("Конгресс")) return("конгресса");
        }

        if(type.equals("Dat")){
            if(eventForm.equals("Семинар")) return("семинару");
            else if(eventForm.equals("Конференция")) return("конференции");
            else if(eventForm.equals("Симпозиум")) return("симпозиуму");
            else if(eventForm.equals("Круглый стол")) return("круглому столу");
            else if(eventForm.equals("Конгресс")) return("конгрессу");
        }
        return eventForm;
    }

    public void OnButtonClick(View view){ //Нажатие кнопки создания документа
        Toast toast = new Toast(this);
        try{
            Bundle arguments = getIntent().getExtras();
            HashMap<String, String> data = new HashMap<>();

            data.put("eventForm", arguments.get("typeEvent").toString());
            data.put("eventFormPred", ChangeEventForm(arguments.get("typeEvent").toString(), "Pred"));
            data.put("eventFormRod", ChangeEventForm(arguments.get("typeEvent").toString(), "Rod"));
            data.put("eventFormDat", ChangeEventForm(arguments.get("typeEvent").toString(), "Dat"));
            data.put("faxNumber", ((EditText) findViewById(R.id.editTextPhone2)).getText().toString());
            data.put("city", ((EditText) findViewById(R.id.editTextName4)).getText().toString());
            data.put("cityPred", ((EditText) findViewById(R.id.editTextName5)).getText().toString());
            data.put("eventName", ((EditText) findViewById(R.id.editTextName)).getText().toString());
            data.put("eventNameRod", ((EditText) findViewById(R.id.editTextName2)).getText().toString());
            data.put("PhoneNumber", ((EditText) findViewById(R.id.editTextPhone)).getText().toString());
            data.put("organizationName", ((EditText) findViewById(R.id.editTextName3)).getText().toString());
            data.put("eventYear", eventYear);
            data.put("Email", ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString());

            copyAssets(data);

            toast = Toast.makeText(this, "Файлы созданы!",Toast.LENGTH_LONG);
        }
        catch (Exception e){
            e.printStackTrace();
            toast = Toast.makeText(this, "Ошибка создания!",Toast.LENGTH_LONG);
        }
        finally {
            toast.show();
        }
    }

    private void copyAssets(HashMap<String, String> data){
        String[] files = null;
        AssetManager assetManager = getAssets();
        try{
            files = assetManager.list("Templates");
        } catch (IOException e){
            Log.e("tag", "HELP ME", e);
        }
        if(files != null) for(String filename : files){
            InputStream in = null;
            OutputStream out = null;
            Replace r = new Replace();
            try {
                if(equalsCheckBox(filename)){
                    in = assetManager.open("Templates/" + filename);
                    File outFile = new File(getExternalFilesDir(null), filename);
                    out = new FileOutputStream(outFile);
                    r.replace(in, data, out);
                }

            } catch (IOException e){
                Log.e("tag", "HELP, file died!", e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(in != null){
                    try{
                        in.close();
                    } catch (IOException e){

                    }
                }
                if(out != null){
                    try{
                        out.close();
                    } catch (IOException e){

                    }
                }
            }
        }
    }

    private boolean equalsCheckBox(String filename){
        if(filename.equals("Заявка на участие.docx") && ((CheckBox) findViewById(R.id.checkBox)).isChecked()) {return true;}
        else if(filename.equals("Письмо поддержки.docx") && ((CheckBox) findViewById(R.id.checkBox2)).isChecked()){return true;}
        else if(filename.equals("Письмо потенциальному спонсору.docx") && ((CheckBox) findViewById(R.id.checkBox3)).isChecked()){return true;}
        else if(filename.equals("Типовой договор.docx") && ((CheckBox) findViewById(R.id.checkBox4)).isChecked()){return true;}
        else if(filename.equals("Описание рекламной кампании для потенциальных спонсоров.docx") && ((CheckBox) findViewById(R.id.checkBox5)).isChecked()){return true;}
        else if(filename.equals("Памятка по составлению отчета о проведенном мероприятии.docx") && ((CheckBox) findViewById(R.id.checkBox6)).isChecked()){return true;}
        else if(filename.equals("План-график подготовки и проведения мероприятия.docx") && ((CheckBox) findViewById(R.id.checkBox7)).isChecked()){return true;}
        else if(filename.equals("Проект расходной части сметы.docx") && ((CheckBox) findViewById(R.id.checkBox8)).isChecked()){return true;}
        else if(filename.equals("Регламент для председателя заседания.docx") && ((CheckBox) findViewById(R.id.checkBox9)).isChecked()){return true;}
        else if(filename.equals("Требования к месту проведения.docx") && ((CheckBox) findViewById(R.id.checkBox10)).isChecked()){return true;}
        else if(filename.equals("Баланс.docx") && ((CheckBox) findViewById(R.id.checkBox11)).isChecked()){return true;}
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        // Связываемся с нашим календариком:
        mCalendarView = (CalendarView)findViewById(R.id.calendarView);

        //Настраиваем слушателя смены даты:
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            // Описываем метод выбора даты в календаре:
            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {
                eventYear = String.valueOf(year);
                // При выборе любой даты отображаем Toast сообщение с данными о выбранной дате (Год, Месяц, День):
                Toast.makeText(getApplicationContext(),
                        "Год: " + year + "\n" +
                                "Месяц: " + (month+1) + "\n" +
                                "День: " + dayOfMonth,
                        Toast.LENGTH_SHORT).show();
            }});

    }
}