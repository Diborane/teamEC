package com.internousdev.gerbera.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.gerbera.dao.CartInfoDAO;
import com.internousdev.gerbera.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware{

	private Map<String,Object> session;

	private String productCount;

	public String execute(){

		if(!session.containsKey("mCategoryList")) {
			return "sessionTimeOut";
		}

		String result = ERROR;
		String loginId = null;
		String tempUserId = null;

		try{
			Integer.parseInt(productCount);
		}catch(NumberFormatException e){
			return ERROR;
		}

		if(!(Integer.parseInt(productCount)>0 && Integer.parseInt(productCount)<=5)){
			return ERROR;
		}

		// ログイン情報確認
		if(session.containsKey("loginId")){
			loginId = session.get("loginId").toString();
			tempUserId = session.get("tempUserId").toString();
		}else{
			loginId = session.get("tempUserId").toString();
			tempUserId = session.get("tempUserId").toString();
		}


		// 以下1の処理をする為のDAOを取得する
		CartInfoDAO cartInfoDAO = new CartInfoDAO();

		// 1. カートに商品を新規追加or情報を更新する
		String productId=session.get("productId").toString();
		String price=session.get("price").toString();
		if(cartInfoDAO.isExistsCartInfo(loginId, productId)){
			int count = cartInfoDAO.registUpdate(loginId, tempUserId, Integer.parseInt(productId), Integer.parseInt(productCount));
			if(count>0){
				result = SUCCESS;
			}
		}else{
			int count = cartInfoDAO.regist(loginId, tempUserId, Integer.parseInt(productId), Integer.parseInt(productCount), Integer.parseInt(price));
			if(count>0){
				result = SUCCESS;
			}
		}


		// 更新後のカート履歴を再取得, put
		List<CartInfoDTO> cartInfoDTOList = cartInfoDAO.getCartInfo(loginId);
		session.put("cartInfoDtoList", cartInfoDTOList);

		// カート合計金額の取得, put
		int totalPrice = cartInfoDAO.getTotalPrice(loginId);
		session.put("totalPrice", totalPrice);


		return result;
	}

	public void setProductCount(String productCount){
		this.productCount = productCount;
	}

	@Override
	public void setSession(Map<String,Object> session){
		 this.session = session;
	}

}
