package com.example.springboot_startspringio.mapper;

import com.example.springboot_startspringio.domain.Anime;
import com.example.springboot_startspringio.requests.AnimePostRequestBody;
import com.example.springboot_startspringio.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
