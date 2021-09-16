package com.radhio.parent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.radhio.grandparent.GrandParent;

public class ParentActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    SplitInstallManager installManager;
    private int userSessionID;

    private static final String TAG = "ParentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        TextView grandParent, parent;
        grandParent = findViewById(R.id.grandparent_parent_text);
        parent = findViewById(R.id.parent_text);
        grandParent.setText(GrandParent.grandParentText);
        parent.setText("To parent activity");

        progressDialog = new ProgressDialog(this);
        installManager = SplitInstallManagerFactory.create(this);

        Button client, admin;
        client = findViewById(R.id.install);
        admin = findViewById(R.id.demand);

        client.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName(BuildConfig.APPLICATION_ID, "com.radhio.client.ClientActivity");
            startActivity(intent);
        });

        admin.setOnClickListener(v -> startDownloading());
    }

    private void startDownloading() {
        SplitInstallRequest installRequest = SplitInstallRequest.newBuilder().addModule("admin").build();

        SplitInstallStateUpdatedListener listener = splitInstallSessionState -> {
            if (splitInstallSessionState.sessionId() == userSessionID) {
                switch (splitInstallSessionState.status()) {
                    case SplitInstallSessionStatus.DOWNLOADING:
                        progressDialog.setMessage("Downloading");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.INSTALLED:
                        Intent intent = new Intent();
                        intent.setClassName(BuildConfig.APPLICATION_ID, "com.radhio.admin.AdminActivity");
                        startActivity(intent);
                    case SplitInstallSessionStatus.CANCELED:
                        progressDialog.setMessage("Canceled");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.CANCELING:
                        progressDialog.setMessage("Canceling");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.DOWNLOADED:
                        progressDialog.setMessage("DOWNLOADED");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.FAILED:
                        progressDialog.setMessage("FAILED");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.INSTALLING:
                        progressDialog.setMessage("Installing");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.PENDING:
                        progressDialog.setMessage("Pending");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION:
                        progressDialog.setMessage("REQUIRES_USER_CONFIRMATION");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.UNKNOWN:
                        break;
                }
            }
        };

        installManager.startInstall(installRequest)
                .addOnFailureListener(exception ->
                        Log.d(TAG, "startDownloading: Exception"+ exception)
                )
                .addOnSuccessListener(sessionId -> userSessionID = sessionId);

        installManager.registerListener(listener);

       // installManager.unregisterListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}