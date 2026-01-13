package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import me.akshaygupta.connectcard.model.Link;
import me.akshaygupta.connectcard.repository.LinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public int getNextPosition(String profileId) {
        return linkRepository.countByProfileId(profileId) + 1;
    }

    public void validatePosition(String profileId, int position) {
        if (linkRepository.existsByProfileIdAndPosition(profileId, position)) {
            throw new RuntimeException("Position already in use");
        }
    }

    public List<Link> getPublicLinks(String profileId) {
        return linkRepository.findByProfileIdAndActiveTrueOrderByPositionAsc(profileId);
    }

    public List<Link> getAllLinks(String profileId) {
        return linkRepository.findByProfileIdOrderByPositionAsc(profileId);
    }

    public void toggleLink(String linkId, boolean active) {
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        link.setActive(active);
        linkRepository.save(link);
    }

}
