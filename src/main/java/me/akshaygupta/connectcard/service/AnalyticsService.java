package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.LinkClickCount;
import org.springframework.stereotype.Service;

import me.akshaygupta.connectcard.dto.AnalyticsSummaryResponse;
import me.akshaygupta.connectcard.dto.LinkAnalyticsDto;
import me.akshaygupta.connectcard.model.Link;
import me.akshaygupta.connectcard.repository.ClickEventRepository;
import me.akshaygupta.connectcard.repository.LinkRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ClickEventRepository clickEventRepository;
    private final LinkRepository linkRepository;

    public AnalyticsSummaryResponse getSummary(
            String profileId,
            Instant from,
            Instant to) {

        Instant start = from != null ? from : Instant.EPOCH;
        Instant end = to != null ? to : Instant.now();

        List<LinkClickCount> clickCounts =
                clickEventRepository.aggregateClicksByProfileAndDate(
                        profileId, start, end);

        Map<String, Long> clickMap = clickCounts.stream()
                .collect(Collectors.toMap(
                        LinkClickCount::getLinkId,
                        LinkClickCount::getClicks
                ));

        List<LinkAnalyticsDto> links = linkRepository
                .findByProfileIdOrderByPositionAsc(profileId)
                .stream()
                .map(link -> new LinkAnalyticsDto(
                        link.getId(),
                        link.getTitle(),
                        clickMap.getOrDefault(link.getId(), 0L)
                ))
                .collect(Collectors.toList());

        long totalClicks = clickMap.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        return AnalyticsSummaryResponse.builder()
                .totalClicks(totalClicks)
                .links(links)
                .build();
    }
}
