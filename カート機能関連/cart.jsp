<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="./css/style.css">
	<link rel="stylesheet" href="./css/select.css">
	<title>カート</title>
	<script type="text/javascript">
		/* チェックボックスに一つでもチェックが付いていれば、checkFlgを立てる */
		function checkValue(){
			var checkList = document.getElementsByClassName("checkList");
			var checkFlag = 0;
			for (  var i = 0;  i < checkList.length;  i++  ) {
				if(checkFlag == 0){
					if(checkList[i].checked) {
						checkFlag = 1;
						break;
					}
				}
			}
			/* checkFlgが立っていれば削除ボタンを使用可能に、立っていなければ使用不可状態にする */
			var deleteBtn = document.getElementById('delete_btn');
			if (checkFlag == 1) {
				deleteBtn.disabled="";
				deleteBtn.classList.add("submit_btn");
				deleteBtn.classList.remove("inactive_btn");
			} else {
				deleteBtn.disabled="true";
				deleteBtn.classList.add("inactive_btn");
				deleteBtn.classList.remove("submit_btn");
			}
		}
	</script>
</head>
<body>

	<s:include value="header.jsp"/>

	<div id="contents">
		<h1>カート画面</h1>

		<!-- カート情報の取得に成功した場合、表示する -->
 		<s:if test="%{#session.cartInfoDtoList.size()>0}">

		<s:form>

			<table>

				<tr>
					<th>削除対象</th><th>商品名</th><th>商品名ふりがな</th><th>商品画像</th><th>値段</th><th>発売会社名</th><th>発売年月日</th><th>購入個数</th><th>合計金額</th>
				</tr>

				<s:iterator value="#session.cartInfoDtoList">
					<tr>
						<!-- onchange: checkboxの状態が変化したとき、checkValue関数(JavaScriptで定義した関数)を実行する -->
						<td><s:checkbox name="checkList"  class="checkList" value="checked" fieldValue="%{productId}"  onchange="checkValue()"/></td>
						<td><s:property value="%{productName}"/></td>
						<td><s:property value="%{productNameKana}"/></td>
						<td><img src="<s:property value='imageFilePath'/>/<s:property value='imageFileName'/>"/></td>
						<td><s:property value="%{price}"/>円</td>
						<td><s:property value="%{releaseCompany}"/></td>
						<td><s:property value="%{releaseDate}"/></td>
						<td><s:property value="%{productCount}"/>個</td>
						<td><s:property value="%{price*productCount}"/>円</td>
					</tr>
				</s:iterator>

			</table>

			<h2>カート合計金額：<s:property value="%{#session.totalPrice}"/>円</h2>

					<s:submit value="決済" onClick="this.form.action='SettlementConfirmAction';" theme="simple" class="submit_btn"/>
					<s:submit value="削除" onClick="this.form.action='DeleteCartAction';" theme="simple" disabled="true" id="delete_btn" class="inactive_btn"/>

		</s:form>

		</s:if>
		<s:else>
			<div class="info">
				カート情報がありません。
			</div>
		</s:else>
	</div>

	<div id="footer">
		<s:include value="footer.jsp"/>
	</div>

</body>
</html>