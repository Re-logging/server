package com.relogging.server.domain.plogging.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.relogging.server.domain.plogging.PloggingEventSortType
import com.relogging.server.domain.plogging.entity.PloggingEvent
import com.relogging.server.domain.plogging.entity.QPloggingEvent
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDate

class PloggingEventCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : PloggingEventCustomRepository {
    override fun findPloggingEvents(
        region: String?,
        isOpen: Boolean?,
        pageable: Pageable,
        sortBy: PloggingEventSortType?,
        sortDirection: Sort.Direction,
    ): Page<PloggingEvent> {
        val event = QPloggingEvent.ploggingEvent
        val now = LocalDate.now()

        val query =
            queryFactory
                .selectFrom(event)
                .where(
                    containsRegion(event, region),
                    filterByOpenStatus(event, isOpen, now),
                )

        val totalCount =
            queryFactory
                .select(event.count())
                .from(event)
                .where(
                    containsRegion(event, region),
                    filterByOpenStatus(event, isOpen, now),
                )
                .fetchOne() ?: 0L

        val orderSpecifier =
            when (sortBy) {
                PloggingEventSortType.START_DATE ->
                    if (sortDirection == Sort.Direction.ASC) {
                        event.startDate.asc()
                    } else {
                        event.startDate.desc()
                    }
                PloggingEventSortType.END_DATE ->
                    if (sortDirection == Sort.Direction.ASC) {
                        event.endDate.asc()
                    } else {
                        event.endDate.desc()
                    }
                PloggingEventSortType.HITS ->
                    if (sortDirection == Sort.Direction.ASC) {
                        event.hits.asc()
                    } else {
                        event.hits.desc()
                    }
                null -> event.startDate.desc()
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
        event: QPloggingEvent,
        region: String?,
    ): BooleanExpression? {
        return region?.let { event.region.contains(it) }
    }

    private fun filterByOpenStatus(
        event: QPloggingEvent,
        isOpen: Boolean?,
        now: LocalDate,
    ): BooleanExpression? {
        return isOpen?.let {
            if (it) {
                event.endDate.goe(now)
            } else {
                null
            }
        }
    }
}
