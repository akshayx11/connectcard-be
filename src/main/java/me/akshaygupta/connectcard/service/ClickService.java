package me.akshaygupta.connectcard.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import me.akshaygupta.connectcard.model.ClickEvent;
import me.akshaygupta.connectcard.model.Link;
import me.akshaygupta.connectcard.repository.ClickEventRepository;
import me.akshaygupta.connectcard.repository.LinkRepository;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final ClickEventRepository clickEventRepository;
    private final LinkRepository linkRepository;

    public String registerClick(String linkId, HttpServletRequest request) {

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.isActive()) {
            throw new RuntimeException("Link is disabled");
        }

        ClickEvent event = ClickEvent.builder()
                .linkId(link.getId())
                .profileId(link.getProfileId())
                .ip(getClientIp(request))
                .userAgent(request.getHeader("User-Agent"))
                .referrer(request.getHeader("Referer"))
                .build();

        clickEventRepository.save(event);

        link.setClicks(link.getClicks() + 1);
        linkRepository.save(link);

        return link.getUrl();
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null) return xf.split(",")[0];
        return request.getRemoteAddr();
    }
}
