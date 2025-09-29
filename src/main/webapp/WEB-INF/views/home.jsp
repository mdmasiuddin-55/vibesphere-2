<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vibesphere - Home</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <div class="navbar">
        <div class="nav-brand">Vibesphere</div>
        <div class="nav-links">
            <a href="/">Home</a>
            <a href="/explore">Explore</a>
            <a href="/profile">Profile</a>
            <a href="/logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <!-- Create Post Form -->
        <div class="create-post">
            <form action="/posts/create" method="post" enctype="multipart/form-data">
                <textarea name="caption" placeholder="What's on your mind?" rows="3"></textarea>
                <input type="file" name="media" accept="image/*,video/*" required>
                <select name="mediaType" required>
                    <option value="image">Image</option>
                    <option value="video">Video</option>
                </select>
                <button type="submit">Post</button>
            </form>
        </div>

        <!-- Posts Feed -->
        <c:forEach var="post" items="${posts}">
            <div class="post">
                <div class="post-header">
                    <img src="${post.user.profilePicture != null ? post.user.profilePicture : '/images/default-avatar.png'}" 
                         alt="Profile" class="avatar">
                    <span class="username">${post.user.username}</span>
                </div>
                
                <div class="post-media">
                    <c:choose>
                        <c:when test="${post.mediaType == 'IMAGE'}">
                            <img src="${post.mediaUrl}" alt="Post image">
                        </c:when>
                        <c:when test="${post.mediaType == 'VIDEO'}">
                            <video controls>
                                <source src="${post.mediaUrl}" type="video/mp4">
                                Your browser does not support the video tag.
                            </video>
                        </c:when>
                    </c:choose>
                </div>
                
                <div class="post-actions">
                    <button class="like-btn ${post.likedByCurrentUser ? 'liked' : ''}" 
                            onclick="likePost(${post.id})">
                        ❤️ ${post.likeCount}
                    </button>
                    <button class="comment-btn" onclick="toggleComments(${post.id})">
                        💬 ${post.commentCount}
                    </button>
                </div>
                
                <div class="post-caption">
                    <strong>${post.user.username}</strong> ${post.caption}
                </div>
                
                <div class="post-comments" id="comments-${post.id}" style="display: none;">
                    <c:forEach var="comment" items="${post.comments}">
                        <div class="comment">
                            <strong>${comment.user.username}</strong> ${comment.commentText}
                        </div>
                    </c:forEach>
                    <form action="/posts/${post.id}/comment" method="post" class="comment-form">
                        <input type="text" name="commentText" placeholder="Add a comment..." required>
                        <button type="submit">Post</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

    <script src="/js/app.js"></script>
</body>
</html>