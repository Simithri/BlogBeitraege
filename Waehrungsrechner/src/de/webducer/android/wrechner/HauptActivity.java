package de.webducer.android.wrechner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HauptActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Init();
    }
    
    private void Init(){
    	// Button mit einem Listener verbinden
    	Button cmdBerechnen = (Button)findViewById(R.id.cmdBerechnen);
    	cmdBerechnen.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				kursBerechnen();
			}
		});
    }
    
    private void kursBerechnen(){
    	// Elemente auslesen
    	EditText txtBetrag = (EditText)findViewById(R.id.txtBetrag);
    	TextView txtErgebnis = (TextView)findViewById(R.id.txtErgebnisse);
    	Spinner selWaehrungStart = (Spinner)findViewById(R.id.selWaehrungStart);
    	Spinner selWaehrungEnde = (Spinner)findViewById(R.id.selWaehrungEnde);
    	
    	// Uwandlung des Betrages in eine Zahl
    	String betragText = txtBetrag.getText().toString();
    	Double betrag = 0d;
    	if (betragText != null && betragText != "") {
    		try {
    			betrag = Double.parseDouble(betragText);
			} catch (NumberFormatException e) {
				Toast.makeText(this, R.string.err_betarg_format, Toast.LENGTH_LONG);
			}
		}
    	
    	// Bestimmen der Ausgangs- und Zielwährung
    	int wahrungStart = selWaehrungStart.getSelectedItemPosition();
    	int waehrungEnde = selWaehrungEnde.getSelectedItemPosition();
    	
    	// Bestimmen des jeweiligen Wechselkurses
    	String kursStartText = getResources().getStringArray(R.array.waehrung_kurs)[wahrungStart];
    	String kursEndeText = getResources().getStringArray(R.array.waehrung_kurs)[waehrungEnde];
    	
    	// Umwandlung des Kurses in eine Zahl
    	Double kursStart = 0d;
    	Double kursEnde = 0d;
    	if (kursStartText != null && kursStartText != "") {
			try {
				kursStart = Double.parseDouble(kursStartText);
			} catch (NumberFormatException e) {
				Toast.makeText(this, R.string.err_kurs_format, Toast.LENGTH_LONG);
			}
		}
    	if (kursEndeText != null && kursEndeText != "") {
			try {
				kursEnde = Double.parseDouble(kursEndeText);
			} catch (NumberFormatException e) {
				Toast.makeText(this, R.string.err_kurs_format, Toast.LENGTH_LONG);
			}
		}
    	
    	// Umrechnung
    	Double ergebnis = betrag / kursStart * kursEnde;
    	
    	// Ergebnis ausgeben
    	StringBuilder ergebnisText = new StringBuilder();
    	ergebnisText.append(getResources().getStringArray(R.array.waehrung)[wahrungStart].subSequence(0, 3))
    		.append(" ")
    		.append(String.format("%.2f", betrag))
    		.append(" = ")
    		.append(getResources().getStringArray(R.array.waehrung)[waehrungEnde].substring(0, 3))
    		.append(" ")
    		.append(String.format("%.2f", ergebnis))
    		.append("\n");
    	txtErgebnis.setText(txtErgebnis.getText() + ergebnisText.toString());
    	
    }
}