package com.example.toma.androidsapt10.UI;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toma.androidsapt10.Entities.Task;
import com.example.toma.androidsapt10.Entities.User;
import com.example.toma.androidsapt10.R;
import com.example.toma.androidsapt10.ViewModels.HomeViewModel;
import com.example.toma.androidsapt10.ViewModels.TaskViewModel;
import com.example.toma.androidsapt10.vo.RequestObject;
import com.example.toma.androidsapt10.vo.ResponseObject;
import com.example.toma.androidsapt10.vo.TaskEdit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class HomeActivity extends AppCompatActivity {
    protected TextView text_welcome;
    private TextView text_error;
    public static String errorMsg;
    private HomeViewModel homeViewModel;
    private TaskViewModel taskViewModel;
    private Button button_logout;
    private ListView listView;
    private static final String CLASSNAME = "HOME ACTIVITY";
    private final Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    private OkHttpClient client;
    protected static WebSocket ws;
    protected static User userPrincipal;
    final String[] token = new String[]{"string"};
    private List<Task> tasksList;

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            RequestObject requestObject = new RequestObject("GET", tasksList, userPrincipal.getToken());
            webSocket.send(g.toJson(requestObject));
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            ResponseObject responseObject = g.fromJson(text, ResponseObject.class);
            List<Task> responseList = responseObject.getTasks();
            if (responseObject.getResponseType().equals("GET")) {
//                if (responseList.size() == 0){
//                    taskViewModel.deleteAll();
//                }
//                else {
//                    if (tasksList.size()==0) {
//                        tasksList = responseList;
//                    } else {
//                        for (Task task : responseList) {
//                            for (Task task1 : tasksList) {
//                                if (task1.getId() == task.getId()) {
//                                    if (!task.getDescriere().equals(task1.getDescriere())) {
//                                        tasksList.remove(task1);
//                                        tasksList.add(task);
//                                    }
//                                }
//                            }
//                        }
//                    }
                    taskViewModel.deleteAll();
                    for (Task task : responseList) {
                        taskViewModel.addTask(task);
                    }
//                }
            } else if(responseObject.getResponseType().equals("UPDATE")){
                taskViewModel.updateTask(responseObject.getTasks().get(0));
            }else if(responseObject.getResponseType().equals("UPDATE_FAILURE")){
                taskViewModel.updateTask(responseObject.getTasks().get(0));
                text_welcome.setText("Invalid credentials, update failed");
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            //output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            RequestObject requestObject = new RequestObject("CLOSE",new ArrayList<Task>(),"bla");
            webSocket.send(g.toJson(requestObject));
            webSocket.close(NORMAL_CLOSURE_STATUS, reason);
            //output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            //output("Error : " + t.getMessage());
        }
    }

    private void initControls() {
        text_welcome = (TextView) findViewById(R.id.welcome);
        text_error = (TextView) findViewById(R.id.error);
        button_logout = (Button) findViewById(R.id.logout);
        listView = (ListView) findViewById(R.id.listview_tasks);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initControls();
        client = new OkHttpClient();
        homeViewModel = ViewModelProviders.of(this, new HomeViewModel.Factory(getApplicationContext())).get(HomeViewModel.class);
        taskViewModel = ViewModelProviders.of(this, new TaskViewModel.Factory(getApplicationContext())).get(TaskViewModel.class);
        homeViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    userPrincipal = user;
                    token[0] = "Welcome, " + user.getUsername() + " !";
                } else {
                    Log.d(CLASSNAME, "VALUE USER IS NULL");
                    logoutUser();
                }
            }
        });
        final Context context = this;
        taskViewModel.getTasksLiveData().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                tasksList = tasks;
                if(tasksList.size()==0){
                    text_welcome.setText("You don't have access to the list");
                }
                else if(errorMsg != null){
                    text_welcome.setText(errorMsg);
                }else{
                    text_welcome.setText(token[0]);
                }
                TaskListAdapter adapter = new TaskListAdapter(context, tasks);
                listView.setAdapter(adapter);
            }
        });

//                final String[] token = new String[]{"string"};
//        homeViewModel.getUser().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(@Nullable User user) {
//                if (user != null) {
//                    taskViewModel.getTasks(new GetTasksRequest(user.getToken()));
//                    Log.d(CLASSNAME, "GET USER ");
//                    if (errorMsg == null) {
//                        token[0]="Welcome, " + user.getUsername() + " !";
//                        text_welcome.setText("Welcome, " + user.getUsername() + " !");
//                    } else {
//                        text_welcome.setText(errorMsg);
//                    }
//
//                } else {
//                    Log.d(CLASSNAME, "VALUE USER IS NULL");
//                    // Show ERROR
//                    logoutUser();
//                }
//            }
//        });
//
//        final Context context = this;
//
//        taskViewModel.getTasksLiveData().observe(this, new Observer<List<Task>>() {
//            @Override
//            public void onChanged(List<Task> tasks) {
//                if(tasks.size()==0)
//                    text_welcome.setText("You don't have the credentials to access the list");
//                else
//                    text_welcome.setText(token[0]);
//                TaskListAdapter adapter = new TaskListAdapter(context, tasks);
//                listView.setAdapter(adapter);
//            }
//        });
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Task item = (Task) parent.getItemAtPosition(position);
//                TaskEdit taskEdit = new TaskEdit(item.getDescriere(), item.getId());
//                Intent intent = new Intent(context, EditActivity.class);
//                // using putExtra(String key, Serializable value) method
//                intent.putExtra("TaskEdit", taskEdit);
//                startActivity(intent);
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Task item = (Task) parent.getItemAtPosition(position);
                TaskEdit taskEdit = new TaskEdit(item.getDescriere(), item.getId());
                Intent intent = new Intent(context, EditActivity.class);
                // using putExtra(String key, Serializable value) method
                intent.putExtra("TaskEdit", taskEdit);
                startActivity(intent);
            }
        });
        start();
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.close(1000, "I am closing now");logoutUser();
            }
        });
    }

    private void start() {
        Request request = new Request.Builder().url("ws://192.168.43.75:8080/web-tasks").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private void logoutUser() {
        homeViewModel.clearUserData();
        Log.d(CLASSNAME, "LOG OUT OK");
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}