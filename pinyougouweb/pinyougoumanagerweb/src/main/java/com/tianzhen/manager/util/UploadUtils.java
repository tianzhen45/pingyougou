package com.tianzhen.manager.util;

import org.springframework.web.multipart.MultipartFile;

public interface UploadUtils {

    String upload(MultipartFile file)throws Exception;
}
