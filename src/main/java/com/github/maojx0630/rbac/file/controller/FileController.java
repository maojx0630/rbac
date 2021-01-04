package com.github.maojx0630.rbac.file.controller;

import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.utils.file.FileUtils;
import com.github.maojx0630.rbac.file.model.FileInfo;
import com.github.maojx0630.rbac.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

@Api(tags = "文件控制器")
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService service;

    @ApiOperation("显示文件")
    @GetMapping("show/{id}")
    public void show(@ApiParam(value = "文件id", required = true) @PathVariable String id, HttpServletResponse response) {
        FileInfo info = service.getById(id);
        File file = getFileInfo(info);
        writeFile(response, file, info.getName() + "." + info.getSuffix(), info.getContentType());
    }

    @ApiOperation("下载文件")
    @GetMapping("download/{id}")
    public void show(@ApiParam(value = "文件id", required = true) @PathVariable String id, HttpServletResponse response,
                     @ApiParam("文件别名") @RequestParam(value = "name", required = false) String name) {
        FileInfo info = service.getById(id);
        File file = getFileInfo(info);
        String fileName;
        if (StrUtil.isNotBlank(name)) {
            fileName = name;
        } else {
            fileName = info.getName() + "." + info.getSuffix();
        }
        writeFile(response, file, fileName, "application/octet-stream");
    }

    private void writeFile(HttpServletResponse response, File file, String fileName, String contentType) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw StateEnum.file_download_error.create().setData(e.getMessage());
        }
        response.setHeader("Content-Length", file.length() + "");
        response.setHeader("Content-Type", contentType);
        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            //创建缓冲区
            byte[] buffer = new byte[1024];
            int len;
            //循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw StateEnum.file_download_error.create();
        }
    }

    private File getFileInfo(FileInfo info) {
        if (Objects.isNull(info)) {
            throw StateEnum.file_does_not_exist.create();
        }
        File file = FileUtils.getFile(info.getHash());
        if (file.exists()) {
            return file;
        } else {
            throw StateEnum.file_does_not_exist.create();
        }
    }
}
