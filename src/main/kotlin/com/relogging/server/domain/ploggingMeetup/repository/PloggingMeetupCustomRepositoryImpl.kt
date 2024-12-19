package com.relogging.server.domain.ploggingMeetup.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.relogging.server.domain.ploggingMeetup.PloggingMeetupSortType
import com.relogging.server.domain.ploggingMeetup.entity.PloggingMeetup
import com.relogging.server.domain.ploggingMeetup.entity.QPloggingMeetup
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDateTime

class PloggingMeetupCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : PloggingMeetupCustomRepository {
    override fun findPloggingMeetups(
        region: String?,
        isOpen: Boolean?,
        pageable: Pageable,
        sortBy: PloggingMeetupSortType?,
        sortDirection: Sort.Direction,
    ): Page<PloggingMeetup> {
        val meetup = QPloggingMeetup.ploggingMeetup
        val now = LocalDateTime.now()

        val query =
            queryFactory
                .selectFrom(meetup)
                .leftJoin(meetup.host).fetchJoin()
                .where(
                    containsRegion(meetup, region),
                    filterByOpenStatus(meetup, isOpen, now),
                )

        val totalCount =
            queryFactory
                .select(meetup.count())
                .from(meetup)
                .where(
                    containsRegion(meetup, region),
                    filterByOpenStatus(meetup, isOpen, now),
                )
                .fetchOne() ?: 0L

        val orderSpecifier =
            when (sortBy) {
                PloggingMeetupSortType.START_DATE ->
                    if (sortDirection == Sort.Direction.ASC) {
                        meetup.startDate.asc()
                    } else {
                        meetup.startDate.desc()
                    }
                PloggingMeetupSortType.END_DATE ->
                    if (sortDirection == Sort.Direction.ASC) {
                        meetup.endDate.asc()
                    } else {
                        meetup.endDate.desc()
                    }
                PloggingMeetupSortType.HITS ->
                    if (sortDirection == Sort.Direction.ASC) {
                        meetup.hits.asc()
                    } else {
                        meetup.hits.desc()
                    }
                null -> meetup.startDate.desc()
            }

        val results =
            query
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .orderBy(orderSpecifier)
                .fetch()

        return PageImpl(results, pageable, totalCount)
    }

    private fun containsRegion(
        meetup: QPloggingMeetup,
        region: String?,
    ): BooleanExpression? {
        return region?.let { meetup.region.contains(it) }
    }

    private fun filterByOpenStatus(
        meetup: QPloggingMeetup,
        isOpen: Boolean?,
        now: LocalDateTime,
    ): BooleanExpression? {
        return isOpen?.let {
            if (it) {
                meetup.endDate.goe(now)
            } else {
                null
            }
        }
    }
}
