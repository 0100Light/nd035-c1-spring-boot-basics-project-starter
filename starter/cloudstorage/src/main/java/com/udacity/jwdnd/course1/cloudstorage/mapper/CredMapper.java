package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Cred;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredMapper {
    @Insert("insert into CREDENTIALS (url, username, key, password, userid) values ('${url}', '${username}', '${key}', '${password}', ${userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public int addCred(Cred cred);

    @Select("select * from CREDENTIALS where userid=${userid} LIMIT 50")
    @Results({
            @Result(property = "password", column = "password")
    })
    public List<Cred> creds(int userid);

    @Select("select * from CREDENTIALS where CREDENTIALID = ${credId}")
    public Cred cred(int credId);

    @Delete("delete from CREDENTIALS where credentialid = ${credid}")
    public int delCred(int credId);

    @Update("update CREDENTIALS set url='${url}', username='${username}', password='${password}' where CREDENTIALID = ${credentialid}")
    public int updateCred(Cred cred);
}
