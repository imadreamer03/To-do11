package com.nanoapps.to_do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    List<recyclerData> cartList;
    recyclerAdapter mAdapter;
    CoordinatorLayout coordinatorLayout;
    LinearLayout container;
    BottomSheetBehavior bottomSheetBehavior;
    FloatingActionButton fab;
    EditText edit1;
    Context context;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.botom_container);

        bottomSheetBehavior = BottomSheetBehavior.from(container);

        fab = findViewById(R.id.fab);
        timePicker = findViewById(R.id.timee);
        timePicker.setIs24HourView(true);

        recyclerView = findViewById(R.id.myRec);
        coordinatorLayout = findViewById(R.id.rootLay);
        cartList = new ArrayList<>();
        mAdapter = new recyclerAdapter(this, cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        botttom();

        context = this;


        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


    }

    public void botttom(){
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    // do stuff when the drawer is expanded
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    // do stuff when the drawer is collapsed
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // do stuff during the actual drag event for example
                // animating a background color change based on the offset

                // or for example hidding or showing a fab
                if (slideOffset > 0.2) {
                    if (fab.isShown()) {
                        //fab.hide();
                    }
                } else if (slideOffset < 0.15) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
    }

    public void setLists(View view){
        String txt1, txt2, tim1, tim2, time;
        int Hour, minute, leng;
        edit1 = findViewById(R.id.edit1);
        txt1 = edit1.getText().toString();


        Hour = timePicker.getHour();
        minute = timePicker.getMinute();
        tim1 = String.valueOf(Hour);
        tim2 = String.valueOf(minute);
        time = tim1 + ":" + tim2;



        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){

            leng = txt1.length();
            if(leng > 2){
                txt2 = txt1.substring(0,1);
            }
            else {
                txt2 = "";
            }

            mAdapter.addItem(txt1, txt2, time, R.drawable.gradient);
            edit1.onEditorAction(EditorInfo.IME_ACTION_DONE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            edit1.setText("");



        }



    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof recyclerAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getDescription();

            // backup of removed item for undo purpose
            final recyclerData deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());
            String tttext = " Removed!";

            // showing snack bar with Undo option
            if (direction == ItemTouchHelper.LEFT){
                tttext = " Удалено!";
            }

            else if (direction == ItemTouchHelper.RIGHT){
                tttext = " Ура! Вы сделали это!";
            }
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + tttext, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}