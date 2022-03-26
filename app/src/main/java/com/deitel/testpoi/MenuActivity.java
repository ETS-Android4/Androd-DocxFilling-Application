package com.deitel.testpoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    String[] events = { "Конференция", "Круглый стол", "Семинар", "Симпозиум", "Конгресс"};
    String typeEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, events);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                TextView textView = (TextView) findViewById(R.id.MenuTextView);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                if(item == "Конференция"){
                    imageView.setImageResource(R.drawable.conference);
                    textView.setText(R.string.conferenceText);
                }
                else if(item == "Семинар"){
                    imageView.setImageResource(R.drawable.seminar);
                    textView.setText(R.string.seminarText);
                }
                else if(item == "Конгресс"){
                    imageView.setImageResource(R.drawable.congress);
                    textView.setText(R.string.congressText);
                }
                else if(item == "Симпозиум"){
                    imageView.setImageResource(R.drawable.symphosium);
                    textView.setText(R.string.symposiumText);
                }
                else if(item == "Круглый стол"){
                    imageView.setImageResource(R.drawable.roundtable);
                    textView.setText(R.string.TableText);
                }
                typeEvent = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    public void ButtonToDocument(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("typeEvent", typeEvent);
        startActivity(intent);
    }
}