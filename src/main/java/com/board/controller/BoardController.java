package com.board.controller;


import com.board.dto.BoardDto;
import com.board.service.BoardService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

@RestController
@AllArgsConstructor
public class BoardController {

    @Resource(name="boardService")
    private BoardService boardService;
    
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword")String keyword,Model model){
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }
//dsf
    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }
    
    @GetMapping("/")
    public String list(Model model,@RequestParam(value="page",defaultValue = "1")Integer pageNum){
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        return "board/list.html";
    }

    // ResponseEntity를 사용해서 성공적으로 글을 올렸음을 리턴한다.
    @PostMapping("/post")
    public ResponseEntity<BoardDto> write(BoardDto boardDto) {
        BoardDto bDto = boardService.savePost(boardDto);
        return new ResponseEntity<BoardDto>(bDto,HttpStatus.OK);
    }
}
