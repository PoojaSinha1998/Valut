package com.example.dell.passwords.Common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.Listeners.HomePageListener;
import com.example.dell.passwords.Pojo.RecordsPojo;
import com.example.dell.passwords.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportIntoExcel {
    private Context context;
    HomePageListener mListener;
    View mainView;

    // Progress Dialog
    private ProgressDialog pDialog;
    Button btnShowProgress;

    AlertDialog dialog;
    String csvFile;
    ImageView my_image;
    File sd;

    public ExportIntoExcel(Context context) {


        this.context = context;
        new BackTask().execute();
    }

    // background task to download file
    private class BackTask extends AsyncTask<Void, Integer, Void> {
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        Notification noti;

        protected void onPreExecute() {
            super.onPreExecute();
            mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("File Download")
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.ic_export_excel);
            Toast.makeText(context, "Downloading the file... The download progress is on notification bar.", Toast.LENGTH_LONG).show();

//            pDialog = new ProgressDialog(context);
//            pDialog.setMessage("Downloading file. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setMax(100);
//            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        protected Void doInBackground(Void... params) {

            try {
                try {
                    DumpIntoExcel();

                } catch (Exception e) {
                    e.printStackTrace();

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;

        }

        protected void onProgressUpdate(Integer... progress) {

            mBuilder.setProgress(100, progress[0], false);
            // Displays the progress bar on notification
            mNotifyManager.notify(0, mBuilder.build());

            // setting progress percentage
           // pDialog.setProgress(progress[0]);
        }

        protected void onPostExecute(Void result) {
            Log.d("VT","Value of SD : "+sd);
            Toast.makeText(context,"Data Exported in a Excel Sheet : " + sd + csvFile, Toast.LENGTH_SHORT).show();

            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(0, mBuilder.build());

           // pDialog.dismiss();

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/" + csvFile;
            // setting downloaded into image view
        //    my_image.setImageDrawable(Drawable.createFromPath(imagePath));

            noti = new Notification(R.drawable.ic_export_excel,"Password Data", System.currentTimeMillis());

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(String.valueOf(imagePath))), "application/vnd.ms-excel");

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(contentIntent);
            noti.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

            mNotifyManager.notify(0, mBuilder.build());

        }

    }


    public void DumpIntoExcel() {
        DBHelper dbHelper = new DBHelper(context);


        final int gotAllData = dbHelper.getAllRecord(ApplicationClass.getUserName());
        if (gotAllData > 0) {

            sd = Environment.getExternalStorageDirectory();
            csvFile = "myData.xls";

            File directory = new File(sd.getAbsolutePath());
            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            try {

                //file path
                File file = new File(directory, csvFile);
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("passwordList", 0);
                // column and row
                sheet.addCell(new Label(0, 0, "Site Name"));
                sheet.addCell(new Label(1, 0, "User Name"));
                sheet.addCell(new Label(2, 0, "User Password"));
                sheet.addCell(new Label(3, 0, "Note"));

                ArrayList<RecordsPojo> recordsPojos = ApplicationClass.getRecordsPojoArrayList();

                Log.d("VT", "File size recordPojos :" + recordsPojos.size() + " data : " + recordsPojos.get(1).getName());
                if (recordsPojos.size() > 0) {

                    for (int i = 0; i < recordsPojos.size(); i++) {
                        Log.d("VT", "File size i :" + i);
                        String sitename = recordsPojos.get(i).getName();
                        String username = recordsPojos.get(i).getUserId();
                        String userpassword = recordsPojos.get(i).getUserPassword();
                        String note = recordsPojos.get(i).getDes();

                        int j = i + 1;
                        sheet.addCell(new Label(0, j, sitename));
                        sheet.addCell(new Label(1, j, username));
                        sheet.addCell(new Label(2, j, userpassword));
                        sheet.addCell(new Label(3, j, note));

                        Log.d("VT", "data added to sheet :" + i);

                    }
                }


                //closing gotAllData

                workbook.write();
                workbook.close();
//                Toast.makeText(getActivity(),
//                        "Data Exported in a Excel Sheet : "+sd+csvFile, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "No data available.", Toast.LENGTH_LONG).show();
        }
    }

}
