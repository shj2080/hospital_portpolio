package action.reservation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.reservation.ReservationCancelService;
import vo.ActionForward;

public class ReservationCancelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
        String viewId = (String)session.getAttribute("userID");
        String cancelID = request.getParameter("cancel_id");

        //현재 로그인된 상태가 아니면
	  	if(viewId == null) {
  			response.setContentType("text/html;charset=utf-8");
  			PrintWriter out = response.getWriter();
  			out.println("<script>");
  			out.println("alert('로그인이 필요한 서비스입니다.');");
  			out.println("location.href='login.do';");
  			out.println("</script>");
  		//로그인 상태라면
	  	}else {
	  		//예약코드 넘겨받음
	  		int reservation_code = Integer.parseInt(request.getParameter("reservation_code"));
	  		
	  		//서비스 객체 생성
	  		ReservationCancelService reservationCancelService = new ReservationCancelService();
	  		
	  		int cancelResult = reservationCancelService.deleteReservation(reservation_code, cancelID);
	  		
	  		if(cancelResult > 0) {
	  			response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('선택한 진료예약을 취소했습니다.');");
				out.println("location.replace('reservationCheckList.ad')");
				out.println("</script>");
				
	  			//forward = new ActionForward("reservationMemberSearch.ad?u_id=" + cancelID, true);
	  		}else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('진료예약 취소를 실패했습니다. 잠시 후 다시 시도해 주세요.');");
				out.println("history.back();");
				out.println("</script>");
	  		}
	  	}
		
		
		return forward;
	}

}
