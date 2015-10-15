package com.example.renan.mprime_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.widget.DrawerLayout;
import android.widget.ActionMenuView;
import android.widget.Chronometer;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static String tempo = "00:00";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006300")));

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    public void chstart(boolean i, final Chronometer ch, final Ringtone r, final Vibrator v) {
        int stoppedMilliseconds = 0;
        if (i) {
            String chronoText = ch.getText().toString();
            String array[] = chronoText.split(":");
            if (array.length == 2) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                        + Integer.parseInt(array[1]) * 1000;
            } else if (array.length == 3) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                        + Integer.parseInt(array[1]) * 60 * 1000
                        + Integer.parseInt(array[2]) * 1000;
            }

            ch.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
            ch.start();

            //ch.setBase(SystemClock.elapsedRealtime());
            //ch.start();
            final int[] teste = {0};
            ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    teste[0]++;
                    if (teste[0] == 900) {
                        //r.play();
                        //v.vibrate(new long[]{0, 300, 30, 300}, -1);
                        teste[0] = 0;
                    }
                    tempo = String.valueOf(ch.getText());
                }
            });
        } else

        {
            ch.stop();
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments


        Fragment MyFragment = null;

        switch (position) {
            case 0:
                MyFragment = new Atividade_fragment();
                break;
            //case 1:
            // MyFragment = new Treinos_fragment();
            //  break;
            case 1:
                MyFragment = new NovoTreino_fragment();
                break;
            case 2:
                MyFragment = new Exercicio_fragment();
                break;
            case 3:
                MyFragment = new LogTreino_fragment();
                break;
            case 4:
                MyFragment = new Sobre_fragment();
                break;
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MyFragment)
                .commit();


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_Treinar);
                break;
            //  case 2:
            // mTitle = getString(R.string.title_Treinos);
            //    break;
            case 2:
                mTitle = getString(R.string.title_NovoTreino);
                break;
            case 3:
                mTitle = getString(R.string.title_AdicionaExercicios);
                break;
            case 4:
                mTitle = getString(R.string.title_LogTreinos);
                break;
            case 5:
                mTitle = getString(R.string.title_Sobre);
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        // actionBar.setTitle(mTitle);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.halter2);
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_novotreino) {
            onNavigationDrawerItemSelected(1);
            return true;
        }

        if (id == R.id.action_ajuda) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog ajuda = builder.setTitle("Ajuda").setMessage
                    ("Encontrou dificuldades?\n" +
                            "Entre em contato conosco!\n" +
                            "===========================\n" +
                            "Desenvolvedor: Renan Cunha\n" +
                            "Email: renan.cunha33@gmail.com\n" +
                            "===========================\n" +
                            "Empresa: HauseGroup\n" +
                            "Email: hausegroup@gmail.com\n\n" +
                            "OBS.: a aba 'SOBRE' pode te ajudar a utilizar o app.").setNeutralButton("OK", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }


    public void boo(boolean what) {
        mNavigationDrawerFragment.lock(what);
    }

}
