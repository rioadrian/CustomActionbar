package kr.susemi99.customactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity
{
  private enum ACTION_MODE
  {
    NORMAL, DELETE
  }
  
  private ActionMode _actionMode;
  private ACTION_MODE _currentMode = ACTION_MODE.NORMAL;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    Button btn1 = (Button) findViewById(R.id.button1);
    btn1.setOnClickListener(new OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        toggleDeleteMode();
      }
    });
  }
  
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if (item.getItemId() == R.id.action_delete)
      toggleDeleteMode();
    return super.onOptionsItemSelected(item);
  }
  
  
  private void toggleDeleteMode()
  {
    if (_currentMode == ACTION_MODE.NORMAL)
    {
      _currentMode = ACTION_MODE.DELETE;
      _actionMode = startActionMode(new ActionModeCallback());
//      _actionMode.setTitle("xx selected");
      
      View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_delete_mode, null);
      final TextView text1 = (TextView) view.findViewById(R.id.textView1);
      CheckBox check1 = (CheckBox) view.findViewById(R.id.checkBox1);
      check1.setOnCheckedChangeListener(new OnCheckedChangeListener()
      {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
          text1.setText(isChecked ? "selected all" : "not selected");
        }
      });
      ImageButton btn = (ImageButton) view.findViewById(R.id.btn_delete_all);
      btn.setOnClickListener(new OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
          deleteAll();
        }
      });
      
      _actionMode.setCustomView(view);
    }
    else
    {
      _actionMode.finish();
    }
  }
  
  
  private void deleteAll()
  {
    // TODO delete all
    Log.i("MainActivity.java | deleteAll", "|" + "delete all" + "|");
    toggleDeleteMode();
  }
  
  /**************************************************
   * action mode
   ***************************************************/
  private class ActionModeCallback implements ActionMode.Callback
  {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu)
    {
//      mode.getMenuInflater().inflate(R.menu.main_delete_mode, menu);
      return true; // important!
    }
    
    
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu)
    {
      return false;
    }
    
    
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
    {
      if (item.getItemId() == R.id.delete)
        deleteAll();
      
      return false;
    }
    
    
    @Override
    public void onDestroyActionMode(ActionMode mode)
    {
      Log.i("MainActivity.java | onDestroyActionMode", "|" + "action mode finish" + "|");
      _actionMode = null;
      _currentMode = ACTION_MODE.NORMAL;
    }
  }
}
