<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>로그인</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<main class="container">

    <section class="page-heading">
        <h1>로그인</h1>
        <p>아이디와 비밀번호를 입력해 주세요.</p>
    </section>

    <section class="board-card">

        <form action="${pageContext.request.contextPath}/login"
              method="post"
              class="write-form">

            <input type="hidden"  name="returnUrl" value="${returnUrl}">

            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text"
                       id="userId"
                       name="userId"
                       placeholder="아이디 입력"
                       required>
            </div>

            <div class="form-group">
                <label for="userPass">비밀번호</label>
                <input type="password"
                       id="userPass"
                       name="userPass"
                       placeholder="비밀번호 입력"
                       required>
            </div>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/"
                   class="home-btn">
                    ← 홈으로
                </a>

                <button type="submit" class="write-btn">
                    로그인
                </button>
            </div>

        </form>

    </section>

</main>

<script>
    const params = new URLSearchParams(window.location.search);

    if (params.has("error")) {
        alert("아이디 또는 비밀번호가 틀립니다.");
    }
</script>

</body>
</html>
