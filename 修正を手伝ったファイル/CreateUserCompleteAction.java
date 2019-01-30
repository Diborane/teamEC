package com.internousdev.gerbera.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.gerbera.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class CreateUserCompleteAction extends ActionSupport implements SessionAware {

	private Map<String, Object> session;

	private String familyName;
	private String firstName;
	private String familyNameKana;
	private String firstNameKana;
	private String sex;
	private String email;
	private String createLoginId;
	private String password;

	public String execute() {

		if (!session.containsKey("mCategoryList")) {
			return "sessionTimeOut";
		}
		String result = ERROR;

		UserInfoDAO UserInfoDao = new UserInfoDAO();

		String familyName = session.get("familyName").toString();
		String firstName = session.get("firstName").toString();
		String familyNameKana = session.get("familyNameKana").toString();
		String firstNameKana = session.get("firstNameKana").toString();

		String sex = session.get("sex").toString();
		if(sex.equals("男性")){
			sex = "0";
		}else if(sex.equals("女性")){
			sex = "1";
		}

		String email = session.get("email").toString();
		String createLoginId = session.get("createLoginId").toString();
		String password = session.get("password").toString();

		int count = UserInfoDao.createUser(familyName, firstName, familyNameKana, firstNameKana, sex, email, createLoginId,
				password);
		if (count > 0) {
			result = SUCCESS;
			session.remove("familyName");
			session.remove("firstName");
			session.remove("familyNameKana");
			session.remove("firstNameKana");
			session.remove("sex");
			session.remove("email");
			session.put("createUserCompleteFlg", "");
		}
		return result;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFamilyNameKana() {
		return familyNameKana;
	}

	public String getFirstNameKana() {
		return firstNameKana;
	}

	public String getSex() {
		return sex;
	}

	public String getEmail() {
		return email;
	}

	public String getCreateLoginId() {
		return createLoginId;
	}

	public String getPassword() {
		return password;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}

	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreateLoginId(String createLoginId) {
		this.createLoginId = createLoginId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
