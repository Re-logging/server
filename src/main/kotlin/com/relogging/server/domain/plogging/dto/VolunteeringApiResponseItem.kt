package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class VolunteeringApiResponseItem(
    // 활동 시작 시간
    @field:XmlElement(name = "actBeginTm")
    val actBeginTime: String? = null,
    // 활동 종료 시간
    @field:XmlElement(name = "actEndTm")
    val actEndTime: String? = null,
    // 활동 장소
    @field:XmlElement(name = "actPlace")
    val actPlace: String? = null,
    // 성인 가능 여부(Y, N)
    @field:XmlElement(name = "adultPosblAt")
    val adultPossibleAt: String? = null,
    // 아동 청소년 가능 여부(Y, N)
    @field:XmlElement(name = "yngbgsPosblAt")
    val youngPossibleAt: String? = null,
    // 구, 군 코드
    @field:XmlElement(name = "gugunCd")
    val districtCode: String? = null,
    // 시, 도 코드
    @field:XmlElement(name = "sidoCd")
    val cityProvinceCode: String? = null,
    // 모집 기관
    @field:XmlElement(name = "nanmmbyNm")
    val organization: String? = null,
    // 모집 시작일
    @field:XmlElement(name = "noticeBgnde")
    val noticeBeginDate: String? = null,
    // 모집 종료일
    @field:XmlElement(name = "noticeEndde")
    val noticeEndDate: String? = null,
    // 봉사 시작일
    @field:XmlElement(name = "progrmBgnde")
    val programBeginDate: String? = null,
    // 봉사 종료일
    @field:XmlElement(name = "progrmEndde")
    val programEndDate: String? = null,
    // 봉사 등록 번호
    @field:XmlElement(name = "progrmRegistNo")
    val programRegistrationNumber: String? = null,
    // 봉사 제목
    @field:XmlElement(name = "progrmSj")
    val programSubject: String? = null,
    // 봉사 상태
    @field:XmlElement(name = "progrmSttusSe")
    val programStatus: String? = null,
    // 상위 - 하위 분야
    @field:XmlElement(name = "srvcClCode")
    val field: String? = null,
    // url
    @field:XmlElement(name = "url")
    val url: String? = null,
//    // 활동 시작 시간
//    @XmlElement(name = "actBeginTm")
//    val actBeginTm: String,
//    // 활동 종료 시간
//    @XmlElement(name = "actEndTm")
//    val actEndTm: String,
//    // 활동 장소
//    @XmlElement(name = "actPlace")
//    val actPlace: String,
//    // 성인 가능 여부(Y, N)
//    @XmlElement(name = "adultPosblAt")
//    val adultPosblAt: String,
//    // 아동 청소년 가능 여부(Y, N)
//    @XmlElement(name = "yngbgsPosblAt")
//    val yngbgsPosblAt: String,
//    // 구, 군 코드
//    @XmlElement(name = "gugunCd")
//    val gugunCd: String,
//    // 시, 도 코드
//    @XmlElement(name = "sidoCd")
//    val sidoCd: String,
//    // 모집 기관
//    @XmlElement(name = "nanmmbyNm")
//    val nanmmbyNm: String,
//    // 모집 시작일
//    @XmlElement(name = "noticeBgnde")
//    val noticeBgnde: String,
//    // 모집 종료일
//    @XmlElement(name = "noticeEndde")
//    val noticeEndde: String,
//    // 봉사 시작일
//    @XmlElement(name = "progrmBgnde")
//    val progrmBgnde: String,
//    // 봉사 종료일
//    @XmlElement(name = "progrmEndde")
//    val progrmEndde: String,
//    // 봉사 등록 번호
//    @XmlElement(name = "progrmRegistNo")
//    val progrmRegistNo: String,
//    // 봉사 제목
//    @XmlElement(name = "progrmSj")
//    val progrmSj: String,
//    // 봉사 상태
//    @XmlElement(name = "progrmSttusSe")
//    val progrmSttusSe: String,
//    // 상위 - 하위 분야
//    @XmlElement(name = "srvcClCode")
//    val srvcClCode: String,
//    // url
//    @XmlElement(name = "url")
//    val url: String,
)
