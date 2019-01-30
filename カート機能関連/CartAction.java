package com.internousdev.gerbera.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.gerbera.dao.CartInfoDAO;
import com.internousdev.gerbera.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;


public class CartAction extends ActionSupport implements SessionAware{

	private Map<String,Object> session;

	public String execute(){

		if(!session.containsKey("mCategoryList")) {
			return "sessionTimeOut";
		}

		String result = ERROR;
		String loginId = null;

		// ログイン情報確認
		if(session.containsKey("loginId")){
			loginId = session.get("loginId").toString();
		}else if(!(session.containsKey("loginId"))&&session.containsKey("tempUserId")){
			loginId = session.get("tempUserId").toString();
		}

		CartInfoDAO cartInfoDAO = new CartInfoDAO();

		// カート情報の取得
		List<CartInfoDTO> cartInfoDTOList = cartInfoDAO.getCartInfo(loginId);


		Iterator<CartInfoDTO> iterator = cartInfoDTOList.iterator();
		if(!(iterator.hasNext())) {
			cartInfoDTOList = null;
		}
		// カート情報(cartInfoDtoList)をput
		session.put("cartInfoDtoList", cartInfoDTOList);

		// カート金額合計をput
		int totalPrice = cartInfoDAO.getTotalPrice(loginId);
		session.put("totalPrice", totalPrice);


		result = SUCCESS;
		return result;
	}


	@Override
	public void setSession(Map<String,Object> session){
		 this.session = session;
	}

}
