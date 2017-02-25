package com.awesomedroidapps.inappstoragereader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.awesomedroidapps.inappstoragereader.AppDataStorageItem;
import com.awesomedroidapps.inappstoragereader.AppStorageDataRecyclerView;
import com.awesomedroidapps.inappstoragereader.Constants;
import com.awesomedroidapps.inappstoragereader.ErrorMessageHandler;
import com.awesomedroidapps.inappstoragereader.ErrorMessageInterface;
import com.awesomedroidapps.inappstoragereader.ErrorType;
import com.awesomedroidapps.inappstoragereader.R;
import com.awesomedroidapps.inappstoragereader.SharedPreferenceReader;
import com.awesomedroidapps.inappstoragereader.Utils;
import com.awesomedroidapps.inappstoragereader.adapters.IconWithTextListAdapter;
import com.awesomedroidapps.inappstoragereader.adapters.SharedPreferencesListAdapter;
import com.awesomedroidapps.inappstoragereader.entities.SharedPreferenceObject;

import java.util.List;

/**
 * Created by anshul on 11/2/17.
 */

public class SharedPreferencesActivity extends AppCompatActivity implements
    ErrorMessageInterface, PopupMenu.OnMenuItemClickListener {

  private AppStorageDataRecyclerView sharedPreferencesRecylerView;
  private RelativeLayout errorHandlerLayout;
  private String fileName = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(
        R.layout.com_awesomedroidapps_inappstoragereader_activity_shared_preferences_list);
    sharedPreferencesRecylerView =
        (AppStorageDataRecyclerView) findViewById(R.id.shared_preferences_recycler_view);
    sharedPreferencesRecylerView.setRecyclerViewWidth(getRecyclerViewWidth());
    errorHandlerLayout = (RelativeLayout) findViewById(R.id.error_handler);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      fileName = bundle.getString(Constants.BUNDLE_FILE_NAME);
    }
  }

  /**
   * To get the width of the recyclerView based on its individual column widths.
   *
   * @return - The width of the RecyclerView.
   */
  private int getRecyclerViewWidth() {
    return (int) (getResources().getDimension(R.dimen.sharedpreferences_type_width) +
        getResources().getDimension(R.dimen.sharedpreferences_key_width) +
        getResources().getDimension(R.dimen.sharedpreferences_value_width));
  }

  @Override
  public void onStart() {
    super.onStart();
    if (Utils.isEmpty(fileName)) {
      loadAllSharedPreferences();
    } else {
      loadSharedPreferencesFromFile(fileName);
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.shared_preference_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    if (item.getItemId() == R.id.shared_preferences_filter) {
      View view = findViewById(R.id.shared_preferences_filter);
      showPopup(view);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * When the popup is shown.
   *
   * @param v
   */
  private void showPopup(View v) {
    PopupMenu popup = new PopupMenu(this, v);
    MenuInflater inflater = popup.getMenuInflater();
    inflater.inflate(R.menu.shared_preferences_popup_menu, popup.getMenu());
    popup.setOnMenuItemClickListener(this);
    popup.show();
  }

  /**
   * This function is for loading all the shared preferences.
   */
  private void loadAllSharedPreferences() {
    List<SharedPreferenceObject> sharedPreferenceObjectArrayList =
        SharedPreferenceReader.getAllSharedPreferences(this);
    getSharedPreference(sharedPreferenceObjectArrayList);
  }

  /**
   * This function is for loading all the shared preferences of a file
   *
   * @param fileName
   */
  private void loadSharedPreferencesFromFile(String fileName) {
    List<SharedPreferenceObject> sharedPreferenceObjectArrayList =
        SharedPreferenceReader.getSharedPreferencesBaseOnFileName(this, fileName);
    getSharedPreference(sharedPreferenceObjectArrayList);
  }

  /**
   * This function is used for loading all the shared preferences files.
   */
  private void loadSharedPreferencesFiles() {
    List sharedPreferencesFileList = SharedPreferenceReader.getSharedPreferencesTags(this);
    if (Utils.isEmpty(sharedPreferencesFileList)) {
      return;
    }
    IconWithTextListAdapter
        adapter = new IconWithTextListAdapter(sharedPreferencesFileList, this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);
    setActionBarTitle(null);
  }

  private void getSharedPreference(List<SharedPreferenceObject> sharedPreferenceObjectArrayList) {
    setActionBarTitle(sharedPreferenceObjectArrayList);
    if (Utils.isEmpty(sharedPreferenceObjectArrayList)) {
      handleError(ErrorType.NO_SHARED_PREFERENCES_FOUND);
      return;
    }
    sharedPreferencesRecylerView.setVisibility(View.VISIBLE);
    errorHandlerLayout.setVisibility(View.GONE);

    SharedPreferencesListAdapter
        adapter = new SharedPreferencesListAdapter(sharedPreferenceObjectArrayList, this);
    sharedPreferencesRecylerView.setLayoutManager(new LinearLayoutManager(this));
    sharedPreferencesRecylerView.setAdapter(adapter);
  }


  /**
   * Handle Errors .
   *
   * @param errorType
   */
  @Override
  public void handleError(ErrorType errorType) {
    sharedPreferencesRecylerView.setVisibility(View.GONE);
    errorHandlerLayout.setVisibility(View.VISIBLE);
    ErrorMessageHandler handler = new ErrorMessageHandler();
    handler.handleError(errorType, errorHandlerLayout);
  }

  /**
   * Callback for click on pop up items.
   *
   * @param item
   * @return
   */
  @Override
  public boolean onMenuItemClick(MenuItem item) {
    if (item == null) {
      return false;
    }

    //The view to show the user all the shared preferences at once.
    if (item.getItemId() == R.id.shared_preferences_all) {
      item.setChecked(item.isChecked());
      loadAllSharedPreferences();
      return true;
    }
    //The view to show the user all the shared preferences files.
    else if (item.getItemId() == R.id.shared_preferences_file) {
      item.setChecked(item.isChecked());
      loadSharedPreferencesFiles();
      return true;
    }
    return onMenuItemClick(item);
  }

  /**
   * Sets the title of the action bar with the number of shared preferences items.
   *
   * @param sharedPreferenceObjectList - List containing the items.
   */
  private void setActionBarTitle(List<SharedPreferenceObject> sharedPreferenceObjectList) {
    if (getSupportActionBar() == null) {
      return;
    }
    String sharedPreferenceTitle = getResources().getString(R.string
        .com_awesomedroidapps_inappstoragereader_shared_preferences_list_activity);
    if (Utils.isEmpty(sharedPreferenceObjectList)) {
      getSupportActionBar().setTitle(sharedPreferenceTitle);
      return;
    }

    int size = sharedPreferenceObjectList.size();
    StringBuilder stringBuilder = new StringBuilder(sharedPreferenceTitle);
    stringBuilder.append(Constants.SPACE);
    stringBuilder.append(Constants.OPENING_BRACKET);
    stringBuilder.append(size);
    stringBuilder.append(Constants.CLOSING_BRACKET);
    getSupportActionBar().setTitle(stringBuilder.toString());
  }
}