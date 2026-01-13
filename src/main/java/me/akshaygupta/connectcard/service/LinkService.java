package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.CreateLinkRequest;
import me.akshaygupta.connectcard.dto.UpdateLinkRequest;
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

    public Link createLink(String profileId, CreateLinkRequest request) {

        int position = getNextPosition(profileId);

        Link link = Link.builder()
                .profileId(profileId)
                .title(request.getTitle())
                .url(request.getUrl())
                .icon(request.getIcon())
                .position(position)
                .build();

        return linkRepository.save(link);
    }

    public List<Link> getMyLinks(String profileId) {
        return linkRepository.findByProfileIdOrderByPositionAsc(profileId);
    }

    public Link updateLink(String profileId, String linkId, UpdateLinkRequest req) {

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getProfileId().equals(profileId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (req.getTitle() != null)
            link.setTitle(req.getTitle());

        if (req.getUrl() != null)
            link.setUrl(req.getUrl());

        if (req.getIcon() != null)
            link.setIcon(req.getIcon());

        if (req.getActive() != null)
            link.setActive(req.getActive());

        if (req.getPosition() != null)
            link.setPosition(req.getPosition());

        return linkRepository.save(link);
    }

    public void deleteLink(String profileId, String linkId) {

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getProfileId().equals(profileId)) {
            throw new RuntimeException("Unauthorized");
        }

        linkRepository.delete(link);
    }

    public void reorderLinks(String profileId, List<String> linkIds) {

        for (int i = 0; i < linkIds.size(); i++) {
            Link link = linkRepository.findById(linkIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Link not found"));

            if (!link.getProfileId().equals(profileId)) {
                throw new RuntimeException("Unauthorized");
            }

            link.setPosition(i + 1);
            linkRepository.save(link);
        }
    }


}
