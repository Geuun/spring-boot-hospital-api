package com.springboothospitalapi.controller;

import com.springboothospitalapi.dao.HospitalDao;
import com.springboothospitalapi.domain.Hospital;
import com.springboothospitalapi.domain.dto.HospitalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo: id 110000의 1병원 이름, 2주소, 3도로명주소, 4의료진 수, 5병상 수, 6면적, 7폐업여부
 */
@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalDao hospitalDao;


    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> get(@PathVariable Integer id) {
        Hospital hospital = hospitalDao.findById(id);
            return ResponseEntity.ok().body(hospital);
    }
}
