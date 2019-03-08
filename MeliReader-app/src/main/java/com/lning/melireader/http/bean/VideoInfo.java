package com.lning.melireader.http.bean;

/**
 * Created by Ning on 2019/2/23.
 */

public class VideoInfo {

   /* {
        "id": "v0301fa70000bhmgvq5srapqvu6isl5g",
            "duration": 239.38,
            "thumbnail": "http://sf1-ttcdn-tos.pstatp.com/obj/tos-cn-p-0000/df71bf7b796d4463bbc364d047b1375b",
            "size": 6503486,
            "vtype": "mp4",
            "main_url": "http://v3-tt.ixigua.com/fee33415388f20b0b31ecc5fb791da9b/5c6ff46f/video/m/2207208d9d7f44d46d8b9be07d724ffad0011617f336000066a41b95efb2/?rc=am9seHRkdnRqazMzOmkzM0ApQHRAbzg4NTs2MzUzMzMzNTUzNDVvQGgzdSlAZjN1KWRzcmd5a3VyZ3lybHh3Zjc2QDJwM3NtbzJzal8tLV4uMHNzLW8jbyM1NTEtMzAtLjEyLS8vNi06I28jOmEtcSM6YHZpXGJmK2BeYmYrXnFsOiMzLl4=",
            "backup_url": "http://v6-tt.ixigua.com/1ded387c16ec61074eb33c3efc0a4254/5c6ff46f/video/m/2207208d9d7f44d46d8b9be07d724ffad0011617f336000066a41b95efb2/?rc=am9seHRkdnRqazMzOmkzM0ApQHRAbzg4NTs2MzUzMzMzNTUzNDVvQGgzdSlAZjN1KWRzcmd5a3VyZ3lybHh3Zjc2QDJwM3NtbzJzal8tLV4uMHNzLW8jbyM1NTEtMzAtLjEyLS8vNi06I28jOmEtcSM6YHZpXGJmK2BeYmYrXnFsOiMzLl4="
    }*/
    private String id;
    private double duration;
    private String thumbnail;
    private long size;
    private String vtype;
    private String main_url;
    private String backup_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getMain_url() {
        return main_url;
    }

    public void setMain_url(String main_url) {
        this.main_url = main_url;
    }

    public String getBackup_url() {
        return backup_url;
    }

    public void setBackup_url(String backup_url) {
        this.backup_url = backup_url;
    }
}
