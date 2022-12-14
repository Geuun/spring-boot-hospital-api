package com.springboothospitalapi.parser;

import com.springboothospitalapi.dao.HospitalDao;
import com.springboothospitalapi.domain.Hospital;
import com.springboothospitalapi.service.HospitalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HospitalParserTest {

    String line1 = "1,의원,01_01_02_P,3620000,PHMA119993620020041100004,19990612,,1,영업/정상,13,영업중,,,,,062-515-2875,,500881.0,광주광역시 북구 풍향동 565번지 4호 3층,광주광역시 북구 동문대로 24 3층 (풍향동),61205.0,효치과의원,20211100000000.0,U,2021.11.17 2:40,치과의원,192630.7351,185314.6176,치과의원,1,0,0,52.29,401,치과,,,,0.0,0.0,,,0.0,";

    @Autowired
    ReadLineContext<Hospital> hospitalReadLineContext;

    @Autowired
    HospitalDao hospitalDao;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospitalParser hospitalParser;

    @Test
    @DisplayName("Hospital이 Insert 와 Select가 잘 되는지 Test")
    void insertAndSelect() {
        hospitalDao.deleteAll();
        assertEquals(0, hospitalDao.getCount());

        HospitalParser hospitalParser = new HospitalParser();
        Hospital hospital = hospitalParser.parse(line1);
        hospitalDao.add(hospital); // Autowired 해서 new 안해도 됨
        assertEquals(1, hospitalDao.getCount());
        Hospital selectedHospital = hospitalDao.findById(hospital.getId());

        assertEquals(selectedHospital.getId(), hospital.getId());
        assertEquals(selectedHospital.getOpenServiceName(), hospital.getOpenServiceName());
        assertEquals(selectedHospital.getOpenLocalGovernmentCode(),hospital.getOpenLocalGovernmentCode());
        assertEquals(selectedHospital.getManagementNumber(),hospital.getManagementNumber());
        assertEquals(selectedHospital.getBusinessStatus(), hospital.getBusinessStatus());
        assertEquals(selectedHospital.getBusinessStatusCode(), hospital.getBusinessStatusCode());
        assertTrue(selectedHospital.getLicenseDate().isEqual(hospital.getLicenseDate()));
        assertEquals(selectedHospital.getPhone(), hospital.getPhone());
        assertEquals(selectedHospital.getFullAddress(), hospital.getFullAddress());
        assertEquals(selectedHospital.getRoadNameAddress(), hospital.getRoadNameAddress());
        assertEquals(selectedHospital.getHospitalName(), hospital.getHospitalName());
        assertEquals(selectedHospital.getBusinessTypeName(), hospital.getBusinessTypeName());
        assertEquals(selectedHospital.getHealthcareProviderCount(), hospital.getHealthcareProviderCount());
        assertEquals(selectedHospital.getPatientRoomCount(), hospital.getPatientRoomCount());
        assertEquals(selectedHospital.getTotalNumberOfBeds(), hospital.getTotalNumberOfBeds());
        assertEquals(selectedHospital.getTotalAreaSize(), hospital.getTotalAreaSize());
    }

    @Test
    @DisplayName("대용량 데이터에도 잘 파싱이 되는지 Test")
    void oneHundradThousandRowsTest() throws IOException {
        hospitalDao.deleteAll();
        String fileName = "/Users/geun/Desktop/CodeLion/data/spring-boot-hospital-api/PreprocessedHospitalData.csv";
        int dataCnt = this.hospitalService.insertLargeVolumeHospitalData(fileName);
        assertTrue(dataCnt > 1000);
        assertTrue(dataCnt > 10000);

        System.out.printf("파싱된 데이터의 수: %d", dataCnt);
    }

    @Test
    @DisplayName("csv 1줄을 Hospital로 잘 만드는지 Test")
    void convertToHospital() {

        System.out.println((hospitalParser.parse(line1)));
        Hospital hospital = hospitalParser.parse(line1);

        assertEquals(1, hospital.getId());
        assertEquals("의원", hospital.getOpenServiceName());
        assertEquals(3620000, hospital.getOpenLocalGovernmentCode());
        assertEquals("PHMA119993620020041100004", hospital.getManagementNumber());
        assertEquals(LocalDateTime.of(1999, 6, 12, 0, 0, 0), hospital.getLicenseDate()); //19990612
        assertEquals(1, hospital.getBusinessStatus());
        assertEquals(13, hospital.getBusinessStatusCode());
        assertEquals("062-515-2875", hospital.getPhone());
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress());
        assertEquals("광주광역시 북구 동문대로 24 3층 (풍향동)", hospital.getRoadNameAddress());
        assertEquals("효치과의원", hospital.getHospitalName());
        assertEquals("치과의원", hospital.getBusinessTypeName());
        assertEquals(1.0, hospital.getHealthcareProviderCount());
        assertEquals(0, hospital.getPatientRoomCount());
        assertEquals(0, hospital.getTotalNumberOfBeds());
        assertEquals(52.29f, hospital.getTotalAreaSize());
    }

    @Test
    @DisplayName("add 와 deleteById 메서드 Test")
    void addAndDeleteById() {

        hospitalDao.deleteAll();
        assertEquals(0, hospitalDao.getCount());
        Hospital hospital = hospitalParser.parse(line1);
        hospitalDao.add(hospital);
        assertEquals(1,hospitalDao.getCount());
        hospitalDao.deleteById(hospital.getId());
        assertEquals(0, hospitalDao.getCount());

        for (int i = 0 ; i < hospitalDao.getCount(); i++ ) {
        }


    }
}