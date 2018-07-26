package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

public class GetBusesList extends AsyncTask<Object, Document, Document>{

    @Override
    protected Document doInBackground(Object... objects) {
        DownloadUrl downloadUrl = new DownloadUrl();
        return downloadUrl.readUrl((String) objects[0]);
    }

}
