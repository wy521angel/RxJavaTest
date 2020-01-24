package com.example.rxjavatest.network;

import com.example.rxjavatest.model.Repo;
import com.example.rxjavatest.model.User;

import java.util.List;

import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("users/{username}/repos")
    Single<List<Repo>> getRepos(@Path("username") String userName);

    @GET("users/{username}/{repo_name}/forks")
    Single<List<Repo>> getForks(@Path("username") String userName,@Path("repo_name") String repoName);

    @GET("repos/wy521angel/{repo_name}")
    Single<Repo> getRepo(@Path("repo_name") String repoName);

    @GET("users")
    Single<List<User>> getUsers();

    @GET("users/{username}")
    Single<User> getUser(@Path("username") String userName);
}
