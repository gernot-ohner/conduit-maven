package dev.ohner.conduit.service;

import dev.ohner.conduit.repository.TagRepository;
import dev.ohner.conduit.service.model.TagModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagModel> getTags() {
        return tagRepository.findAll()
            .stream()
            .map(TagModel::fromEntity)
            .toList();
    }

    public TagModel createTag(TagModel tag) {
        return TagModel.fromEntity(tagRepository.save(tag.toEntity()));
    }
}
