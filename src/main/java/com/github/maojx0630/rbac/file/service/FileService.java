package com.github.maojx0630.rbac.file.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.rbac.file.mapper.FileMapper;
import com.github.maojx0630.rbac.file.model.FileInfo;
import org.springframework.stereotype.Service;

@Service
public class FileService extends ServiceImpl<FileMapper, FileInfo> {
}
