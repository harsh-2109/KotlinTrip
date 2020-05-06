package com.finlite.admin.kotlintrip.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.finlite.admin.kotlintrip.R;
import com.finlite.admin.kotlintrip.cal.date.DatePickerDialog;

import java.util.Calendar;

public class SecondMainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;
    private CheckBox modeDarkDate;
    private CheckBox modeCustomAccentDate;
    private CheckBox vibrateDate;
    private CheckBox dismissDate;
    private CheckBox titleDate;
    private CheckBox showYearFirst;
    private CheckBox showVersion2;
    private CheckBox switchOrientation;
    private CheckBox limitSelectableDays;
    private CheckBox highlightDays;
    private DatePickerDialog dpd;
    Calendar now;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        dateTextView = findViewById(R.id.date_textview);
        Button dateButton = findViewById(R.id.date_button);
        modeDarkDate = findViewById(R.id.mode_dark_date);
        modeCustomAccentDate = findViewById(R.id.mode_custom_accent_date);
        vibrateDate = findViewById(R.id.vibrate_date);
        dismissDate = findViewById(R.id.dismiss_date);
        titleDate = findViewById(R.id.title_date);
        showYearFirst = findViewById(R.id.show_year_first);
        showVersion2 = findViewById(R.id.show_version_2);
        switchOrientation = findViewById(R.id.switch_orientation);
        limitSelectableDays = findViewById(R.id.limit_dates);
        highlightDays = findViewById(R.id.highlight_dates);


        findViewById(R.id.original_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar now = Calendar.getInstance();
                        new android.app.DatePickerDialog(SecondMainActivity.this,
                                new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Log.d("Orignal", "Got clicked");
                                    }
                                }, now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
        now = Calendar.getInstance();
        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            /*
            It is recommended to always create a new instance whenever you need to show a Dialog.
            The sample app is reusing them because it is useful when looking for regressions
            during testing
             */
                if (dpd == null) {
                    dpd = DatePickerDialog.newInstance(
                            SecondMainActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                } else {
                    dpd.initialize(
                            SecondMainActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                }
                dpd.setThemeDark(modeDarkDate.isChecked());
                dpd.vibrate(vibrateDate.isChecked());
                dpd.dismissOnPause(dismissDate.isChecked());
                dpd.showYearPickerFirst(showYearFirst.isChecked());
                dpd.setVersion(showVersion2.isChecked() ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
//                if (modeCustomAccentDate.isChecked()) {
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
//                }
                if (titleDate.isChecked()) {
                    dpd.setTitle("DatePicker Title");
                }
                if (highlightDays.isChecked()) {
                    Calendar date1 = Calendar.getInstance();
                    Calendar date2 = Calendar.getInstance();
                    date2.add(Calendar.WEEK_OF_MONTH, -1);
                    Calendar date3 = Calendar.getInstance();
                    date3.add(Calendar.WEEK_OF_MONTH, 1);
                    Calendar[] days = {date1, date2, date3};
                    dpd.setHighlightedDays(days);
                }
                if (limitSelectableDays.isChecked()) {
                    Calendar[] days = new Calendar[13];
                    for (int i = -6; i < 7; i++) {
                        Calendar day = Calendar.getInstance();
                        day.add(Calendar.DAY_OF_MONTH, i * 2);
                        days[i + 6] = day;
                    }
                    dpd.setSelectableDays(days);
                }
                if (switchOrientation.isChecked()) {
                    if (dpd.getVersion() == DatePickerDialog.Version.VERSION_1) {
                        dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.HORIZONTAL);
                    } else {
                        dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);
                    }
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                dpd.setMaxDate(calendar);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d("DatePickerDialog", "Dialog was cancelled");
                    }
                });
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateTextView.setText(date);
    }
//    private void addNotification() {
//        new generatePictureStyleNotification(this, "Title", "Message",
//                "https://www.w3schools.com/w3css/img_lights.jpg").execute();
//
//    }
//
//    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {
//
//        private Context mContext;
//        private String title, message, imageUrl;
//
//        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl) {
//            super();
//            this.mContext = context;
//            this.title = title;
//            this.message = message;
//            this.imageUrl = imageUrl;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//
//            InputStream in;
//            try {
//                URL url = new URL(this.imageUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                in = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(in);
//                return myBitmap;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            super.onPostExecute(result);
//
//
//            Intent intent = new Intent(SecondMainActivity.this, SecondMainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(SecondMainActivity.this, 0, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(SecondMainActivity.this,
//                    getString(R.string.notification_channel_id))
//                    .setContentTitle("Notification Title")
//                    .setContentText("Notification Body")
//                    .setAutoCancel(true)
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                    .setContentIntent(pendingIntent)
//                    .setContentInfo("Notification Body Description")
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                    .setColor(getResources().getColor(R.color.colorAccent))
//                    .setLights(Color.RED, 1000, 300)
//                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setNumber(1)
//                    .setSmallIcon(R.mipmap.ic_launcher);
//
//            notificationBuilder.setStyle(
//                    new NotificationCompat.BigPictureStyle().bigPicture(result).setSummaryText("Notification Body Summary")
//            );
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(
//                        getString(R.string.notification_channel_id), "name", NotificationManager.IMPORTANCE_DEFAULT
//                );
//                channel.setDescription("desc");
//                channel.setShowBadge(true);
//                channel.canShowBadge();
//                channel.enableLights(true);
//                channel.setLightColor(Color.RED);
//                channel.enableVibration(true);
//                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//
//                assert notificationManager != null;
//                notificationManager.createNotificationChannel(channel);
//            }
//
//            assert notificationManager != null;
//            notificationManager.notify(0, notificationBuilder.build());
//        }
//    }
}
