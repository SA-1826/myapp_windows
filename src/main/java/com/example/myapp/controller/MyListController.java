package com.example.myapp.controller;

import com.example.myapp.model.MyList;
import com.example.myapp.service.MyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class MyListController {
  @Autowired
  private MyListService myListService;

  // 全権取得
  @GetMapping
  public List<MyList> getAllLists() {
    return myListService.findAll();
  }

  // ID指定で1件取得 (GET /lists/{id})
  @GetMapping("/{id}")
  public MyList getListById(@PathVariable Long id) {
    return myListService.findById(id).orElseThrow(() -> new RuntimeException("ID " + id + "のリストが見つかりませんでした"));
  }

  // 新規作成 (POST /lists)
  @PostMapping
  public MyList createList(@RequestBody MyList myList) {
    return myListService.save(myList);
  }

  // 更新 (PUT /lists/{id})
  @PutMapping("/{id}")
  public MyList updateList(@PathVariable Long id, @RequestBody MyList myList) {
    return myListService.update(id, myList);
  }

  // 削除 (DELETE /lists/{id})
  @DeleteMapping("/{id}")
  public void deleteList(@PathVariable Long id) {
    myListService.delete(id);
  }
}
