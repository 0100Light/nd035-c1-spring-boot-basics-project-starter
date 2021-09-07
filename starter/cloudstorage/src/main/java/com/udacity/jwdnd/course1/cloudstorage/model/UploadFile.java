package com.udacity.jwdnd.course1.cloudstorage.model;

public class UploadFile {
    private int fileId;
    private String filename;
    private String contenttype;
    private long filesize;
    private int userid;
    private String filelocation;

    public UploadFile(String filename, String contenttype, long filesize, int userid, String filelocation) {
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filelocation = filelocation;
    }

    public UploadFile(int fileId, String filename, String contenttype, long filesize, int userid, String filelocation) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filelocation = filelocation;
    }

    public int getFileId() {
        return fileId;
    }

    public int getUserid() {
        return userid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }
}