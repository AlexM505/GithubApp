package com.alxd.githubapp.db;

import android.util.SparseIntArray;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alxd.githubapp.model.Contributor;
import com.alxd.githubapp.model.Repo;
import com.alxd.githubapp.model.RepoSearchResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Repo... repos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertContributors(List<Contributor> contributors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRepos(List<Repo> repositories);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void createRepoIfNotExist(Repo repo);

    @Query("SELECT * FROM REPO WHERE OWNER_LOGIN = :login AND NAME = :name")
    public abstract LiveData<Repo> load(String login , String name);

    @Query("SELECT login, avatarUrl, repoName, repoOwner, contributions FROM CONTRIBUTOR WHERE repoName = :name AND repoOwner = :owner ORDER BY contributions DESC")
    public abstract LiveData<List<Contributor>> loadContributors(String owner, String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RepoSearchResult result);

    @Query("SELECT * FROM reposearchresult WHERE query = :query")
    public abstract LiveData<RepoSearchResult> search(String query);

    @Query("SELECT * FROM repo WHERE id in (:repoIds)")
    protected abstract LiveData<List<Repo>> loadById(List<Integer> repoIds);

    @Query("SELECT * FROM reposearchresult WHERE query = :query")
    public abstract RepoSearchResult findSearchResult(String query);

//    public LiveData<List<Repo>> loadOrdered(List<Integer> repoIds){
//        SparseIntArray order = new SparseIntArray();
//        int index = 0;
//        for(Integer repoId : repoIds){
//            order.put(repoId, index++);
//        }
//
//        return Transformations.map(loadById(repoIds), new Function<List<Repo>, List<Repo>>() {
//            @Override
//            public List<Repo> apply(List<Repo> repositories) {
//                Collections.sort(repositories, new Comparator<Repo>() {
//                    @Override
//                    public int compare(Repo o1, Repo o2) {
//                        int pos1 = order.get(o1.id);
//                        int pos2 = order.get(o2.id);
//                        return pos1 - pos2;
//                    }
//                });
//            }
//        });
//    }
}
