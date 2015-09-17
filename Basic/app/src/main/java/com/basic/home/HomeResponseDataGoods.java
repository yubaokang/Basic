package com.basic.home;

/**
 * Created by ybk on 2015/9/7.
 */
public class HomeResponseDataGoods {
    private int id;// 301,

    private float discountPrice;//45.2,

    private int houseTypeId;// 7,

    private String houseTypeName;// "四室两厅",

    private String name;// "【卢瓦尔】深圳武警小区B5户型",

    private String photoPath;//: "http://t-image.goujiawang.com/store/uploaded/143918/417c54e19d22eb03d214e5eb.jpg",

    private int styleId;//15,

    private String styleName;// "卢瓦尔"

    private String gmtCreate; //收藏列表用到



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(int houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
