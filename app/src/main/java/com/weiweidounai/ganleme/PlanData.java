package com.weiweidounai.ganleme;

/**
 * Created by haha on 2016/3/7.
 */
public class PlanData {
    private int imgId;
    private String planType;
    private int planComplete;

    public PlanData(int imgId, String planType,int planComplete) {
        this.planType = planType;
        this.imgId=imgId;
        this.planComplete=planComplete;
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
