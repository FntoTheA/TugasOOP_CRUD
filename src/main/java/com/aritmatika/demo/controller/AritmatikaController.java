package com.aritmatika.demo.controller;

import com.aritmatika.demo.model.HasilAritmatika;
import com.aritmatika.demo.service.HasilAritmatikaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AritmatikaController {

    private final HasilAritmatikaService service;

    public AritmatikaController(HasilAritmatikaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(name = "editId", required = false) Long editId,
            Model model) {

        List<HasilAritmatika> listHasil = service.findAll();
        model.addAttribute("listHasil", listHasil);

        if (editId != null) {
            Optional<HasilAritmatika> editTarget = service.findById(editId);
            if (editTarget.isPresent()) {
                model.addAttribute("editItem", editTarget.get());
            } else {
                model.addAttribute("errorMessage", "Data dengan ID #" + editId + " tidak ditemukan.");
            }
        } else if (!model.containsAttribute("inputBilangan1") && !listHasil.isEmpty()) {
            HasilAritmatika lastItem = listHasil.get(listHasil.size() - 1);
            model.addAttribute("inputBilangan1", lastItem.getFormattedBilangan1());
            model.addAttribute("inputOperator", lastItem.getOperator());
            model.addAttribute("inputBilangan2", lastItem.getFormattedBilangan2());
            if (!model.containsAttribute("latestCalculation")) {
                model.addAttribute("latestCalculation", lastItem);
            }
        }

        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam("bilangan1") Double bilangan1,
            @RequestParam("operator") String operator,
            @RequestParam("bilangan2") Double bilangan2,
            RedirectAttributes redirectAttributes) {

        try {
            HasilAritmatika saved = service.saveCalculation(bilangan1, operator, bilangan2);
            redirectAttributes.addFlashAttribute("latestCalculation", saved);
            redirectAttributes.addFlashAttribute("inputBilangan1", saved.getFormattedBilangan1());
            redirectAttributes.addFlashAttribute("inputOperator", saved.getOperator());
            redirectAttributes.addFlashAttribute("inputBilangan2", saved.getFormattedBilangan2());
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Operasi " + saved.getFormattedBilangan1() + " " + saved.getOperator() + " "
                            + saved.getFormattedBilangan2() + " = " + saved.getFormattedResult()
                            + " berhasil dihitung dan disimpan ke database.");
        } catch (ArithmeticException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            redirectAttributes.addFlashAttribute("inputBilangan1", HasilAritmatika.formatNumber(bilangan1));
            redirectAttributes.addFlashAttribute("inputOperator", operator);
            redirectAttributes.addFlashAttribute("inputBilangan2", HasilAritmatika.formatNumber(bilangan2));
        }

        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestParam("bilangan1") Double bilangan1,
            @RequestParam("operator") String operator,
            @RequestParam("bilangan2") Double bilangan2,
            RedirectAttributes redirectAttributes) {

        try {
            HasilAritmatika updated = service.updateCalculation(id, bilangan1, operator, bilangan2);
            redirectAttributes.addFlashAttribute("latestCalculation", updated);
            redirectAttributes.addFlashAttribute("inputBilangan1", updated.getFormattedBilangan1());
            redirectAttributes.addFlashAttribute("inputOperator", updated.getOperator());
            redirectAttributes.addFlashAttribute("inputBilangan2", updated.getFormattedBilangan2());
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Data #" + id + " berhasil diperbarui menjadi: "
                            + updated.getFormattedBilangan1() + " " + updated.getOperator() + " "
                            + updated.getFormattedBilangan2() + " = " + updated.getFormattedResult());
        } catch (ArithmeticException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/?editId=" + id;
        }

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Data operasi #" + id + " berhasil dihapus dari tabel dan database PostgreSQL.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/";
    }
}

