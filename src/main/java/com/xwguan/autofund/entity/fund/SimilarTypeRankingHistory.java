package com.xwguan.autofund.entity.fund;

import java.time.LocalDate;

/**
 * 同类排名历史
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public class SimilarTypeRankingHistory {

    private Long id;

    /**
     * 对应基金在数据库中的id
     */
    private Long fundId;

    /**
     * 数据日期
     */
    private LocalDate date;

    /**
     * 排名
     */
    private Integer ranking;

    /**
     * 同类总数
     */
    private Integer totalSimilar;

    public SimilarTypeRankingHistory() {
        super();
    }

    public SimilarTypeRankingHistory(LocalDate date, Integer ranking, Integer totalSimilar) {
        this.date = date;
        this.ranking = ranking;
        this.totalSimilar = totalSimilar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getTotalSimilar() {
        return totalSimilar;
    }

    public void setTotalSimilar(Integer totalSimilar) {
        this.totalSimilar = totalSimilar;
    }

    @Override
    public String toString() {
        return "SimilarTypeRankingHistory [id=" + id + ", fundId=" + fundId + ", date=" + date + ", ranking=" + ranking
            + ", totalSimilar=" + totalSimilar + "]";
    }

}
