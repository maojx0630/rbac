package com.github.maojx0630.rbac.common.utils.file;


import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.global.MyProperty;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.utils.SpringUtils;
import com.github.maojx0630.rbac.file.model.FileInfo;
import com.github.maojx0630.rbac.file.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String BASE_PATH = SpringUtils.getBean(MyProperty.class).getFileBasePath();

    private static final FileService FILE_SERVICE = SpringUtils.getBean(FileService.class);

    /**
     * 上传单个文件
     * @param file 文件
     * @param table 关联的表名
     * @param id 关联的id
     * @param visitPublic 是否公开
     * @return 存储对象
     */
    public static FileInfo saveFile(MultipartFile file, String table, Long id, boolean visitPublic) {
        FileInfo fileInfo = saveTo(file, table, id, visitPublic);
        FILE_SERVICE.save(fileInfo);
        return fileInfo;
    }

    /**
     * 批量上传 文件
     * @param files 文件集合
     * @param table 关联表名
     * @param id 关联id
     * @param visitPublic 是否公开
     * @return 存储的file集合
     */
    public static List<FileInfo> saveFile(List<MultipartFile> files,String table, Long id, boolean visitPublic){
        List<FileInfo> list=new ArrayList<>();
        if(files.isEmpty()){
            throw StateEnum.file_empty.create();
        }
        files.forEach( info->list.add(saveTo(info,table,id,visitPublic)));
        FILE_SERVICE.saveBatch(list);
        return list;
    }

    /**
     * 获取文件
     * @param hash 文件hash值
     * @return 文件
     */
    public static File getFile(String hash){
        return new File(BASE_PATH+hash);
    }

    //真正执行保存文件的方法
    private static FileInfo saveTo(MultipartFile file, String table, Long id,boolean visitPublic){
        if(file.isEmpty()){
            throw StateEnum.file_empty.create();
        }
        String filename = file.getOriginalFilename();
        if(StrUtil.isBlank(filename)||!filename.contains(".")||filename.lastIndexOf(".")+1==filename.length()){
            throw StateEnum.file_has_no_suffix.create();
        }
        String name =filename.substring(0,filename.lastIndexOf("."));
        String suffix=filename.substring(filename.lastIndexOf(".")+1);
        FileInfo fileInfo=new FileInfo();
        fileInfo.setName(name);
        fileInfo.setHash(Etag.tag(file));
        fileInfo.setContentType(file.getContentType());
        fileInfo.setSuffix(suffix);
        if(visitPublic){
            fileInfo.setVisit("0");
        }else{
            fileInfo.setVisit("1");
        }
        fileInfo.setJoinTable(table);
        fileInfo.setJoinId(id);
        File localFile=new File(BASE_PATH+fileInfo.getHash());
        if(!localFile.exists()){
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
              throw StateEnum.file_cannot_save.create().setData(e.getMessage());
            }
        }
        return fileInfo;
    }

}
