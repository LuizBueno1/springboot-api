package com.example.springboot_startspringio.service;

import com.example.springboot_startspringio.domain.Anime;
import com.example.springboot_startspringio.exception.BadRequestException;
import com.example.springboot_startspringio.mapper.AnimeMapper;
import com.example.springboot_startspringio.repository.AnimeRepository;
import com.example.springboot_startspringio.requests.AnimePostRequestBody;
import com.example.springboot_startspringio.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService{

    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }
    
    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePutRequestBody));
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

}
