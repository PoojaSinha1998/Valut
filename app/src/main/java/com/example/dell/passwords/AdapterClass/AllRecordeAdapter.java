package com.example.dell.passwords.AdapterClass;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.passwords.Activities.AddNewRecord;
import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.Dailogs.validatePin;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.Listeners.ConfirmationListener;
import com.example.dell.passwords.Pojo.RecordsPojo;
import com.example.dell.passwords.R;

import java.util.ArrayList;

public class AllRecordeAdapter extends RecyclerView.Adapter<AllRecordeAdapter.ViewHolder> {
    public ViewHolder viewHolder;
    ArrayList<RecordsPojo> recordsPojos;
    public Context context;
    DBHelper dbHelper;

    String title;
    String username;
    String password;
    String note;
    int Lid;
    boolean isEdit = false;
    boolean isDelete = false;


    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;

    public AllRecordeAdapter(Context context, ArrayList<RecordsPojo> recordsPojos) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.recordsPojos = recordsPojos;
    }

    public AllRecordeAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public AllRecordeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_record_file, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllRecordeAdapter.ViewHolder holder, final int i) {
        holder.t.setText(recordsPojos.get(i).getName());
        holder.st.setText(recordsPojos.get(i).getUserId());
        holder.des.setText(recordsPojos.get(i).getDes());
        // holder.showPass.setText(recordsPojos.get(i).getUserPassword());

        holder.sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.onItemClick(recordsPojos, i);
                Log.d("TAG", "adapter position " + i);
            }
        });


        holder.setLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(int view) {
                Lid = view;
                title = recordsPojos.get(i).getName().toString();
                username = recordsPojos.get(i).getUserId().toString();
                password = recordsPojos.get(i).getUserPassword().toString();
                note = recordsPojos.get(i).getDes().toString();
                Log.d("TAG", "adapter Long press position " + i);
            }

        });


    }

    public void getItemSelected(MenuItem item) {
        this.openDetailActivity(item.getTitle().toString());
        // Toast.makeText(context,name+" : "+item,Toast.LENGTH_LONG).show();
    }

    private void openDetailActivity(String choice) {
        if (choice.equals("Edit")) {
            isEdit = true;
            isDelete = false;
            FragmentActivity activity = (FragmentActivity) (context);
            FragmentManager fm = activity.getSupportFragmentManager();
            validatePin dFragment;
            ApplicationClass.setTitle(title);
            ApplicationClass.setUserTitle(username);
            ApplicationClass.setPassword(password);
            ApplicationClass.setNote(note);

            dFragment = new validatePin();
            dFragment.isEdit();
            dFragment.show(fm, "Title");




        }
        if (choice.equals("Delete")) {
            isEdit = false;
            isDelete = true;
            ApplicationClass.setTitle(title);
            ApplicationClass.setUserTitle(username);
            ApplicationClass.setPassword(password);
            ApplicationClass.setNote(note);
            FragmentActivity activity = (FragmentActivity) (context);
            FragmentManager fm = activity.getSupportFragmentManager();
            validatePin dFragment;
            dFragment = new validatePin();
            dFragment.isDelete();
            dFragment.show(fm, "Title");
//            final String id = dbHelper.getDataToDelete(title, username, password);
//
//            //Alert Box
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setCancelable(false);
//            builder.setMessage("Do you want to Delete This Item?");
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //if user pressed "yes", then he is allowed to exit from application
//
//
//                    boolean a = dbHelper.deleteRow(id);
//
//                    if (a) {
//                        Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
//
//                        recordsPojos.remove(Lid);
//                        notifyItemRemoved(Lid);
//                        notifyItemRangeChanged(Lid, recordsPojos.size());
//                    } else
//                        Toast.makeText(context, "Data Not Deleted", Toast.LENGTH_LONG).show();
//
//
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //if user select "No", just cancel this dialog and continue with app
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alert = builder.create();
//            alert.show();

        }
    }

    @Override
    public int getItemCount() {
        return recordsPojos.size();
    }

//    @Override
//    public void onPinExit(boolean proceed) {
//        if (!proceed) {
//
//            return;
//        }
//
//        else
//        {
//            if(isEdit)
//            {
//                Intent intent = new Intent(context, AddNewRecord.class);
//                intent.putExtra("TITLE", title);
//                intent.putExtra("USER_NAME", username);
//                intent.putExtra("PASSWORD", password);
//                intent.putExtra("NOTE", note);
//                context.startActivity(intent);
//            }
//            if(isDelete)
//            {
//                final String id = dbHelper.getDataToDelete(title, username, password);
//
//                //Alert Box
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setCancelable(false);
//                builder.setMessage("Do you want to Delete This Item?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //if user pressed "yes", then he is allowed to exit from application
//
//
//                        boolean a = dbHelper.deleteRow(id);
//
//                        if (a) {
//                            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
//
//                            recordsPojos.remove(Lid);
//                            notifyItemRemoved(Lid);
//                            notifyItemRangeChanged(Lid, recordsPojos.size());
//                        } else
//                            Toast.makeText(context, "Data Not Deleted", Toast.LENGTH_LONG).show();
//
//
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //if user select "No", just cancel this dialog and continue with app
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//
//            }
//        }
//
//    }

    public void EditData()
    {
        Intent intent = new Intent(context, AddNewRecord.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("NOTE", note);
        context.startActivity(intent);
    }

    public void DeleteData()
    {
        final String id = dbHelper.getDataToDelete(title, username, password);

        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Delete This Item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application


                boolean a = dbHelper.deleteRow(id);

                if (a) {
                    Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_LONG).show();

                    recordsPojos.remove(Lid);
                    notifyItemRemoved(Lid);
                    notifyItemRangeChanged(Lid, recordsPojos.size());
                } else
                    Toast.makeText(context, "Data Not Deleted", Toast.LENGTH_LONG).show();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();



    }

    public void getEditOrDelete() {
        if(isEdit)
        {
            EditData();
        }
        if(isDelete){
            DeleteData();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView t, st, sp, des, showPass;
        LinearLayout allData;

        ItemLongClickListener longClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.title);
            st = itemView.findViewById(R.id.subtitle);
            sp = itemView.findViewById(R.id.showpassword);
            des = itemView.findViewById(R.id.showDiscri);
            allData = itemView.findViewById(R.id.myall_data);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
//            showPass = itemView.findViewById(R.id.textshowpass);

        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onItemLongClick(getLayoutPosition());
            return false;
        }

        public void setLongClickListener(ItemLongClickListener itemClickListener) {
            this.longClickListener = itemClickListener;
        }

        //        public void showPassword()
//        {
//            Log.d("VT","inside view holder show password");
//            showPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//
//            sp.setText("Hide password");
//        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose Your Action");
            menu.add(0, 0, 0, "Edit");
            menu.add(0, 1, 0, "Delete");
        }

    }

    public interface ItemClickListener {
        void onItemClick(ArrayList<RecordsPojo> view, int position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(int position);
    }

    // allows clicks events to be caught


//    public void callShowPassword()
//    {
//        Log.d("VT","inside show password");
//        viewHolder.showPassword();
//    }


}
