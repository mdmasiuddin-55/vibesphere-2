function likePost(postId) {
    fetch(`/posts/${postId}/like`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => response.text())
    .then(result => {
        if (result === 'success') {
            location.reload();
        } else {
            alert('Error liking post');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error liking post');
    });
}

function toggleComments(postId) {
    const commentsDiv = document.getElementById(`comments-${postId}`);
    if (commentsDiv.style.display === 'none') {
        commentsDiv.style.display = 'block';
    } else {
        commentsDiv.style.display = 'none';
    }
}

// File upload preview
document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.querySelector('input[type="file"]');
    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                // You can add file preview functionality here
                console.log('File selected:', file.name);
            }
        });
    }
});