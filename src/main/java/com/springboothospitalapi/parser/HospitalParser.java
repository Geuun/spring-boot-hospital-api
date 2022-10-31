package com.springboothospitalapi.parser;

import com.springboothospitalapi.domain.Hospital;

import java.time.LocalDateTime;
import java.util.Arrays;

public class HospitalParser implements Parser<Hospital>{
    @Override
    public Hospital parse(String str) {
        // str = "1,의원,01_01_02_P,3620000,PHMA119993620020041100004,19990612,,1,영업/정상,13,영업중,,,,,062-515-2875,,500881.0,광주광역시 북구 풍향동 565번지 4호 3층,광주광역시 북구 동문대로 24 3층 (풍향동),61205.0,효치과의원,20211100000000.0,U,2021.11.17 2:40,치과의원,192630.7351,185314.6176,치과의원,1.0,0.0,0.0,52.29,401,치과,,,,0.0,0.0,,,0.0,"

        // String -> int 파싱
        String[] row = str.split(",");
        System.out.println(Arrays.toString(row));
        System.out.println(row[29]);

        Hospital hospital = new Hospital();

        hospital.setId(Integer.parseInt(row[0]));
        hospital.setOpenServiceName(row[1]);
        hospital.setOpenLocalGovernmentCode(Integer.parseInt(row[3]));
        hospital.setManagementNumber(row[4]);

        int year = Integer.parseInt(row[5].substring(0, 4));
        int month = Integer.parseInt(row[5].substring(4, 6));
        int day = Integer.parseInt(row[5].substring(6,8));
        hospital.setLicenseDate(LocalDateTime.of(year, month, day, 0, 0, 0));

        hospital.setBusinessStatus(Integer.parseInt(row[7]));
        hospital.setBusinessStatusCode(Integer.parseInt(row[9]));
        hospital.setPhone(row[15]);
        hospital.setFullAddress(row[18]);
        hospital.setRoadNameAddress(row[19]);
        hospital.setHospitalName(row[21]);
        hospital.setBusinessTypeName(row[25]);
        hospital.setHealthcareProviderCount(Float.parseFloat(row[29]));
        hospital.setPatientRoomCount(Float.parseFloat(row[30]));
        hospital.setTotalNumberOfBeds(Float.parseFloat(row[31]));
        hospital.setTotalAreaSize(Float.parseFloat(row[32]));

        return hospital;
    }
}
