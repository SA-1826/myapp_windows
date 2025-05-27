package com.example.myapp.controller;

import com.example.myapp.model.MyList;
import com.example.myapp.service.MyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lists")
public class MyListWebController {
  @Autowired
  private MyListService myListService;

  // 一覧表示（GET /lists）
  @GetMapping
  public String index(Model model) {
    List<MyList> lists = myListService.findAll();
    model.addAttribute("lists", lists);
    return "lists/index"; // index.htmlを表示する
  }

  // 詳細表示（GET /lists/{id}）
  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    MyList list = myListService.findById(id).orElseThrow(() -> new RuntimeException("ID " + id + " のデータが見つかりません"));
    model.addAttribute("myList", list);
    return "lists/show";
  }

  // 新規作成フォーム表示 (GET /lists/new)
  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("myList", new MyList());
    return "lists/new";
  }

  // 新規作成保存（POST /lists）
  @PostMapping
  public String create(@ModelAttribute MyList myList) {
    myListService.save(myList);
    return "redirect:/lists";
  }

  // 編集画面表示（GET /lists/{id}/edit）
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    MyList list = myListService.findById(id).orElseThrow(() -> new RuntimeException("ID " + id + " のデータが見つかりません"));
    model.addAttribute("list", list);
    return "lists/edit";
  }

  // 更新保存 (POST /lists/{id}/update)
  @PostMapping("/{id}/update")
  public String update(@PathVariable Long id, @ModelAttribute MyList myList) {
    myListService.update(id, myList);
    return "redirect:/lists";
  }

  // 削除 (POST /lists/{id}/delete)
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    myListService.delete(id);
    return "redirect:/lists";
  }
}
