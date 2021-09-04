package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteA;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from NOTES LIMIT 100")
    public List<NoteA> getNotes();
//    @Results({
//            @Result(property = "title", column = "notetitle"),
//            @Result(property = "description", column = "notedescription"),
//            @Result(property = "userId", column = "userid")
//    })
//    public List<Note> getNotes();
    // TODO: mapping problems


    // title, desc, userid
    @Insert("insert into NOTES (notetitle, notedescription, userid) values (#{title}, #{description}, #{userId})")
//    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    public int addNote(Note note);

    @Select("select count(*) from NOTES")
    public int countNotes();

    @Delete("delete from NOTES where noteid = ${noteid}")
    public int deleteNote(int noteId);

}
