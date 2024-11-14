package com.relogging.server.domain.plogging.dto

import jakarta.xml.bind.annotation.XmlElement

class VoluteeringDetailApiResponseItem(
    // 봉사 등록 번호
    @field:XmlElement(name = "progrmRegistNo")
    override val programRegistrationNumber: String? = null,
    // 봉사 제목
    @field:XmlElement(name = "progrmSj")
    override val programSubject: String? = null,
    // 봉사 상태
    @field:XmlElement(name = "progrmSttusSe")
    override val programStatus: String? = null,
    // 등록 기관
    @field:XmlElement(name = "nanmmbyNm")
    override val organization: String? = null,
    // 모집 시작일
    @field:XmlElement(name = "noticeBgnde")
    override val noticeBeginDate: String? = null,
    // 모집 종료일
    @field:XmlElement(name = "noticeEndde")
    override val noticeEndDate: String? = null,
    // 봉사 시작일
    @field:XmlElement(name = "progrmBgnde")
    override val programBeginDate: String? = null,
    // 봉사 종료일
    @field:XmlElement(name = "progrmEndde")
    override val programEndDate: String? = null,
    // 활동 시작 시간
    @field:XmlElement(name = "actBeginTm")
    override val actBeginTime: String? = null,
    // 활동 종료 시간
    @field:XmlElement(name = "actEndTm")
    override val actEndTime: String? = null,
    // 활동 장소
    @field:XmlElement(name = "actPlace")
    override val actPlace: String? = null,
    // 성인 가능 여부(Y, N)
    @field:XmlElement(name = "adultPosblAt")
    override val adultPossibleAt: String? = null,
    // 아동 청소년 가능 여부(Y, N)
    @field:XmlElement(name = "yngbgsPosblAt")
    override val youngPossibleAt: String? = null,
    // 구, 군 코드
    @field:XmlElement(name = "gugunCd")
    override val districtCode: String? = null,
    // 시, 도 코드
    @field:XmlElement(name = "sidoCd")
    override val cityProvinceCode: String? = null,
    // 상위 - 하위 분야
    @field:XmlElement(name = "srvcClCode")
    override val field: String? = null,
) : VolunteeringApiResponseItem
