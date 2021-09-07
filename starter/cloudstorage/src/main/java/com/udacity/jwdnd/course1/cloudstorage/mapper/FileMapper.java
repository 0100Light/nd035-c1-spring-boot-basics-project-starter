package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UploadFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("insert into FILES (filename, contenttype, filesize, userid, filelocation) values ('${filename}', '${contenttype}', '${filesize}', '${userid}', '${filelocation}')")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int addFile(UploadFile file);

    // 這邊在propagate的方式是用constructor，如果要取所有欄位的話一定也要有一個完整的constructor
    // 不然會出現 exception: org.h2.jdbc.JdbcSQLDataException: Data conversion error converting "txt"
    @Select("select * from FILES where userid = ${userId} limit 50")
    public List<UploadFile> getFiles(int userId);

    @Select("select * from FILES where fileId = ${fileId} limit 1")
    public UploadFile getFile(int fileId);

//    @Update("update FILES set filename=${filename}, contenttype=${contenttype}, filesize=${filesize}, filelocation=${filelocation} where fileId = ${fileId}")
//    public int updateFile(UploadFile file);
    @Delete("delete from FILES where fileId = ${fileId}")
    public int delFile(int fileId);
}
