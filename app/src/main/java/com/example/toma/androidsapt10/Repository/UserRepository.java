package com.example.toma.androidsapt10.Repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.toma.androidsapt10.API.APIservice;
import com.example.toma.androidsapt10.DAO.UserDao;
import com.example.toma.androidsapt10.Entities.User;
import com.example.toma.androidsapt10.vo.AccountRequest;
import com.example.toma.androidsapt10.vo.AccountResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository
{
    private static final String CLASSNAME = "USER REPOSITORY";
    private UserDao userDao;
    private static UserRepository repositoryInstance;
    Executor executor;

    private UserRepository(UserDao userDao)
    {

        this.userDao=userDao;
        executor = Executors.newSingleThreadExecutor();
    }

    public static  UserRepository getInstance(UserDao userDao)
    {
        if(repositoryInstance==null)
        {
            repositoryInstance=new UserRepository(userDao);
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

    public boolean login(String username, String password)
    {
        final Boolean[] message = new Boolean[]{false};

        getAPIService()
                .login(new AccountRequest(username,password))
                .enqueue(new Callback<AccountResponse>() {
                    @Override
                    public void onResponse(Call<AccountResponse> call, final Response<AccountResponse> response) {
                        Log.d(CLASSNAME,"CALL LOGIN SUCCESS");
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(response.code()==200) {
                                    String username = response.body().getUsername();
                                    String password = response.body().getPassword();
                                    String token2 = response.body().getToken();
                                    User user = new User(username, password, token2);
                                    userDao.insert(user);
                                    User user2 = userDao.getCurrentUser().getValue();
                                    message[0] = true;
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<AccountResponse> call, Throwable t) {
                        Log.d(CLASSNAME,"CALL LOGIN FAILED",t);
                    }
                });

        return message[0];
    }

    public boolean userExists(String username, String password)
    {
        LiveData<User> user=userDao.getUser(username);
        if(user==null)
        {
            return false;
        }
        return user.getValue().getPassword().equals(password);
    }

    public LiveData<User> getUser() {
        return userDao.getCurrentUser();
    }

    public void clearUserCached() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAll();
            }
        });
    }

}
