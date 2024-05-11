package com.example.deletethisshit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

// MainActivity implements search and list last searches functionality

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter, adapter2;
    private String choice;
    private ListView lv_listView, lv_listView2;
    private TextView tv_emptyTextView;
    private FragmentManager fragmentManager;
    ArrayList<String> lastCitieslist = new ArrayList<>();

    //Shows the search layout and on click item saves users choice and updates list of last searches
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_listView = findViewById(R.id.lv_listView);
        tv_emptyTextView = findViewById(R.id.tv_emptyTextView);

        fragmentManager = getSupportFragmentManager();

        lv_listView2 = findViewById(R.id.lv_listView2);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cities_array));
        lv_listView.setAdapter(adapter);

        lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();


                Intent intent = new Intent(MainActivity.this, CityInfo.class);
                intent.putExtra("city", choice);
                startActivity(intent);

                if (lastCitieslist.size() == 5) {
                    lastCitieslist.remove(0);
                    lastCitieslist.add(choice);
                } else {
                    lastCitieslist.add(choice);
                }
            }
        });

        lv_listView.setEmptyView(tv_emptyTextView);
    }

    // Updates list of last searches and shows it through adapter for the user
    protected void onResume() {
        super.onResume();

        adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lastCitieslist);
        lv_listView2.setAdapter(adapter2);

        lv_listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();


                Intent intent = new Intent(MainActivity.this, CityInfo.class);
                intent.putExtra("city", choice);
                startActivity(intent);

                if (lastCitieslist.size() == 5) {
                    lastCitieslist.remove(0);
                    lastCitieslist.add(choice);
                } else {
                    lastCitieslist.add(choice);
                }
            }
        });
    }


    // Search method which updates search results while user types it
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_nav_menu, menu);

        android.view.MenuItem search = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search something!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }



}