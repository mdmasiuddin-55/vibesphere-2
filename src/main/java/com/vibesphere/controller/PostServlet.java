package com.vibesphere.controllers;

import com.vibesphere.dao.PostDAO;
import com.vibesphere.models.Post;
import com.vibesphere.utils.S3Util;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
    private PostDAO postDAO = new PostDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a multipart request");
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 10); // 10MB
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 10); // 10MB
        upload.setSizeMax(1024 * 1024 * 10); // 10MB

        try {
            List<FileItem> items = upload.parseRequest(request);
            String caption = null;
            File file = null;
            String fileName = null;

            for (FileItem item : items) {
                if (item.isFormField()) {
                    if ("caption".equals(item.getFieldName())) {
                        caption = item.getString();
                    }
                } else {
                    fileName = new File(item.getName()).getName();
                    file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
                    item.write(file);
                }
            }

            if (file != null) {
                String mediaUrl = S3Util.uploadFile(fileName, file);
                Post post = new Post();
                post.setUserId((Integer) request.getSession().getAttribute("userId")); // Assuming user is logged in and userId is stored in session
                post.setMediaUrl(mediaUrl);
                post.setCaption(caption);

                if (postDAO.createPost(post)) {
                    response.sendRedirect("home.jsp");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create post");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Upload failed");
        }
    }
}