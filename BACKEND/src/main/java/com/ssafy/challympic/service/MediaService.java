package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Media;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public Media getMedia(int fileNo){
        Media media = mediaRepository.findByFileNo(fileNo);
        return media;
    }

    @Transactional
    public int saveMedia(Media media) {
        // 중복 확인
        mediaRepository.save(media);
        return media.getFile_no();
    }

    @Transactional
    public int update(int fileNo, Media media){
        Media orgMedia = mediaRepository.findByFileNo(fileNo);
        orgMedia.setFile_name(media.getFile_name());
        orgMedia.setFile_savedname(media.getFile_savedname());
        orgMedia.setFile_path(media.getFile_path());
        return orgMedia.getFile_no();
    }

    @Transactional
    public int delete(int fileNo){
        mediaRepository.deleteMedia(fileNo);
        return 1;
    }

}
