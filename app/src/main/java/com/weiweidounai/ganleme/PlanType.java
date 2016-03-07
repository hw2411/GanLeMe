package com.weiweidounai.ganleme;

/**
 * Created by haha on 2016/3/7.
 */
public class PlanType {
    private int imgId;
    private String planType;
    private int planComplete;

    public PlanType(int imgId, String planType) {
        this.planType = planType;
        this.imgId=imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getPlanComplete() {
        return planComplete;
    }

    public void setPlanComplete(int planComplete) {
        this.planComplete = planComplete;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }
}
