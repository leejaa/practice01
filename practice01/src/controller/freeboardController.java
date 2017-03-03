package controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Model.Dao;
 
@Controller
public class freeboardController {
	
	
	@Autowired
	Dao dao;

	@RequestMapping("/freeboard/step01")
	public ModelAndView pathHandler01(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		int cnt = dao.cntFreeboard();
		int size = cnt/5+1;
		
		List<HashMap> list = dao.readFreeboard(995+cnt, 999+cnt);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tile01");
		mav.addObject("title", "자유게시판");
		mav.addObject("main","/freeboard/main");
		
		mav.addObject("list", list);
		mav.addObject("cnt", cnt);
		mav.addObject("page", 1);
		mav.addObject("size", size);
		
		return mav;
	}
	
	@RequestMapping("/freeboard/step02")
	public ModelAndView pathHandler02(@RequestParam(name="page",defaultValue="1") int page){
		
		ModelAndView mav = new ModelAndView();
		
		int cnt = dao.cntFreeboard();
		
		int start = 999+cnt-5*page;
		int end = start+4;
		List<HashMap> list = dao.readFreeboard(start, end);
		int size = cnt/5+1;
		
		mav.setViewName("tile01");
		mav.addObject("title", "자유게시판");
		mav.addObject("main", "/freeboard/main");
		
		
		mav.addObject("list", list);
		mav.addObject("cnt", cnt);
		mav.addObject("page",page);
		mav.addObject("size", size);
		
		return mav;
		
		
	}
	
	
	@RequestMapping("/freeboard/step03")
	public ModelAndView pathHandler03(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tile01");
		mav.addObject("title", "글작성");
		mav.addObject("main", "/freeboard/write");
		
		return mav;
		
	}
	
	@RequestMapping("/freeboard/step04")
	public ModelAndView pathHandler04(@RequestParam(name="category",defaultValue="") String category
			,@RequestParam(name="title",defaultValue="") String title
			,@RequestParam(name="content",defaultValue="") String content,HttpSession session){
		
		ModelAndView mav = new ModelAndView("freeBoardMain");
		
		HashMap param = new HashMap<>();
		
		String writer = (String)session.getAttribute("id");
		String Category = category;
		String Title = title;
		String Content = content;
		
		param.put("WRITER", writer);
		param.put("CATEGORY", Category);
		param.put("TITLE", Title);
		param.put("CONTENT", Content);
		
		int n = dao.writeFreeboard(param);
		
		if(n==1){
			System.out.println("게시판 글 등록 성공");
		}
		
		int cnt = dao.cntFreeboard();
		int size = cnt/5+1;
		
		List<HashMap> list = dao.readFreeboard(995+cnt, 999+cnt);
		
		
		mav.setViewName("tile01");
		mav.addObject("title", "자유게시판");
		mav.addObject("main","/freeboard/main");
		
		mav.addObject("list", list);
		mav.addObject("cnt", cnt);
		mav.addObject("page", 1);
		mav.addObject("size", size);
		
		return mav;
		
	}
	
	@RequestMapping("/freeboard/step05")
	public ModelAndView pathHandle05(@RequestParam(name="num",defaultValue="") String num){
		ModelAndView mav = new ModelAndView();
		HashMap map = new HashMap<>();
		HashMap param = new HashMap<>();
		
		param.put("NUM", num);
		
		map = dao.readOne(param);
		
		
		mav.setViewName("tile01");
		mav.addObject("title", "상세내용");
		mav.addObject("main", "/freeboard/detail");
		mav.addObject("map",map);
		
		return mav;
	}
	@RequestMapping("/freeboard/step06")
	public ModelAndView pathHandle06(){
		ModelAndView mav = new ModelAndView();
		dao.like();
		
		int cnt = dao.cntFreeboard();
		int size = cnt/5+1;
		
		List<HashMap> list = dao.readFreeboard(995+cnt, 999+cnt);
		
		
		mav.setViewName("tile01");
		mav.addObject("title", "자유게시판");
		mav.addObject("main","/freeboard/main");
		
		mav.addObject("list", list);
		mav.addObject("cnt", cnt);
		mav.addObject("page", 1);
		mav.addObject("size", size);
		
		return mav;
	}
	
	@RequestMapping("/freeboard/step07")
	public ModelAndView pathHandle07(@RequestParam(name="num",defaultValue="")int num){
		ModelAndView mav = new ModelAndView();
		
		HashMap map = new HashMap<>();
		HashMap param = new HashMap<>();
		
		param.put("NUM", num);
		
		map = dao.readOne(param);
		
		
		mav.setViewName("tile01");
		mav.addObject("title", "글수정하기");
		mav.addObject("main", "/freeboard/change");
		mav.addObject("map",map);
		
		return mav;
	}
	
	@RequestMapping("/freeboard/step08")
	public ModelAndView pathHandle08(@RequestParam HashMap param, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		String writer = (String)session.getAttribute("id");
		param.put("writer", writer);
		
		int n = dao.updateFreeboard(param);
		
		if(n==1){
			int cnt = dao.cntFreeboard();
			int size = cnt/5+1;
			
			List<HashMap> list = dao.readFreeboard(995+cnt, 999+cnt);
			
			
			mav.setViewName("tile01");
			mav.addObject("title", "글수정 성공");
			mav.addObject("main","/freeboard/main");
			
			mav.addObject("list", list);
			mav.addObject("cnt", cnt);
			mav.addObject("page", 1);
			mav.addObject("size", size);
			
			return mav;
		}else{
			int cnt = dao.cntFreeboard();
			int size = cnt/5+1;
			
			List<HashMap> list = dao.readFreeboard(995+cnt, 999+cnt);
			
			
			mav.setViewName("tile01");
			mav.addObject("title", "글 수정 실패");
			mav.addObject("main","/freeboard/main");
			
			mav.addObject("list", list);
			mav.addObject("cnt", cnt);
			mav.addObject("page", 1);
			mav.addObject("size", size);
			
			return mav;
		}
		
	}
}
