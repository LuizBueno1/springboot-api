package com.example.springboot_startspringio.util;

import com.example.springboot_startspringio.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.builder()
            .name(AnimeCreator.createValidUpdatedAnime().getName())
            .id(AnimeCreator.createValidUpdatedAnime().getId())
            .build();
    }
}
