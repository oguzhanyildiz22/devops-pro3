package com.project.project2.controller;

import com.project.project2.model.Adviser;
import com.project.project2.repository.AdviserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class AdviserController {

    private final AdviserRepository adviserRepository;

    @GetMapping("/adviser")
    public String getAdviser(Model model) {
        List<Adviser> advisers = adviserRepository.findAllAscById();
        model.addAttribute("advisers", advisers);
        return "adviser/index";
    }

    @PostMapping("/adviser/add")
    public String addAdviser(@Valid Adviser adviser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("adviser", adviser);
            model.addAttribute("error", result.getAllErrors());
            return "/adviser/add";
        }
        adviser.setName(convertFirstLetterToUpperCase(adviser.getName()));
        adviser.setDepartment(convertFirstLetterToUpperCase(adviser.getDepartment()));

        adviserRepository.save(adviser);
        return "redirect:/adviser";
    }

    @GetMapping("/adviser/update/{id}")
    @ResponseBody
    public Adviser updateAdviser(@PathVariable int id) {
        return adviserRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Adviser not found!:" + id)
        );
    }

    @PostMapping("/adviser/update")
    public String updateAdviser(@Valid Adviser adviser, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/adviser";
        }
        adviser.setName(convertFirstLetterToUpperCase(adviser.getName()));
        adviser.setDepartment(convertFirstLetterToUpperCase(adviser.getDepartment()));

        adviserRepository.save(adviser);
        return "redirect:/adviser";
    }

    @GetMapping("/adviser/delete/{id}")
    @ResponseBody
    public Adviser deleteAdviser(@PathVariable int id) {
        return adviserRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Adviser not found!:" + id)
        );
    }

    @PostMapping("/adviser/delete")
    public String deleteAdviser(Adviser adviser) {
        adviserRepository.delete(adviser);
        return "redirect:/adviser";
    }

    @GetMapping("/adviser/getAdvisers")
    public ResponseEntity<List<Adviser>> getAllStudies() {
        List<Adviser> advisers = adviserRepository.findAll();
        return ResponseEntity.ok(advisers);
    }

    private String convertFirstLetterToUpperCase(String input) {
        return Pattern.compile("\\b(\\w)").matcher(input).replaceAll(m -> m.group().toUpperCase());
    }




}
