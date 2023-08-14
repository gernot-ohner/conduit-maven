package dev.ohner.conduit.controller;

import dev.ohner.conduit.api.TagsApi;
import dev.ohner.conduit.model.GetTags200Response;
import dev.ohner.conduit.service.TagService;
import dev.ohner.conduit.service.model.TagModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class TagsController implements TagsApi {

    final TagService tagService;

    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TagsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<GetTags200Response> getTags() {
        final List<TagModel> tags = tagService.getTags();
        return ResponseEntity.ok(new GetTags200Response(
          tags.stream().map(TagModel::tag).toList()
        ));
    }

}
