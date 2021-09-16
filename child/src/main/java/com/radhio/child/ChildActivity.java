package com.radhio.child;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.radhio.grandparent.GrandParent;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        TextView grandParent, child;
        grandParent = findViewById(R.id.grandparent_child_text);
        child = findViewById(R.id.child_text);
        grandParent.setText(GrandParent.grandParentText);
        child.setText("To child activity");
    }
}