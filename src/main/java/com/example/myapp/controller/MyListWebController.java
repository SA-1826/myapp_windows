package com.example.myapp.controller;

import com.example.myapp.model.MyList;
import com.example.myapp.service.MyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

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
    MyList list = myListService.findById(id).orElseThrow(() -> new IllegalArgumentException("指定されたIDのデータが存在しません" + id));
    model.addAttribute("myList", list);
    model.addAttribute("timestamp", System.currentTimeMillis());
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
  public String create(@ModelAttribute @Valid MyList myList, BindingResult result, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes, Model model) throws IOException {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "作成に失敗しました。入力内容を確認してください。");
      return "lists/new";
    }

    if (!imageFile.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
      String uploadDir = System.getProperty("user.home") + "/myapp/uploads/";

      File uploadPath = new File(uploadDir);
      if (!uploadPath.exists()) {
        uploadPath.mkdirs();
      }

      File dest = new File(uploadDir + fileName);
      imageFile.transferTo(dest); // ファイルを保存

      String webPath = "/uploads/" + fileName;
      myList.setImagePath(webPath); // 表示用にパス保存
    }
    MyList savedList = myListService.save(myList); // 保存して戻り値をsavedListに受け取る
    redirectAttributes.addFlashAttribute("message", "作成しました！");
    return "redirect:/lists/" + savedList.getId();
  }

  // 編集画面表示（GET /lists/{id}/edit）
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    MyList list = myListService.findById(id).orElseThrow(() -> new IllegalArgumentException("指定されたIDのデータが存在しません" + id));
    model.addAttribute("myList", list);
    return "lists/edit";
  }

  // 更新保存 (POST /lists/{id}/update)
  @PostMapping("/{id}/update")
  public String update(@PathVariable Long id, @ModelAttribute @Valid MyList myList, BindingResult result, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes, Model model) throws IOException {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "更新に失敗しました。入力内容を確認してください。");
      return "lists/edit";
    }

    // 既存データ取得
    MyList exsistingList = myListService.findById(id).orElseThrow(() -> new IllegalArgumentException("指定されたIDのデータが存在しません" + id));
    
    // 新しい画像ファイルがアップロードされた場合
    if (!imageFile.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
      String uploadDir = System.getProperty("user.home") + "/myapp/uploads/";

      File uploadPath = new File(uploadDir);
      if (!uploadPath.exists()) {
        uploadPath.mkdirs();
      }

      File dest = new File(uploadDir + fileName);
      imageFile.transferTo(dest);

      myList.setImagePath("/uploads/" + fileName); // 新しいパスをセット
    }
    else {
      myList.setImagePath(exsistingList.getImagePath()); // 新しい画像がアップロードされなければ既存のパスを維持
    }

    myList.setId(id);
    MyList updatedList = myListService.update(id, myList);
    redirectAttributes.addFlashAttribute("message", "更新しました！");
    return "redirect:/lists/" + updatedList.getId() + "?t=" + System.currentTimeMillis();
  }

  // 削除 (POST /lists/{id}/delete)
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    myListService.delete(id);
    redirectAttributes.addFlashAttribute("message", "削除しました。");
    return "redirect:/lists";
  }
}
