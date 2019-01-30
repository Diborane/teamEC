package com.internousdev.gerbera.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.gerbera.dto.CartInfoDTO;
import com.internousdev.gerbera.util.DBConnector;

public class CartInfoDAO {

	public List<CartInfoDTO> getCartInfo(String loginId){

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		// cart_info と product_info とをprosuct_idで結合させ、カート情報+商品詳細情報を取得する
		String sql = "SELECT ci.id,ci.user_id,ci.temp_user_id,ci.product_id,ci.product_count,ci.price,"
				+ "pi.product_name,pi.product_name_kana,pi.image_file_path,pi.image_file_name,pi.release_company,pi.release_date "
				+ "FROM cart_info ci "
				+ "LEFT JOIN product_info pi "
				+ "ON ci.product_id = pi.product_id "
				+ "WHERE ci.user_id =? OR ci.temp_user_id=? "
				+ "ORDER BY ci.update_date DESC";

		List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, loginId);
				ps.setString(2, loginId);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				CartInfoDTO cartInfoDto = new CartInfoDTO();
					cartInfoDto.setId(rs.getInt("id"));
					cartInfoDto.setUserId(rs.getString("user_id"));
					cartInfoDto.setTempUserId(rs.getString("temp_user_id"));
					cartInfoDto.setProductId(rs.getInt("product_id"));
					cartInfoDto.setProductCount(rs.getInt("product_count"));
					cartInfoDto.setPrice(rs.getInt("price"));
					cartInfoDto.setProductName(rs.getString("product_name"));
					cartInfoDto.setProductNameKana(rs.getString("product_name_kana"));
					cartInfoDto.setImageFilePath(rs.getString("image_file_path"));
					cartInfoDto.setImageFileName(rs.getString("image_file_name"));
					cartInfoDto.setReleaseCompany(rs.getString("release_company"));
					cartInfoDto.setReleaseDate(rs.getDate("release_date"));
				cartInfoDtoList.add(cartInfoDto);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return cartInfoDtoList;
	}

	public boolean isExistsCartInfo (String loginId, String productId) {
		// 引数として受け取ったloginIdのユーザーが、productIdの商品を購入した履歴が存在するかどうかを判別する
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "SELECT COUNT(id) AS COUNT FROM cart_info WHERE user_id = ? and product_id=?";

		boolean result = false;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, loginId);
				ps.setString(2, productId);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				if(rs.getInt("COUNT") > 0) {
					result = true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}


	public int delete(String loginId, String productId){
		// loginIdのユーザーのカート履歴の中から、productIdの履歴を削除する
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "DELETE FROM cart_info WHERE (user_id=? OR temp_user_id=?) AND product_id=?";
		int result = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,loginId);
				ps.setString(2,loginId);
				ps.setString(3,productId);
			result = ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}


	public int deleteAll(String loginId){
		// loginIdのカート情報を全て削除する
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "DELETE FROM cart_info WHERE user_id=?";
		int result = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,loginId);
			result = ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}


	public int regist(String loginId, String tempUserId,int productId, int productCount, int price){
		// カートに新規に商品を登録する
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "INSERT INTO cart_info(user_id,temp_user_id,product_id,product_count,price,regist_date,update_date) values(?,?,?,?,?,now(),now())";

		int result = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,loginId);
				ps.setString(2, tempUserId);
				ps.setInt(3,productId);
				ps.setInt(4,productCount);
				ps.setInt(5,price);
			result = ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}


	public int registUpdate(String loginId, String tempUserId, int productId, int productCount){
		// カートにすでに存在する商品の購入個数情報を更新する(数を加える)
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "UPDATE cart_info SET product_count=(product_count+?), update_date = now() WHERE (user_id=? OR temp_user_id=?) AND product_id=?";

		int result = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1,productCount);
				ps.setString(2,loginId);
				ps.setString(3,tempUserId);
				ps.setInt(4,productId);
			result = ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}



	public int getTotalPrice(String loginId){
		// 該当loginIdのカート内の総合計金額を求める
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "SELECT sum(price*product_count) as total_price FROM cart_info WHERE user_id=? GROUP BY user_id";
		int totalPrice = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,loginId);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				totalPrice = rs.getInt("total_price");
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return totalPrice;
	}


	public int linkToLoginId(String tempUserId, String loginId){
		// tempUserIdとloginIDとを関連付ける(temp_user_idをnullにし、loginIdを更新する)
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "UPDATE cart_info SET user_id=?, temp_user_id = null WHERE temp_user_id=?";
		int result = 0;

		try{
			PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,loginId);
				ps.setString(2,tempUserId);
			result = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}


	public int integrationCartInfo(String loginId){

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql1 = "SELECT * FROM cart_info WHERE user_id=? AND temp_user_id IS NULL";
		String sql2 = "UPDATE cart_info SET product_count=(product_count+?), update_date = now() WHERE (user_id=? AND temp_user_id IS NOT NULL) AND product_id=?";
		String sql3 = "DELETE FROM cart_info WHERE (user_id=? AND temp_user_id IS NULL) AND product_id=?";
		String sql4 = "UPDATE cart_info SET temp_user_id='none' WHERE (user_id=? AND temp_user_id IS NULL) AND product_id=?";
		int result = 1;

		try{
			PreparedStatement ps1 = con.prepareStatement(sql1);
				ps1.setString(1,loginId);
			ResultSet rs1 = ps1.executeQuery();

			PreparedStatement ps2 = con.prepareStatement(sql2);
			PreparedStatement ps3 = con.prepareStatement(sql3);
			PreparedStatement ps4 = con.prepareStatement(sql4);

			while(rs1.next()){
				ps2.setString(1,rs1.getString("product_count"));
				ps2.setString(2,loginId);
				ps2.setString(3,rs1.getString("product_id"));
				int i2 = ps2.executeUpdate();

				if(i2>0){
					ps3.setString(1,loginId);
					ps3.setString(2,rs1.getString("product_id"));
					result = ps3.executeUpdate();

				}else{
					ps4.setString(1,loginId);
					ps4.setString(2,rs1.getString("product_id"));
					result = ps4.executeUpdate();
				}

				if(result<=0){
					break;
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return result;
	}
}
