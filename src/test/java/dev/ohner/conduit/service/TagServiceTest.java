package dev.ohner.conduit.service;

import dev.ohner.conduit.repository.TagRepository;
import dev.ohner.conduit.repository.entity.TagEntity;
import dev.ohner.conduit.service.model.TagModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagService underTest;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getTags() {

        final var id1 = UUID.randomUUID();
        final var id2 = UUID.randomUUID();
        final var id3 = UUID.randomUUID();

        when(tagRepository.findAll()).thenReturn(
            List.of(
                new TagEntity(id1, "tag1"),
                new TagEntity(id2, "tag2"),
                new TagEntity(id3, "tag3")
            )
        );

        final var actual = underTest.getTags();

        assertThat(actual).containsExactlyInAnyOrder(
            new TagModel(id1, "tag1"),
            new TagModel(id2, "tag2"),
            new TagModel(id3, "tag3")
        );
    }

    @Test
    void createTag() {
        final var id = UUID.randomUUID();
        final var tag = "tag";
        final ArgumentCaptor<TagEntity> tagEntityArgumentCaptor = ArgumentCaptor.forClass(TagEntity.class);

        when(tagRepository.save(tagEntityArgumentCaptor.capture())).thenReturn(new TagEntity(id, tag));

        final var actual = underTest.createTag(new TagModel(null, tag));

        assertThat(actual).isEqualTo(new TagModel(id, tag));
        assertThat(tagEntityArgumentCaptor.getValue()).isEqualTo(new TagEntity(null, tag));
    }
}