package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager extends HotelDAO {	
	public UserDTO getLoginUser(String email, String password) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT  Email, Password "
				   + "FROM    Guest           "
				   + "WHERE   Email    = ?    "
				   + "AND     Password = ?    ";
		UserDTO user = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				user = new UserDTO();
				user.setEmail(rset.getString(1));
				user.setPassword(rset.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}
		
		return user;
	}
	
	public HotelDTO getLoginHotel(String country, String number) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT c.Country, h.PhoneNumber "
				   + "FROM Hotel h, HotelCountry c "
				   + "WHERE h.Country = c.Country "
				   + "AND h.Country = ? "
				   + "AND h.PhoneNumber = ?";
		HotelDTO hotel = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, country);
			pstmt.setString(2, number);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				hotel = new HotelDTO();
				hotel.setCountry(rset.getString(1));
				hotel.setNumber(rset.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}
		
		return hotel;
	}
}
