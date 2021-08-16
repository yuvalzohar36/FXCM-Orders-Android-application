package com.example.fxcm;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String is_buy = "Long";
    ArrayList<String> myArrayList = new ArrayList<>();
    ListView l;
    private ProgressBar spinner;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        final ArrayAdapter<String>  myArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_view_ordering, myArrayList);
        l = (ListView) findViewById(R.id.simpleListView);
        l.setAdapter(myArrayAdapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("FXCMDATA");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    LocalDate date = java.time.LocalDate.now();
                    String[] date1=date.toString().split("-"); //year, month, day
                    String[] date2= item_snapshot.child("Date").getValue().toString().split("/"); //day, month, year
                    if(item_snapshot.child("Is Buy").getValue().toString().equals("false"))
                        is_buy = "Short";
                    if(date1[0].equals(date2[2]) && date1[1].equals(date2[1]) && date1[2].equals(date2[0])) {
                        myArrayList.add(item_snapshot.child("Order").getValue().toString() + ", " + is_buy + ", " + item_snapshot.child("Date").getValue().toString());
                        myArrayAdapter.notifyDataSetChanged();
                    } }
                spinner.setVisibility(View.GONE);}
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }});
    }
}