package com.lchen.cn;

import java.io.Serializable;
import java.util.List;

public class FilmInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filmName; // ��Ӱ����
	private String fimHref; // ��Ӱҳ���ַ
	private String filmTitle;// ��Ӱtitle
	private List<String> filmClassify;// ����
	private String filmZone;// ����
	private String filmScreensTime;// ��ӳʱ��ʱ��
	private String filmPublishTime;// ����ʱ��
	private List<String> filmStarred;// ��Ӱ����
	private String filmState;// ��Ӱ״̬
	private List<String> filmImages;// ��Ӱͼ��
	private String filmPoster;// ��Ӱ����
	private String filmSynopsis;// ��Ӱ���

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getFimHref() {
		return fimHref;
	}

	public void setFimHref(String fimHref) {
		this.fimHref = fimHref;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public List<String> getFilmClassify() {
		return filmClassify;
	}

	public void setFilmClassify(List<String> filmClassify) {
		this.filmClassify = filmClassify;
	}

	public String getFilmZone() {
		return filmZone;
	}

	public void setFilmZone(String filmZone) {
		this.filmZone = filmZone;
	}

	public String getFilmScreensTime() {
		return filmScreensTime;
	}

	public void setFilmScreensTime(String filmScreensTime) {
		this.filmScreensTime = filmScreensTime;
	}

	public String getFilmPublishTime() {
		return filmPublishTime;
	}

	public void setFilmPublishTime(String filmPublishTime) {
		this.filmPublishTime = filmPublishTime;
	}

	public List<String> getFilmStarred() {
		return filmStarred;
	}

	public void setFilmStarred(List<String> filmStarred) {
		this.filmStarred = filmStarred;
	}

	public String getFilmState() {
		return filmState;
	}

	public void setFilmState(String filmState) {
		this.filmState = filmState;
	}

	public List<String> getFilmImages() {
		return filmImages;
	}

	public void setFilmImages(List<String> filmImages) {
		this.filmImages = filmImages;
	}

	public String getFilmPoster() {
		return filmPoster;
	}

	public void setFilmPoster(String filmPoster) {
		this.filmPoster = filmPoster;
	}

	public String getFilmSynopsis() {
		return filmSynopsis;
	}

	public void setFilmSynopsis(String filmSynopsis) {
		this.filmSynopsis = filmSynopsis;
	}

}
