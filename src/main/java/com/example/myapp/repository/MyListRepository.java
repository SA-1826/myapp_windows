package com.example.myapp.repository;

import com.example.myapp.model.MyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyListRepository extends JpaRepository<MyList, Long> {
}
