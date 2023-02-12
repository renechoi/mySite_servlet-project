package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.dao.ReadCondition;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;
import com.javaex.vo.FileVo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BoardFileDownloadController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {

        FileVo file = getFileVo(boardManager, request);

        try {
            proceedDownload(response, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private static void proceedDownload(HttpServletResponse response, FileVo file) throws IOException {
        String fileName = new String(file.getFileName().getBytes("UTF-8"));
        InputStream fileContent = file.getFileContent();
        System.out.println("fileName = " + fileName);

        ServletOutputStream outputStream = response.getOutputStream();

//        response.setContentType("application/octet-stream");
//        response.setContentType("text/html; charset=utf-8");
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        byte[] bytes = new byte[1024 * 8];
        while (true){
            int count = fileContent.read(bytes);
            if(count ==-1){
                break;
            }
            outputStream.write(bytes,0,count);
        }
        fileContent.close();
        outputStream.close();
    }

    private static FileVo getFileVo(BoardManager boardManager, HttpServletRequest request) {
        BoardVo boardVo = new BoardVo(Integer.parseInt(request.getParameter("no")));
        DaoResult daoResult = boardManager.readBy(new ReadCondition("each", 0), boardVo);
        BoardVo boardVo1 = (BoardVo) daoResult.getResultValue().get("boardVo");
        FileVo file = boardVo1.getFile();
        return file;
    }
}
