package com.example.toma.androidsapt10.Repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.toma.androidsapt10.API.APIservice;
import com.example.toma.androidsapt10.DAO.TaskDao;
import com.example.toma.androidsapt10.DAO.UserDao;
import com.example.toma.androidsapt10.Entities.Task;
import com.example.toma.androidsapt10.Entities.User;
import com.example.toma.androidsapt10.UI.HomeActivity;
import com.example.toma.androidsapt10.vo.GetTasksRequest;
import com.example.toma.androidsapt10.vo.Page;
import com.example.toma.androidsapt10.vo.TaskRequest;
import com.example.toma.androidsapt10.vo.TaskVO;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskRepository
{
    private static final String CLASSNAME = "TASK REPOSITORY";
    private TaskDao taskDao;
    private static TaskRepository repositoryInstance;
    Executor executor;

    private TaskRepository(TaskDao taskDao)
    {
        this.taskDao=taskDao;
        executor = Executors.newSingleThreadExecutor();
    }

    public static  TaskRepository getInstance(TaskDao taskDao)
    {
        if(repositoryInstance==null)
        {
            repositoryInstance=new TaskRepository(taskDao);
        }
        return repositoryInstance;
    }

    private APIservice getAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIservice.MY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice api = retrofit.create(APIservice.class);
        return api;
    }


    public boolean getTasks(GetTasksRequest getTaskRequest)
    {
        final Boolean[] message = new Boolean[]{false};
        getAPIService()
                .getTasks(getTaskRequest)
                .enqueue(new Callback<Page>() {
                    @Override
                    public void onResponse(Call<Page> call, final Response<Page> response) {
                        Log.d(CLASSNAME,"CALL GET TASKS SUCCESS");
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                Page page = response.body();

                                taskDao.deleteAll();
                                if(page !=null) {
                                    for (TaskVO task : page.getTasks()) {
                                        Task taskDB = new Task(task.getId(), task.getTitlu(), task.getDescriere(), new Date());
                                        taskDao.insert(taskDB);
                                    }
                                    message[0]=true;
                                }
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<Page> call, Throwable t) {
                        Log.d(CLASSNAME,"CALL GET TASKS FAILED",t);
                    }
                });
        return message[0];
    }

    public void update(TaskRequest taskRequest)
    {
        getAPIService()
                .update(taskRequest)
                .enqueue(new Callback<TaskVO>() {
                    @Override
                    public void onResponse(Call<TaskVO> call, final Response<TaskVO> response) {
                        Log.d(CLASSNAME,"CALL UPDATE SUCCESS");
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                TaskVO task=response.body();
                                HomeActivity.errorMsg="This user is unable to perform updates";
                                if(task!=null) {
                                    Task taskDB = new Task(task.getId(), task.getTitlu(), task.getDescriere(), new Date());
                                    taskDao.update(taskDB);
                                    HomeActivity.errorMsg=null;
                                }
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {

                        Log.d(CLASSNAME,"CALL UPDATE FAILED",t);
                    }
                });
    }

    public LiveData<List<Task>> getCacheTasks()
    {
        return taskDao.getTasks();
    }

    public void addTask(Task task) {
        taskDao.insert(task);
    }

    public void deleteAll() {
        taskDao.deleteAll();
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }
}