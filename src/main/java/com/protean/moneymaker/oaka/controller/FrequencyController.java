package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.rin.model.FrequencyType;
import com.protean.moneymaker.rin.service.FrequencyService;
import com.protean.moneymaker.rin.util.FrequencyUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/frequencyTypes")
public class FrequencyController {

    private FrequencyService frequencyService;

    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllFrequencyTypes() {

        List<FrequencyType> types = frequencyService.getAllFrequencyTypes();

        return ok(FrequencyUtil.convertFrequencyTypesToDto(types));
    }
}
