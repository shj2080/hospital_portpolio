package dao;

//DB 연결을 위해 관련 패키지 import
import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.reservation.ReservationBean;
import vo.reservation.ReservationListBean;

public class ReservationDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//싱글톤 패턴 : DAO 객체 하나만 생성되도록 처리
	
	//생성자
	private ReservationDAO() {}
	
	private static ReservationDAO reservationDAO;
	
	//객체가 없는 경우 생성
	public static ReservationDAO getInstance() {
		
		if(reservationDAO == null) { //DAO 객체가 없는 경우
			reservationDAO = new ReservationDAO(); //객체 생성
		}
		
		return reservationDAO; //객체 주소 반환
	}
	/**********************************************************/
	
	//커넥션 설정
	public void setConnection(Connection con) {
		this.con = con;
	}

	//예약진료목록 하나를 예약테이블에 추가
	public int insertReservation(ReservationBean reservationBean) {
		int insertResult = 0;

		String sql = "insert into reservation(speciality_code, doctor_code, id, reservation_date, phone, u_name)";
		sql += " values(?,?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);

			//?안에 들어갈 값 세팅
			pstmt.setInt(1, reservationBean.getSpeciality_code());
			pstmt.setInt(2, reservationBean.getDoctor_code());
			pstmt.setString(3, reservationBean.getId());
			pstmt.setString(4, reservationBean.getReservation_date());
			pstmt.setString(5, reservationBean.getPhone());
			pstmt.setString(6, reservationBean.getU_name());

			insertResult = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("[ReservationDAO] insertReservation 에러:"+ e);
		} finally { //사용 후 커넥션 해제
			close(pstmt);
		}

		return insertResult;
	}

	//예약된 본인의 진료내역 불러오는 DAO 메서드 - 이름으로 조회하는 메서드
	public ArrayList<ReservationListBean> myReservationNameSelectList(String userName) {
		ArrayList<ReservationListBean> reservationList = null;
		ReservationListBean myReservation = null;
		
		String sql = "select reservation_code , reservation_date, doctor_name, speciality_name,";
		sql += " r.doctor_code, r.speciality_code, r.id, m.name, treatment_status";
		sql += " from reservation r LEFT JOIN membertbl m ON r.id = m.id";
		sql += " LEFT JOIN speciality spec ON r.speciality_code = spec.speciality_code";
		sql += " LEFT JOIN doctor d ON r.doctor_code = d.doctor_code";
		sql += " WHERE r.u_name = ? order by reservation_date asc";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				reservationList = new ArrayList<ReservationListBean>();
				
				do {
					myReservation = new ReservationListBean(rs.getInt("reservation_code"),
							rs.getString("reservation_date"),
							rs.getString("doctor_name"),
							rs.getString("speciality_name"),
							rs.getInt("doctor_code"),
							rs.getInt("speciality_code"),
							rs.getString("id"),
							rs.getString("name"),
							rs.getString("treatment_status"));
					//ArrayList에 추가
					reservationList.add(myReservation);
				} while (rs.next());
				
			}
			
		} catch (Exception e) {
			System.out.println("[ReservationDAO] myReservationNameSelectList() 메서드 예외 발생 : " + e); //예외종류 + 예외메세지
		} finally {
			close(pstmt);
			close(rs);
		}
		return reservationList;
	}
	
	//예약된 본인의 진료내역 불러오는 DAO 메서드 - id로 조회
	public ArrayList<ReservationListBean> myReservationList(String userID) {
		ArrayList<ReservationListBean> reservationList = null;
		ReservationListBean myReservation = null;
		
		String sql = "select reservation_code , reservation_date, doctor_name, speciality_name,";
		sql += " r.doctor_code, r.speciality_code, r.id, m.name, treatment_status";
		sql += " from reservation r LEFT JOIN membertbl m ON r.id = m.id";
		sql += " LEFT JOIN speciality spec ON r.speciality_code = spec.speciality_code";
		sql += " LEFT JOIN doctor d ON r.doctor_code = d.doctor_code";
		sql += " WHERE r.id = ? order by reservation_date asc";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
					
			if(rs.next()){
				reservationList = new ArrayList<ReservationListBean>();
						
						do {
							myReservation = new ReservationListBean(rs.getInt("reservation_code"),
									rs.getString("reservation_date"),
									rs.getString("doctor_name"),
									rs.getString("speciality_name"),
									rs.getInt("doctor_code"),
									rs.getInt("speciality_code"),
									rs.getString("id"),
									rs.getString("name"),
									rs.getString("treatment_status"));
							//ArrayList에 추가
							reservationList.add(myReservation);
						} while (rs.next());
						
					}
					
				} catch (Exception e) {
					System.out.println("[ReservationDAO] myReservationList() 메서드 예외 발생 : " + e); //예외종류 + 예외메세지
				} finally {
					close(pstmt);
					close(rs);
				}
		return reservationList;
	}
	
	//진료예약 수정 DAO 메서드
	public int modifyReservationTreatment(ReservationBean reservationBean) {
		int updateResult = 0;
		
		String sql = "";
		
		if(reservationBean.getTreatment_status() != null) {
			sql = "update reservation";
			sql += " set speciality_code = ?, doctor_code = ?, reservation_date = ?, phone = ?, treatment_status = ?";
			
		}else {
			sql = "update reservation";
			sql += " set speciality_code = ?, doctor_code = ?, reservation_date = ?, phone = ?";
		}
		
		
		sql += " where id = ? AND reservation_code = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			//?안에 들어갈 값 세팅
			if(reservationBean.getTreatment_status() != null) {
				pstmt.setInt(1, reservationBean.getSpeciality_code());
				pstmt.setInt(2, reservationBean.getDoctor_code());
				pstmt.setString(3, reservationBean.getReservation_date());
				pstmt.setString(4, reservationBean.getPhone());
				pstmt.setString(5, reservationBean.getTreatment_status());
				pstmt.setString(6, reservationBean.getId());
				pstmt.setInt(7, reservationBean.getReservation_code());
			}else {
				pstmt.setInt(1, reservationBean.getSpeciality_code());
				pstmt.setInt(2, reservationBean.getDoctor_code());
				pstmt.setString(3, reservationBean.getReservation_date());
				pstmt.setString(4, reservationBean.getPhone());
				pstmt.setString(5, reservationBean.getId());
				pstmt.setInt(6, reservationBean.getReservation_code());
			}
			
			updateResult = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("[ReservationDAO] modifyReservationTreatment 에러:"+ e);
		} finally { //사용 후 커넥션 해제
			close(pstmt);
		}
		
		return updateResult;
	}
	//예약진료조회 메서드(한 예약항목만)
	public ReservationBean selectReservation(int reservation_code) {
		ReservationBean reservation = null;
		
		String sql = "select * from reservation where reservation_code = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			//? 들어갈 값 지정
			pstmt.setInt(1, reservation_code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				reservation = new ReservationBean(reservation_code,
						rs.getInt("speciality_code"), rs.getInt("doctor_code"),
						rs.getString("id"),
						rs.getString("reservation_date"),
						rs.getString("phone"),
						rs.getString("treatment_status"),
						rs.getString("u_name"));
			}
			
		} catch(Exception e) {
			System.out.println("[ReservationDAO] selectReservation 에러:"+ e);
		} finally { //사용 후 커넥션 해제
			close(pstmt);
			close(rs);
		}
		
		return reservation;
	}
	//특정 예약내역 삭제
	public int deleteReservationTreatment(int reservation_code, String userID) {
		int deleteResult = 0;
		
		String sql = "delete from reservation where reservation_code = ? AND id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			//?안에 들어갈 값 세팅
			pstmt.setInt(1, reservation_code);
			pstmt.setString(2, userID);
			
			deleteResult = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("[ReservationDAO] deleteReservationTreatment 에러:"+ e);
		} finally { //사용 후 커넥션 해제
			close(pstmt);
		}
		
		return deleteResult;
	}
	
	//예약진료 항목을 진료테이블에 insert하는 메서드 (admin화면)
	public int insertTreatment_Reservation(ReservationBean reservationInfo) {
		int insertResult = 0;
		
		String sql = "insert into treatment(speciality_code, doctor_code, id, treatment_date, phone, u_name)";
		sql += " values(?,?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);

			//?안에 들어갈 값 세팅
			pstmt.setInt(1, reservationInfo.getSpeciality_code());
			pstmt.setInt(2, reservationInfo.getDoctor_code());
			pstmt.setString(3, reservationInfo.getId());
			pstmt.setString(4, reservationInfo.getReservation_date());
			pstmt.setString(5, reservationInfo.getPhone());
			pstmt.setString(6, reservationInfo.getU_name());

			insertResult = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("[ReservationDAO] insertTreatment_Reservation 에러:"+ e);
		} finally { //사용 후 커넥션 해제
			close(pstmt);
		}
		
		return insertResult;
	}
	
	/**************************************************************************************/
	//예약된 전체 회원의 진료내역 불러오는 DAO 메서드
	public ArrayList<ReservationListBean> allReservationList() {
		ArrayList<ReservationListBean> reservationList = null;
		ReservationListBean myReservation = null;
		
		//전체 예약진료내역을 조회하는 쿼리문
		String sql = "select reservation_code , reservation_date, doctor_name, speciality_name,";
		sql += " r.doctor_code, r.speciality_code, r.id, m.name, treatment_status";
		sql += " from reservation r LEFT JOIN membertbl m ON r.id = m.id";
		sql += " LEFT JOIN speciality spec ON r.speciality_code = spec.speciality_code";
		sql += " LEFT JOIN doctor d ON r.doctor_code = d.doctor_code";
		sql += " order by reservation_date asc";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
					
			if(rs.next()){
				reservationList = new ArrayList<ReservationListBean>();
						
						do {
							myReservation = new ReservationListBean(rs.getInt("reservation_code"),
									rs.getString("reservation_date"),
									rs.getString("doctor_name"),
									rs.getString("speciality_name"),
									rs.getInt("doctor_code"),
									rs.getInt("speciality_code"),
									rs.getString("id"),
									rs.getString("name"),
									rs.getString("treatment_status"));
							//ArrayList에 추가
							reservationList.add(myReservation);
						} while (rs.next());
						
					}
					
				} catch (Exception e) {
					System.out.println("[ReservationDAO] allReservationList() 메서드 예외 발생 : " + e); //예외종류 + 예외메세지
				} finally {
					close(pstmt);
					close(rs);
				}
		
		return reservationList;
	}
	/**************************************************************************************/
}
