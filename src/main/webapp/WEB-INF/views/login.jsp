<!DOCTYPE html>
<html>
<head>
    <title>Login - Vibesphere</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <style>
        .auth-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #fafafa;
        }
        .auth-form {
            background: white;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            padding: 40px;
            width: 350px;
            text-align: center;
        }
        .auth-form h2 {
            margin-bottom: 20px;
            color: #262626;
        }
        .auth-form input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #dbdbdb;
            border-radius: 4px;
        }
        .auth-form button {
            width: 100%;
            background-color: #0095f6;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        .auth-form p {
            margin-top: 20px;
            color: #262626;
        }
        .auth-form a {
            color: #0095f6;
            text-decoration: none;
        }
        .message {
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form">
            <h2>Vibesphere</h2>
            
            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>
            
            <form action="/login" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Log In</button>
            </form>
            <p>Don't have an account? <a href="/register">Sign up</a></p>
        </div>
    </div>
</body>
</html>