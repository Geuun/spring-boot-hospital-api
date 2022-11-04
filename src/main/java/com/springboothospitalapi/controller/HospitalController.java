package com.springboothospitalapi.controller;

import com.springboothospitalapi.dao.HospitalDao;
import com.springboothospitalapi.domain.Hospital;
import com.springboothospitalapi.domain.dto.HospitalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Todo: id 110000의 1병원 이름, 2주소, 3도로명주소, 4의료진 수, 5병상 수, 6면적, 7폐업여부
 */
@Slf4j
@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalDao hospitalDao;

    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Hospital> get(@PathVariable Integer id) {
        log.info("/get/{id}, findById()");
        Hospital hospital = hospitalDao.findById(id);
        Optional<Hospital> opt = Optional.of(hospital);

        if (!opt.isEmpty()) {
            return ResponseEntity.ok().body(hospital);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Hospital());
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        log.info("/delete/{id}, deleteById");
        hospitalDao.deleteById(id);
        return id + "에 해당하는 정보가 삭제되었습니다.";
    }

    @DeleteMapping("/delete/deleteall")
    public String deleteAll() {
        log.info("/delete/deleteall, deleteAll()");
        hospitalDao.deleteAll();
        return "모든 데이터가 삭제되었습니다.";
    }
}