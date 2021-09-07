package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteA;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from NOTES where userid=${userId} LIMIT 100")
    public List<NoteA> getNotes(int UserId);

    @Select("select * from NOTES where noteid = #{noteId}")
    public NoteA getNote(int noteId);


    @Insert("insert into NOTES (notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    public int addNote(NoteA note);

    @Select("select count(*) from NOTES")
    public int countNotes();

    @Delete("delete from NOTES where noteid = ${noteId}")
    public int deleteNote(int noteId);

    @Update("update NOTES set notetitle='${notetitle}', notedescription='${notedescription}' where noteid=${noteid}")
    public void updateNote(NoteA note);
}
