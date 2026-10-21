<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>회원가입 | My Board</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<%-- 에러시 메세지 --%>
<script>
    const errorMessage = "${errorMessage}";

    if (errorMessage) {
        alert(errorMessage);
    }
</script>

<body>

<main class="container">

    <section class="page-heading">
        <h1>회원가입</h1>
        <p>아이디와 비밀번호를 입력해 주세요.</p>
    </section>

    <section class="board-card">

        <form action="${pageContext.request.contextPath}/signup"
              method="post"
              class="write-form">

            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text"
                       id="userId"
                       name="userId"
                       maxlength="20"
                       placeholder="아이디 입력"
                       required>
            </div>

            <div class="form-group">
                <label for="userPass">비밀번호</label>
                <input type="password"
                       id="userPass"
                       name="userPass"
                       autocomplete="new-password"
                       placeholder="비밀번호 입력"
                       required>
            </div>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/"
                   class="home-btn">
                    ← 홈으로
                </a>

                <button type="submit" class="write-btn">
                    회원가입
                </button>
            </div>

        </form>

    </section>

</main>

</body>
</html>
