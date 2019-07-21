package com.bashar.salatreminder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.appindexing.builders.Actions;

import java.util.ArrayList;

/**
 * Created by Jahid on 8/17/2015.
 */
public class FragmentReceipientList extends Fragment {
//    public static final int PICK_CONTACT = 1;
//    private static final int RESULT_PICK_CONTACT = 55500;
//    public static final int REQUEST_CODE_PICK_CONTACT = 1;
//    public static final int  MAX_PICK_CONTACT= 10;
//    ListView list_view;
//    SQLController dbcon;
//    SimpleCursorAdapter adapter = null;
//    TextView text_id;
//    TextView empty;
//    Button but_del_con, but_waq_ok, but_waqt_cancel;
//    CheckBox check_farj, check_dhur, check_asr, check_mag, check_esha, check_all;
//    String sel_fajr = null, sel_dhur = null, sel_asr = null, sel_mag = null, sel_esha = null;
//    SharedPreferences recipientListPref;
//    boolean recDeleteHelp;
//
//    public FragmentReceipientList() {
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        setHasOptionsMenu(true);
//
//        dbcon = new SQLController(getActivity());
//        dbcon.open();
//
//        final View view = inflater.inflate(R.layout.fragment_receipient_list, container, false);
//
//        list_view = (ListView)view.findViewById(R.id.contact_listView);
//        empty = (TextView)view.findViewById(R.id.empty_rec);
//
//        updateContactList();
//
//        recipientListPref = this.getActivity().getSharedPreferences("recipientListPref", Context.MODE_PRIVATE);
//        getPref();
//
//        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                TextView text_name = (TextView)view.findViewById(R.id.name2);
//                text_id = (TextView)view.findViewById(R.id.member_id);
//                TextView text_number = (TextView)view.findViewById(R.id.member_name);
//
//                final String contac_name = text_name.getText().toString();
//                final String contac_num = text_number.getText().toString();
//                String con_id = text_id.getText().toString();
//                final long contac_id = Long.parseLong(con_id);
//
//                Cursor c = dbcon.readData();
//                c.moveToFirst();
//                do {
//                    if(c.getString(2).equals(contac_num)){
//                        sel_fajr = c.getString(3);
//                        sel_dhur = c.getString(4);
//                        sel_asr = c.getString(5);
//                        sel_mag = c.getString(6);
//                        sel_esha = c.getString(7);
//                        //Toast.makeText(getActivity(),sel_esha+" "+c.getString(1),Toast.LENGTH_LONG).show();
//                        break;
//                    }
//
//                } while (c.moveToNext());
//
//                boolean check_all_waqt;
//
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.dialog_waqt_select);
//                dialog.setTitle((new StringBuilder("WAQT to Remind ")).append(contac_name).toString());
//                dialog.setCanceledOnTouchOutside(false);
//
//                check_all = (CheckBox)dialog.findViewById(R.id.allDialogCheckbox);
//
//                check_farj = (CheckBox)dialog.findViewById(R.id.fajrDialogCheckbox);
//                check_dhur = (CheckBox)dialog.findViewById(R.id.dhurDialogCheckbox);
//                check_asr = (CheckBox)dialog.findViewById(R.id.asrDialogCheckbox);
//                check_mag = (CheckBox)dialog.findViewById(R.id.maghribDialogCheckbox);
//                check_esha = (CheckBox)dialog.findViewById(R.id.eshaDialogCheckbox);
//
//                if(sel_fajr.equals("yes") && sel_dhur.equals("yes") && sel_asr.equals("yes") && sel_mag.equals("yes") && sel_esha.equals("yes"))
//                    check_all.setChecked(true);
//                if(sel_fajr.equals("yes"))
//                    check_farj.setChecked(true);
//                if(sel_dhur.equals("yes"))
//                    check_dhur.setChecked(true);
//                if(sel_asr.equals("yes"))
//                    check_asr.setChecked(true);
//                if(sel_mag.equals("yes"))
//                    check_mag.setChecked(true);
//                if(sel_esha.equals("yes"))
//                    check_esha.setChecked(true);
//
//                check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(check_all.isChecked()){
//                            check_farj.setChecked(true);
//                            check_dhur.setChecked(true);
//                            check_asr.setChecked(true);
//                            check_mag.setChecked(true);
//                            check_esha.setChecked(true);
//                        }
//                        else {
//                            check_farj.setChecked(false);
//                            check_dhur.setChecked(false);
//                            check_asr.setChecked(false);
//                            check_mag.setChecked(false);
//                            check_esha.setChecked(false);
//                        }
//                    }
//                });
//
//                but_waq_ok = (Button)dialog.findViewById(R.id.okDialog);
//                but_waqt_cancel =(Button)dialog.findViewById((R.id.cancelDialog));
//                but_waqt_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                but_waq_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(check_farj.isChecked())
//                            sel_fajr = "yes";
//                        else
//                            sel_fajr = "no";
//
//                        if(check_dhur.isChecked())
//                            sel_dhur = "yes";
//                        else
//                            sel_dhur = "no";
//
//                        if(check_asr.isChecked())
//                            sel_asr = "yes";
//                        else
//                            sel_asr = "no";
//
//                        if(check_mag.isChecked())
//                            sel_mag = "yes";
//                        else
//                            sel_mag = "no";
//
//                        if(check_esha.isChecked())
//                            sel_esha = "yes";
//                        else
//                            sel_esha = "no";
//
//                        //Toast.makeText(getActivity(),"esha" + sel_esha, Toast.LENGTH_LONG).show();
//
//                        dbcon.updateData(contac_id, contac_name, contac_num,
//                                sel_fajr, sel_dhur, sel_asr, sel_mag, sel_esha);
//                        dialog.dismiss();
//
//                    }
//                });
//                dialog.show();
//
//            }
//        });
//
//        list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                text_id = (TextView) view.findViewById(R.id.member_id);
////                final Dialog dialog = new Dialog(getActivity());
////                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                dialog.setContentView(R.layout.dialog_delete_contact_confirm);
////                dialog.setCanceledOnTouchOutside(true);
////                but_del_con = (Button) dialog.findViewById(R.id.but_del_confirm);
////                but_del_con.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View arg0) {
////                        // TODO Auto-generated method stub
////                        String mem_id_val = text_id.getText().toString();
////                        long id_val = Long.parseLong(mem_id_val);
////                        dbcon.deleteData(id_val);
////                        updateContactList();
////                        dialog.dismiss();
////
////                    }
////                });
////                dialog.show();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Are you sure you want to remove the recipient?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                String mem_id_val = text_id.getText().toString();
//                                long id_val = Long.parseLong(mem_id_val);
//                                dbcon.deleteData(id_val);
//                                updateContactList();
//                                dialog.cancel();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//                return true;
//            }
//        });
//
//
//        return view;
//    }
//
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_add, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.add_num:
//                pickContact();
//
//                return true;
//        }
//
//        return false;
//    }
//
//    public void pickContact()
//    {
//        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
//
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // check whether the result is ok
//        //Toast.makeText(getActivity(), resultCode, Toast.LENGTH_LONG).show();
//        //Toast.makeText(getActivity(), requestCode, Toast.LENGTH_SHORT).show();
//        //if (resultCode == RESULT_OK) {
//            // Check for the request code, we might be usign multiple startActivityForReslut
//            switch (requestCode) {
//                case RESULT_PICK_CONTACT:
//                    contactPicked(data);
//                    break;
//            }
//        //} else {
//          //  Log.e("MainActivity", "Failed to pick contact");
//        //}
//    }
//
//    private void contactPicked(Intent data) {
//        Cursor cursor = null;
//        try {
//            String phoneNo = null ;
//            String name = null;
//            // getData() method will have the Content Uri of the selected contact
//            Uri uri = data.getData();
//            //Query the content uri
//            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//            cursor.moveToFirst();
//            Cursor cur = dbcon.readData();
//            boolean check = cur.moveToFirst();
//            // column index of the phone number
//            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//            // column index of the contact name
//            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//            phoneNo = cursor.getString(phoneIndex);
//            name = cursor.getString(nameIndex);
//
//            insertContactInDB(phoneNo, name);
//
//            if(!check && !recDeleteHelp)
//                showDeleteHelpAlert();
//            // Set the value to the textviews
//            //textView1.setText(name);
//            //textView2.setText(phoneNo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void insertContactInDB(String phone, String name) {
//        StringBuilder existContacts = new StringBuilder();
//        int check = dbcon.countSpecificStringData(
//                DBhelper.TABLE_MEMBER, phone);
//        if (check == 0) {
//            dbcon.insertData(name, phone, "no", "no", "no", "no", "no");
//            existContacts.append(name + " Added successfully");
//        } else {
//            existContacts.append(name+" already added !"+ "\n");
//        }
//        Toast.makeText(getActivity(), existContacts, Toast.LENGTH_LONG).show();
//        updateContactList();
//    }
//
//    public void updateContactList() {
//        Cursor cursor = dbcon.readData();
//        boolean check = cursor.moveToFirst();
//        String[] from = new String[] { DBhelper.MEMBER_ID, DBhelper.MEMBER_NUMBER, DBhelper.MEMBER_NAME };
//        int[] to = new int[] { R.id.member_id, R.id.member_name, R.id.name2 };
//
//        adapter = new SimpleCursorAdapter(
//                getActivity(), R.layout.view_member_entry, cursor, from, to);
//
//        if(check)
//            empty.setVisibility(View.GONE);
//        else
//            empty.setVisibility(View.VISIBLE);
//
//        adapter.notifyDataSetChanged();
//        list_view.setAdapter(adapter);
//    }
//    public void showDeleteHelpAlert()
//    {
//        View checkBoxView = View.inflate(getActivity(), R.layout.chekbox, null);
//        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                // Save to shared preferences
//            }
//        });
//        checkBox.setText("Do not show this again.");
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Tap on recipient to select waqt to remind.\nTo remove a recipient from list, long press on it!")
//                .setView(checkBoxView)
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        if(checkBox.isChecked())
//                            recDeleteHelp = true;
//                        else
//                            recDeleteHelp = false;
//                        storePref();
//                        dialog.cancel();
//                    }
//                })
//                .show();
//    }
//
//    public void storePref() {
//        SharedPreferences.Editor editor = recipientListPref.edit();
//        editor.putBoolean("REC_DELETE_HELP", recDeleteHelp);
//        editor.commit();
//    }
//
//    public void getPref() {
//        recDeleteHelp = recipientListPref.getBoolean("REC_DELETE_HELP", false);
//    }
}
