package com.relogging.server.domain.plogging.dto

interface VolunteeringApiResponseItem {
    val programRegistrationNumber: String?
    val programSubject: String?
    val programStatus: String?
    val organization: String?
    val noticeBeginDate: String?
    val noticeEndDate: String?
    val programBeginDate: String?
    val programEndDate: String?
    val actBeginTime: String?
    val actEndTime: String?
    val actPlace: String?
    val adultPossibleAt: String?
    val youngPossibleAt: String?
    val districtCode: String?
    val cityProvinceCode: String?
    val field: String?
}
