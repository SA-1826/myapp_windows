package com.example.myapp.service;

import com.example.myapp.model.MyList;
import com.example.myapp.repository.MyListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyListService {
  
  @Autowired
  private MyListRepository myListRepository;

  // 一覧取得
  public List<MyList> findAll() {
    return myListRepository.findAll();
  }

  // IDで取得
  public Optional<MyList> findById(Long id) {
    return myListRepository.findById(id);
  }

  // 新規作成 or 更新（保存）
  public MyList save(MyList myList) {
    return myListRepository.save(myList);
  }

  // 削除
  public void deleteById(Long id) {
    myListRepository.deleteById(id);
  }
}
