package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class GetDetails extends AsyncTask<Object, String, Document>{
    @SuppressLint("StaticFieldLeak")
    private Spinner spinner;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    @Override
    protected Document doInBackground(Object... objects) {
        DownloadUrl downloadUrl = new DownloadUrl();
        spinner = (Spinner) objects[1];
        context = (Context) objects[2];
        return downloadUrl.readUrl((String) objects[0]);
    }

    @Override
    protected void onPostExecute(Document document) {
        Parser parser = new Parser(document);
        ArrayList<BusStopList> arrayList = new ArrayList<>();
        if(parser.getBusStop() != null){
            arrayList.add(new BusStopList(null, "Select Your Bus Stop"));
            arrayList.addAll(parser.getBusStop());
        }
        else{
            arrayList.add(new BusStopList(null, "No Bus Stop Near You"));
        }
        ArrayAdapter<BusStopList> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setVisibility(View.VISIBLE);
    }


}
