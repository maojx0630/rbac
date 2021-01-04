package com.github.maojx0630.rbac.file.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@TableName("file_info")
@EqualsAndHashCode(callSuper = true)
public class FileInfo extends ModelEntity {

    /**
     * 文件名称 不带后缀
     */
    private String name;

    /**
     * 文件hash值 指向真正文件
     */
    private String hash;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     *   文件后缀名
     */
    private String suffix;

    /**
     * 文件访问权限 0 公共 1 私有
     */
    private String visit;

    /**
     * 关联的表名
     */
    private String joinTable;

    /**
     * 关联的id
     */
    private Long joinId;
}
