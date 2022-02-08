package com.ssafy.challympic.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.challympic.domain.Media;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    /**
         input
         => input/profile/20220207/140207/파일.png
         => input/media/20220207/140207/파일.mp4

         output
         => output/profile/20220207/140207/saved파일명.png
         => output/media/20220207/140207/saved파일명.png
         => output/media/20220207/140207/video/saved파일명.m3u8
         => output/media/20220207/140207/thumbnail/saved파일명.png
     * */
    public Media upload(MultipartFile multipartFile, String type, String category) throws Exception{
        // 2. 프론트로부터 파일을 입력 받음

        // 2-1. MultipartFile을 File의 형태로 변환하여 로컬에 저장 완료
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러, 로컬에 파일 업로드
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));  // 저장할 원본 파일

        // 시간 객체
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
        String timeStr = new SimpleDateFormat("HHmmss").format(date);

        String savedFileName = new MD5Generator(uploadFile.getName()).toString();

        // 3. 원본 파일 AWS S3의 input 폴더에 업로드
        String inputPath = "input" + "/" + category + "/" + dateStr + "/" + timeStr;
        upload(uploadFile, inputPath, uploadFile.getName());    // 원본 파일 저장

        String outputPath = "output" + "/" + category + "/" + dateStr + "/" + timeStr;
        String extension = uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1);

        // 타입에 따른 처리
        if(type.equals("image")){
            // Output 경로에 저장
            upload(uploadFile, outputPath, savedFileName+"."+extension);

            // 원본 파일 제거
            removeFile();

            return new Media(uploadFile.getName(), savedFileName+"."+extension, outputPath);

        } else {
            // 해당 파일을 변환해서 저장
            if(hlsConverter(uploadFile.getName())){
                // HLS 파일로 변환한 파일들이 저장된 경로
                String convertPath = System.getProperty("user.dir") + "\\files\\" + dateStr + "\\convert";

                // 썸네일 업로드
                File thumbnail = new File(convertPath + "/" +savedFileName + ".png");
                upload(thumbnail, outputPath+"/thumbnail", savedFileName+".png");

                // 동영상 파일 업로드
                convertPath += "\\" + savedFileName;
                uploadHLSFolder(convertPath, outputPath+"/video");

                // 5. 원본 파일 제거
                removeFile();

                return new Media(uploadFile.getName(), savedFileName, outputPath);
            }
        }

        return null;
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String path, String fileName) {
        // path 경로에 fileName으로 저장
        String uploadImageUrl = putS3(uploadFile, path + "/" + fileName); // s3로 업로드
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 파일 지우기
    private void removeFile() {
        String path = System.getProperty("user.dir") + "\\files";
        if (deleteFolder(path)) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    public boolean uploadHLSFolder(String path, String dirPath){
        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

                // 폴더 내부 폴더, 파일 삭제
                for (int i = 0; i < folder_list.length; i++) {
                    System.out.println(folder_list[i].getName());
                    if(folder_list[i].isFile()) {
                        upload(folder_list[i], dirPath, folder_list[i].getName());
                        log.info("파일이 등록되었습니다.");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    public boolean deleteFolder(String path) {

        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                // 폴더 내부 폴더, 파일 삭제
                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                        log.info("파일이 삭제되었습니다.");
                    }else {
                        deleteFolder(folder_list[i].getPath()); //재귀함수호출
                        log.info("폴더가 삭제되었습니다.");
                    }
                    folder_list[i].delete();
                }
                folder.delete(); // 루트 폴더 삭제
            }
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws Exception {

        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);

        String FILE_PATH = System.getProperty("user.dir") + "\\files";
        makeDirectory(FILE_PATH);

        FILE_PATH += "\\" + dateStr;
        makeDirectory(FILE_PATH);

        FILE_PATH += "\\origin";
        makeDirectory(FILE_PATH);

        // 로컬에 저장된 파일을 읽어옴(실제 파일명)
        File convertFile = new File(FILE_PATH + "\\" + file.getOriginalFilename());

        if(!convertFile.exists()) {
            if(convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } else{
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public boolean hlsConverter(String fileName){
        try{
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");

            final String ROOT_DIR = System.getProperty("user.dir") + "\\files\\" + date.format(new Date());
            final String UPLOAD_DIR = ROOT_DIR + "\\convert";

            if(!new File(UPLOAD_DIR).exists()) {
                new File(UPLOAD_DIR).mkdir();
            }

            final String FFMPEG_PATH = "convert_exe\\bin";
            final String FFMPEG = "ffmpeg";
            final String FFPROBE = "ffprobe";

            final String FILEPATH = ROOT_DIR + "\\origin\\" + fileName;    // 저장된 파일 경로
//            final String ONLY_FILENAME = fileName.substring(0, fileName.lastIndexOf("."));  // 확장자를 제가함 파일명
            final String SAVED_FILENAME = new MD5Generator(fileName).toString();
            String TS_PATH = UPLOAD_DIR + "\\" + SAVED_FILENAME;   // ex) {UPLOAD_DIR}/sample
            File tsPath = new File(TS_PATH); // 파일명으로 폴더 생성

            if(!tsPath.exists()) {
                tsPath.mkdir();
            }

            FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH + "\\" + FFMPEG);
            FFprobe ffprobe = new FFprobe(FFMPEG_PATH + "\\" + FFPROBE);
            FFmpegProbeResult probeResult = ffprobe.probe(FILEPATH);

            log.debug("========== VideoFileUtils.getMediaInfo() ==========");
            log.debug("filename : {}", probeResult.getFormat().filename);
            log.debug("format_name : {}", probeResult.getFormat().format_name);
            log.debug("format_long_name : {}", probeResult.getFormat().format_long_name);
            log.debug("tags : {}", probeResult.getFormat().tags.toString());
            log.debug("duration : {} second", probeResult.getFormat().duration);
            log.debug("size : {} byte", probeResult.getFormat().size);
            log.debug("width : {} px", probeResult.getStreams().get(0).width);
            log.debug("height : {} px", probeResult.getStreams().get(0).height);
            log.debug("===================================================");

            // TS 파일 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    .overrideOutputFiles(true) // 오버라이드 여부
                    .setInput(FILEPATH) // 동영상파일
                    .addOutput(TS_PATH + "\\" + SAVED_FILENAME + ".m3u8")
                    .addExtraArgs("-profile:v", "baseline") //
                    .addExtraArgs("-level", "3.0") //
                    .addExtraArgs("-start_number", "0") //
                    .addExtraArgs("-hls_time", "10") //
                    .addExtraArgs("-hls_list_size", "0") //
                    .addExtraArgs("-f", "hls") //
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();

            // 이미지 파일 생성
            FFmpegBuilder builderThumbNail = new FFmpegBuilder()
                    .overrideOutputFiles(true) // 오버라이드 여부
                    .setInput(FILEPATH) // 동영상파일
                    .addExtraArgs("-ss", "00:00:03") // 썸네일 추출 시작점
                    .addOutput(UPLOAD_DIR + "\\" + SAVED_FILENAME + ".png") // 썸네일 경로
                    .setFrames(1) // 프레임 수
                    .done();

            FFmpegExecutor executorThumbNail = new FFmpegExecutor(ffmpeg, ffprobe);
            executorThumbNail.createJob(builderThumbNail).run();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void makeDirectory(String path){
        if(!new File(path).exists()){
            try {
                new File(path).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

}
