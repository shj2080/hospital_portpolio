package action.mypage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.MemberInfoModifyService;
import vo.ActionForward;
import vo.Member;

import java.io.PrintWriter;

public class MemberInfoModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String name = request.getParameter("name");
		
		//String id_num = request.getParameter("id_num");
		String front_id_num = request.getParameter("front_id_num");
		String back_id_num = request.getParameter("back_id_num");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String address3 = request.getParameter("address3");
		String postcode = request.getParameter("postcode");
		String phone = request.getParameter("phone");
		
		//앞자리 주민번호와 뒷자리 주민번호 조합
		String id_num = front_id_num + "-" + back_id_num;
		
		Member member = null;
		
		//변경할 비밀번호를 입력안했을 경우(비밀번호 변경을 원치 않을 경우)
		if(password == null || password.equals("")) {
			member = new Member(name, id_num, id, address1, address2, address3, postcode, phone);
		}else {
			member = new Member(name, id_num, id, password, address1, address2, address3, postcode, phone);
		}
		
		MemberInfoModifyService memberInfoModifyService = new MemberInfoModifyService();

		boolean isMemberModify = memberInfoModifyService.updateMemberInfo(member);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if(isMemberModify) {
			out.println("<script>");
			out.println("alert('회원 수정을 완료했습니다.');");
			out.println("location.replace('mypage.do');");
			out.println("</script>");
			
			HttpSession session = request.getSession();
			
			//수정된 회원정보로 세션값 갱신
			session.setAttribute("userName",name);
			session.setAttribute("userPhone",phone);
			
			
			//자바스크립트로 메세지창 띄우려면 아래 코드 주석
			//forward = new ActionForward("index.jsp", true);
		}else {
			out.println("<script>");
			out.println("alert('회원 수정을 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}

		return forward;
	}

}
