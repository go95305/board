package com.board.service;

import com.board.domain.entity.BoardEntity;
import com.board.domain.repository.BoardRepository;
import com.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;
import java.util.Optional;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;
    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        // BoardDto boardDTO = BoardDto.builder()
        //         .id(boardEntity.getId())
        //         .title(boardEntity.getTitle())
        //         .content(boardEntity.getContent())
        //         .writer(boardEntity.getWriter())
        //         .createdDate(boardEntity.getCreatedDate())
        //         .build();

        return BoardDto.from(boardEntity);
    }
    

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> getBoardlist(){
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        // DB에서 가져온 entity를 dto형태로 만들어준다.
        for ( BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(BoardDto.from(boardEntity));
        }

        return boardDtoList;
        // return boardRepository.findAll()
        // .stream()
        // .map(BoardDto::from)
        // .collect(Collectors.toList());

    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(BoardEntity.create(boardDto)).getId();
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword){
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boardEntities.isEmpty()){
            return boardDtoList;
        }

        for(BoardEntity boardEntity:boardEntities){
            boardDtoList.add(BoardDto.from(boardEntity));
        }

        return boardDtoList;
    }
}
