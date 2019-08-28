package bootstrap.domain;

import java.io.Serializable;
import javax.persistence.*;

public class Bilibili implements Serializable {
    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * av号
     */
    @Column(name = "av_id")
    private Integer avId;

    /**
     * 浏览
     */
    @Column(name = "view_f")
    private Integer view;

    /**
     * 弹幕
     */
    private Integer danmaku;

    /**
     * 回复
     */
    private Integer reply;

    /**
     * 收藏
     */
    private Integer favorite;

    /**
     * 硬币数
     */
    private Integer coin;

    /**
     * 分享
     */
    @Column(name = "share_f")
    private Integer share;

    /**
     * 点赞数
     */
    @Column(name = "like_f")
    private Integer like;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取av号
     *
     * @return av_id - av号
     */
    public Integer getAvId() {
        return avId;
    }

    /**
     * 设置av号
     *
     * @param avId av号
     */
    public void setAvId(Integer avId) {
        this.avId = avId;
    }

    /**
     * 获取浏览
     *
     * @return view - 浏览
     */
    public Integer getView() {
        return view;
    }

    /**
     * 设置浏览
     *
     * @param view 浏览
     */
    public void setView(Integer view) {
        this.view = view;
    }

    /**
     * 获取弹幕
     *
     * @return danmaku - 弹幕
     */
    public Integer getDanmaku() {
        return danmaku;
    }

    /**
     * 设置弹幕
     *
     * @param danmaku 弹幕
     */
    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }

    /**
     * 获取回复
     *
     * @return reply - 回复
     */
    public Integer getReply() {
        return reply;
    }

    /**
     * 设置回复
     *
     * @param reply 回复
     */
    public void setReply(Integer reply) {
        this.reply = reply;
    }

    /**
     * 获取收藏
     *
     * @return favorite - 收藏
     */
    public Integer getFavorite() {
        return favorite;
    }

    /**
     * 设置收藏
     *
     * @param favorite 收藏
     */
    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    /**
     * 获取硬币数
     *
     * @return coin - 硬币数
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置硬币数
     *
     * @param coin 硬币数
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取分享
     *
     * @return share - 分享
     */
    public Integer getShare() {
        return share;
    }

    /**
     * 设置分享
     *
     * @param share 分享
     */
    public void setShare(Integer share) {
        this.share = share;
    }

    /**
     * 获取点赞数
     *
     * @return like - 点赞数
     */
    public Integer getLike() {
        return like;
    }

    /**
     * 设置点赞数
     *
     * @param like 点赞数
     */
    public void setLike(Integer like) {
        this.like = like;
    }
}