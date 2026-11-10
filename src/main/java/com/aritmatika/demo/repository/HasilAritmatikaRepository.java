package com.aritmatika.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aritmatika.demo.model.HasilAritmatika;

@Repository
public interface HasilAritmatikaRepository extends JpaRepository<HasilAritmatika, Long> {
    List<HasilAritmatika> findAllByOrderByIdDesc();
    List<HasilAritmatika> findAllByOrderByIdAsc();
}

