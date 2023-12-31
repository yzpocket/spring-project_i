package com.yzpocket.iland.entity;

import com.yzpocket.iland.dto.VideoUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "videos")
public class Video extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @Column
    private String videoTitle;

    @Column
    private String videoWriter;

    @Column
    private String videoContents;

    @Column
    @Enumerated(value = EnumType.STRING)
    private VideoTypeEnum videoType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    List<File> fileList = new ArrayList<>();

    public Video(String title, VideoTypeEnum type, String writer, String contents, Board board) {
        this.videoTitle = title;
        this.videoType = type;
        this.videoWriter = writer;
        this.videoContents = contents;
        this.board = board;
    }

    public void update(VideoUpdateRequestDto requestDto) {
        this.videoTitle = requestDto.getVideoTitle();
        this.videoType = requestDto.getVideoType();
        this.videoWriter = requestDto.getVideoWriter();
        this.videoContents = requestDto.getVideoContents();
    }

    public void addFile(File fileEntity) {
        fileList.add(fileEntity);
        fileEntity.setVideo(this);
    }
}
