package action;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.LoginService;
import vo.ActionForward;
import vo.Member;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		//로그인 서비스 객체 생성
		LoginService loginService = new LoginService();
		
		//유저 정보를 받아올 객체
		Member userInfo = null;
		
		//------아이디와 비밀번호를 가져옴------//
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		//----아이디 저장 체크 여부 확인(쿠키객체 생성)------
		String remember = request.getParameter("remember");
		//---------------------------------------
		
		//로그인 처리 결과를 리턴 (id와 password로 체크)
		Member userLoginInfo = new Member(id, password);
		boolean loginResult = loginService.getLoginResult(userLoginInfo);
		
		//로그인 후 이동 위치 (성공 시 지정 페이지로 이동)
		//세션과 쿠키에 id를 "userID"로 저장
		if(loginResult) {
			HttpSession session = request.getSession();
			Cookie cookie = new Cookie("userID", id);
			cookie.setMaxAge(24*60*60); //쿠키 생존시간 24시간으로 설정
			
			userInfo = loginService.getUserInfo(id);
			
			//세션에 저장
			session.setAttribute("userID", id); //ID
			session.setAttribute("userName", userInfo.getName()); //이름
			
			if(remember != null) { //아이디 저장 체크 시
				response.addCookie(cookie); //response객체에 추가하여 클라이언트로 전송
			}else { //아이디 저장 체크 안한 경우
				cookie.setMaxAge(0); //쿠키 유효시간 0으로 설정하여 즉시 소멸되도록 함
				response.addCookie(cookie); //response객체에 추가하여 클라이언트로 전송
			}
			
			//세션 유지시간 변경(기본 30분)
			session.setMaxInactiveInterval(1*60*60); //세션 유지시간을 3600초 = 1시간으로 변경
			
			forward = new ActionForward("index.jsp", true);
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('아이디나 비밀번호가 일치하지 않습니다.')");
			out.println("location.href = 'login.do'");
			out.println("</script>");
		}
		
		return forward;
	}

}