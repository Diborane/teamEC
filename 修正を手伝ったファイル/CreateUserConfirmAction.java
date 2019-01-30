package com.internousdev.gerbera.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.gerbera.dao.UserInfoDAO;
import com.internousdev.gerbera.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class CreateUserConfirmAction extends ActionSupport implements SessionAware {
	private String familyName;
	private String firstName;
	private String familyNameKana;
	private String firstNameKana;
	private String sex;
	private String email;
	private String createLoginId;
	private String password;

	private String isExistErrorMessage = "";

	private List<String> familyNameErrorMessageList = new ArrayList<String>();
	private List<String> firstNameErrorMessageList = new ArrayList<String>();
	private List<String> familyNameKanaErrorMessageList = new ArrayList<String>();
	private List<String> firstNameKanaErrorMessageList = new ArrayList<String>();
	private List<String> emailErrorMessageList = new ArrayList<String>();
	private List<String> loginIdErrorMessageList = new ArrayList<String>();
	private List<String> passwordErrorMessageList = new ArrayList<String>();

	private List<String> sexList = new ArrayList<String>();
	private Map<String, Object> session;

	public String execute() {

		if (!session.containsKey("mCategoryList")) {
			return "sessionTimeOut";
		}

		String result = ERROR;

		InputChecker inputChecker = new InputChecker();

		familyNameErrorMessageList = inputChecker.doCheck("姓", familyName, 1, 16, true, true, true, false, false, false,false, false, false);
		firstNameErrorMessageList = inputChecker.doCheck("名", firstName, 1, 16, true, true, true, false, false, false,false, false, false);
		familyNameKanaErrorMessageList = inputChecker.doCheck("姓ふりがな", familyNameKana, 1, 16, false, false, true, false, false, false, false, false, false);
		firstNameKanaErrorMessageList = inputChecker.doCheck("名ふりがな", firstNameKana, 1, 16, false, false, true, false, false, false, false, false, false);
		emailErrorMessageList = inputChecker.doCheck("メールアドレス", email, 10, 32, true, false, false, true, true, false, false, false, false);
		loginIdErrorMessageList = inputChecker.doCheck("ユーザー"
				+ "ID", createLoginId, 1, 8, true, false, false, true, false, false, false, false, false);
		passwordErrorMessageList = inputChecker.doCheck("パスワード", password, 1, 16, true, false, false, true, false, false, false, false, false);

		if(sex==null){
			sex = "";
		}

		if(!(sex.equals("男性")||sex.equals("女性"))){
			sex="男性";
		}

		if (familyNameErrorMessageList.size() == 0 && firstNameErrorMessageList.size() == 0
				&& familyNameKanaErrorMessageList.size() == 0 && firstNameKanaErrorMessageList.size() == 0
				&& emailErrorMessageList.size() == 0 && loginIdErrorMessageList.size() == 0
				&& passwordErrorMessageList.size() == 0) {

			UserInfoDAO userInfoDAO = new UserInfoDAO();
			if (userInfoDAO.isExistUserInfo(createLoginId)) {
				isExistErrorMessage = "使用できないユーザーIDです。";
				result = ERROR;
			} else {
				result = SUCCESS;
				session.put("familyName",familyName);
				session.put("firstName",firstName);
				session.put("familyNameKana",familyNameKana);
				session.put("firstNameKana",firstNameKana);
				session.put("sex",sex);
				session.put("email",email);
				session.put("createLoginId",createLoginId);
				session.put("password",password);
			}
		}

		return result;
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


	public String getIsExistErrorMessage() {
		return isExistErrorMessage;
	}


	public List<String> getFamilyNameErrorMessageList() {
		return familyNameErrorMessageList;
	}

	public void setFamilyNameErrorMessageList(List<String> familyNameErrorMessageList) {
		this.familyNameErrorMessageList = familyNameErrorMessageList;
	}

	public List<String> getFirstNameErrorMessageList() {
		return firstNameErrorMessageList;
	}

	public void setFirstNameErrorMessageList(List<String> firstNameErrorMessageList) {
		this.firstNameErrorMessageList = firstNameErrorMessageList;
	}

	public List<String> getFamilyNameKanaErrorMessageList() {
		return familyNameKanaErrorMessageList;
	}

	public void setFamilyNameKanaErrorMessageList(List<String> familyNameKanaErrorMessageList) {
		this.familyNameKanaErrorMessageList = familyNameKanaErrorMessageList;
	}

	public List<String> getFirstNameKanaErrorMessageList() {
		return firstNameKanaErrorMessageList;
	}

	public void setFirstNameKanaErrorMessageList(List<String> firstNameKanaErrorMessageList) {
		this.firstNameKanaErrorMessageList = firstNameKanaErrorMessageList;
	}

	public List<String> getEmailErrorMessageList() {
		return emailErrorMessageList;
	}

	public void setEmailErrorMessageList(List<String> emailErrorMessageList) {
		this.emailErrorMessageList = emailErrorMessageList;
	}

	public List<String> getLoginIdErrorMessageList() {
		return loginIdErrorMessageList;
	}

	public void setLoginIdErrorMessageList(List<String> loginIdErrorMessageList) {
		this.loginIdErrorMessageList = loginIdErrorMessageList;
	}

	public List<String> getPasswordErrorMessageList() {
		return passwordErrorMessageList;
	}

	public void setPasswordErrorMessageList(List<String> passwordErrorMessageList) {
		this.passwordErrorMessageList = passwordErrorMessageList;
	}

	public List<String> getSexList() {
		return sexList;
	}

	public void setSexList(List<String> sexList) {
		this.sexList = sexList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
