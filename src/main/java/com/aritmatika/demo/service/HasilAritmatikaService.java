package com.aritmatika.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aritmatika.demo.model.HasilAritmatika;
import com.aritmatika.demo.repository.HasilAritmatikaRepository;

@Service
public class HasilAritmatikaService {

    private final HasilAritmatikaRepository repository;

    public HasilAritmatikaService(HasilAritmatikaRepository repository) {
        this.repository = repository;
    }

    public List<HasilAritmatika> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Optional<HasilAritmatika> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public HasilAritmatika saveCalculation(Double bilangan1, String operator, Double bilangan2) {
        HasilAritmatika item = new HasilAritmatika(bilangan1, operator, bilangan2);
        return repository.save(item);
    }

    @Transactional
    public HasilAritmatika updateCalculation(Long id, Double bilangan1, String operator, Double bilangan2) {
        HasilAritmatika existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data dengan ID " + id + " tidak ditemukan."));
        existing.setBilangan1(bilangan1);
        existing.setOperator(operator);
        existing.setBilangan2(bilangan2);
        existing.setResult(HasilAritmatika.calculateResult(bilangan1, operator, bilangan2));
        return repository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Data dengan ID " + id + " tidak ditemukan.");
        }
        repository.deleteById(id);
    }
}

