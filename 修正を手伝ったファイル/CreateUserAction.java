package com.internousdev.gerbera.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class CreateUserAction extends ActionSupport implements SessionAware {

	private static final String MALE = "男性";
	private static final String FEMALE = "女性";
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

		session.remove("familyName");
		session.remove("firstName");
		session.remove("familyNameKana");
		session.remove("firstNameKana");
		session.remove("sex");
		session.remove("email");
		session.remove("createLoginId");
		session.remove("password");

		String result = ERROR;

		if(sex==null){
			sex = MALE;
		}else if(sex.equals("男性")){
			sex = MALE;
		}else{
			sex = FEMALE;
		}

		List<String>  sexList = new ArrayList<String>();
		sexList.add(MALE);
		sexList.add(FEMALE);
		session.put("sexList", sexList);

		result = SUCCESS;
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


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getFamilyNameKana() {
		return familyNameKana;
	}


	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}


	public String getFirstNameKana() {
		return firstNameKana;
	}


	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCreateLoginId() {
		return createLoginId;
	}


	public void setCreateLoginId(String createLoginId) {
		this.createLoginId = createLoginId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
