package com.example.toma.androidsapt10.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toma.androidsapt10.Entities.Task;
import com.example.toma.androidsapt10.Entities.User;
import com.example.toma.androidsapt10.R;
import com.example.toma.androidsapt10.ViewModels.TaskViewModel;
import com.example.toma.androidsapt10.ViewModels.UserViewModel;
import com.example.toma.androidsapt10.vo.RequestObject;
import com.example.toma.androidsapt10.vo.TaskEdit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EditActivity extends AppCompatActivity {
    private EditText text_description;
    private Button button_edit;
    private TextView text_id, text_error;
    private TaskViewModel taskViewModel;
    private UserViewModel userViewModel;

    private void initControls() {
        text_description = (EditText) findViewById(R.id.description);
        text_id = (TextView) findViewById(R.id.id_task);
        button_edit = (Button) findViewById(R.id.edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit);
        initControls();
        TaskEdit taskEdit = (TaskEdit) getIntent().getSerializableExtra("TaskEdit");

        taskViewModel = ViewModelProviders.of(this, new TaskViewModel.Factory(getApplicationContext())).get(TaskViewModel.class);
        userViewModel = ViewModelProviders.of(this, new UserViewModel.Factory(getApplicationContext())).get(UserViewModel.class);
        text_id.setText(String.valueOf(taskEdit.getId()));
        text_description.setText(taskEdit.getDescription());

        final Context context = this;
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if(!editTask()){
//                        HomeActivity.errorMsg = "Invalid credentials. You are unable to perform an update.";
//                    }
                editTask();
                Intent intent = new Intent(context, HomeActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }


    public void editTask() {
//        final Boolean[] message = new Boolean[]{false};
//        final int id = Integer.parseInt(text_id.getText().toString());
//        final String newDescription = text_description.getText().toString();
//        taskViewModel.editTask(id, newDescription, HomeActivity.userPrincipal.getToken());
        Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        final int id = Integer.parseInt(text_id.getText().toString());
        final String newDescription = text_description.getText().toString();
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(id,"Nu conteaza",newDescription, new Date()));
        RequestObject requestObject = new RequestObject("UPDATE", taskList, HomeActivity.userPrincipal.getToken());
        String json = g.toJson(requestObject);
        taskViewModel.updateTask(taskList.get(0));
        HomeActivity.ws.send(g.toJson(requestObject));
    }
}
