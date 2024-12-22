package com.relogging.server.domain.ploggingMeetup

enum class PloggingMeetupSortType(
    val description: String,
    val fieldName: String,
) {
    START_DATE("모집 일시", "meetDateTime"),
    END_DATE("마감 일시", "closeDateTime"),
    HITS("조회수", "viewCount"),
}
