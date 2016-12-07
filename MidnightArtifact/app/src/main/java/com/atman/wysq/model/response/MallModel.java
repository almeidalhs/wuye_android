package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/12/7.
 */

public class MallModel {
    /**
     * result : 1
     * body : {"adListOne":[{"ad_info_id":29,"name":"sad","description":"asdf","ad_pic":"/imageServer/prop/userbackground/woman/2817/1.jpg","ad_url":"http://www.baidu.com","ad_goods_id":111,"sort":7,"series":2,"type":1,"create_time":1446716212000,"update_time":1446716212000}],"adListTwo":[{"ad_info_id":38,"name":"情趣众筹","description":"情趣众筹","ad_pic":"/imageServer/22D49389a7b9b4644614a5c46cc09a53d779.jpg","ad_url":"","sort":1,"series":3,"type":8,"create_time":1463640935000,"update_time":1469443072000},{"ad_info_id":14,"name":"双倍金币","description":"有钱就是任性","ad_pic":"/imageServer/B5EE3f547496f7b5429eadea0a1a091d08bf.jpg","ad_url":"","ad_goods_id":73,"sort":4,"series":3,"type":5,"create_time":1440490938000,"update_time":1443405175000},{"ad_info_id":28,"name":"每日抽奖","description":"111","ad_pic":"/imageServer/14C5ea631a999b9847edb44e96ec4ea10620.jpg","ad_url":"http://www.baidu.com","sort":6,"series":3,"type":11,"create_time":1443591297000,"update_time":1443591297000},{"ad_info_id":13,"name":"限时抢购","description":"限时啦，不抢对不起自己！","ad_pic":"/imageServer/C744a81c3fc0c4334850b1757d4aeb9610ba.jpg","ad_url":"","ad_goods_id":74,"sort":7,"series":3,"type":5,"create_time":1443161261000,"update_time":1443405161000}],"categoryList":[{"goods_category_id":1,"name":"男性专区","ad_pic":"/imageServer/prop/temp/1.jpg","ad_url":"http://www.meipai.com/?f=MzYwbmF2aWZz","ad_goods_id":"0","sort":1,"type":1,"leafCategorieList":[{"goods_category_id":84,"name":"充气娃娃","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b5.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/a1.png"},{"goods_category_id":83,"name":"自动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":2,"ad_club_pic":"/imageServer/prop/adinfo/a2.png"},{"goods_category_id":81,"name":"手动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":3,"ad_club_pic":"/imageServer/prop/adinfo/a3.png"},{"goods_category_id":76,"name":"名器倒模","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":4,"type":5,"ad_club_pic":"/imageServer/prop/adinfo/a4.png"},{"goods_category_id":85,"name":"男性按摩","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":5,"ad_club_pic":"/imageServer/prop/adinfo/a5.png"}],"ad_club_pic":"/imageServer/prop/temp/1.jpg"}],"magicList":[{"magic_id":5568,"title":"点1","pic_img":"/imageServer/00543a8cdfdb67f44a12be0151645bbb3dc3.jpg","hot":0,"status":2,"magic_index":1,"cover_pic_img":"/imageServer/63C2e65a3b7652a547e2a66da4c77f44d683.jpg","cover_pic_width":305,"cover_pic_height":220,"cover_title":"我说点几就点几","total_magic":4,"view_count":49,"min_level":0,"min_gold":0,"create_time":1470731688000,"update_time":1470731719000,"percent":0,"user_magic_id":888888888,"goods_id":159,"goods_title":"测试图文详情顺序五张图","goods_pic_img":"/imageServer/55719edb08776f2a42ada86e106e0fde090d.jpg","price":"100.00","discount_price":"95.00","goods_sales":5}],"goodsList":[{"goods_id":65,"title":"共振1","pic_img":"/imageServer/763Efba5244c4861403f9d54a9aea6ba36a5.jpg","store":"100","price":"0","discount_price":"0.03","sales":11,"special_edition_id":13,"status":2,"key_words":"打飞机","gold_coin":1111,"integral":1111,"chat_prop_id":9,"update_time":1443174707000,"goods_type":1,"source":1,"postage":1,"gold_coin_price":11111,"charm_price":1111}],"wishMap":{"imgList":["/imageServer/A18Acebab82674d94d0bbde550a1734d46a9.jpg"],"contList":["vbnb 你吧","这个是否也好累累硕果","人，他与，不停。他徒步啊","感觉咋不？","高端访谈和幸福感方法过度开发工程基地参观参观考察祸从口出和祸从口出佛教国家规定价格大惊小怪从恐惧"]},"fundMap":{"imgList":["/imageServer/5D618e308f049bfc4d7fbae98edf0ca45830.jpg"],"contList":["夺得话费充值100元:五七零一。","夺得话费充值100元:五七零一。","夺得快充10元话费1元得:五七零一。","夺得快充10元话费1元得:五七零一。","夺得快充10元话费1元得:五七零一。"]}}
     */

    private String result;
    /**
     * adListOne : [{"ad_info_id":29,"name":"sad","description":"asdf","ad_pic":"/imageServer/prop/userbackground/woman/2817/1.jpg","ad_url":"http://www.baidu.com","ad_goods_id":111,"sort":7,"series":2,"type":1,"create_time":1446716212000,"update_time":1446716212000}]
     * adListTwo : [{"ad_info_id":38,"name":"情趣众筹","description":"情趣众筹","ad_pic":"/imageServer/22D49389a7b9b4644614a5c46cc09a53d779.jpg","ad_url":"","sort":1,"series":3,"type":8,"create_time":1463640935000,"update_time":1469443072000},{"ad_info_id":14,"name":"双倍金币","description":"有钱就是任性","ad_pic":"/imageServer/B5EE3f547496f7b5429eadea0a1a091d08bf.jpg","ad_url":"","ad_goods_id":73,"sort":4,"series":3,"type":5,"create_time":1440490938000,"update_time":1443405175000},{"ad_info_id":28,"name":"每日抽奖","description":"111","ad_pic":"/imageServer/14C5ea631a999b9847edb44e96ec4ea10620.jpg","ad_url":"http://www.baidu.com","sort":6,"series":3,"type":11,"create_time":1443591297000,"update_time":1443591297000},{"ad_info_id":13,"name":"限时抢购","description":"限时啦，不抢对不起自己！","ad_pic":"/imageServer/C744a81c3fc0c4334850b1757d4aeb9610ba.jpg","ad_url":"","ad_goods_id":74,"sort":7,"series":3,"type":5,"create_time":1443161261000,"update_time":1443405161000}]
     * categoryList : [{"goods_category_id":1,"name":"男性专区","ad_pic":"/imageServer/prop/temp/1.jpg","ad_url":"http://www.meipai.com/?f=MzYwbmF2aWZz","ad_goods_id":"0","sort":1,"type":1,"leafCategorieList":[{"goods_category_id":84,"name":"充气娃娃","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b5.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/a1.png"},{"goods_category_id":83,"name":"自动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":2,"ad_club_pic":"/imageServer/prop/adinfo/a2.png"},{"goods_category_id":81,"name":"手动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":3,"ad_club_pic":"/imageServer/prop/adinfo/a3.png"},{"goods_category_id":76,"name":"名器倒模","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":4,"type":5,"ad_club_pic":"/imageServer/prop/adinfo/a4.png"},{"goods_category_id":85,"name":"男性按摩","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":5,"ad_club_pic":"/imageServer/prop/adinfo/a5.png"}],"ad_club_pic":"/imageServer/prop/temp/1.jpg"}]
     * magicList : [{"magic_id":5568,"title":"点1","pic_img":"/imageServer/00543a8cdfdb67f44a12be0151645bbb3dc3.jpg","hot":0,"status":2,"magic_index":1,"cover_pic_img":"/imageServer/63C2e65a3b7652a547e2a66da4c77f44d683.jpg","cover_pic_width":305,"cover_pic_height":220,"cover_title":"我说点几就点几","total_magic":4,"view_count":49,"min_level":0,"min_gold":0,"create_time":1470731688000,"update_time":1470731719000,"percent":0,"user_magic_id":888888888,"goods_id":159,"goods_title":"测试图文详情顺序五张图","goods_pic_img":"/imageServer/55719edb08776f2a42ada86e106e0fde090d.jpg","price":"100.00","discount_price":"95.00","goods_sales":5}]
     * goodsList : [{"goods_id":65,"title":"共振1","pic_img":"/imageServer/763Efba5244c4861403f9d54a9aea6ba36a5.jpg","store":"100","price":"0","discount_price":"0.03","sales":11,"special_edition_id":13,"status":2,"key_words":"打飞机","gold_coin":1111,"integral":1111,"chat_prop_id":9,"update_time":1443174707000,"goods_type":1,"source":1,"postage":1,"gold_coin_price":11111,"charm_price":1111}]
     * wishMap : {"imgList":["/imageServer/A18Acebab82674d94d0bbde550a1734d46a9.jpg"],"contList":["vbnb 你吧","这个是否也好累累硕果","人，他与，不停。他徒步啊","感觉咋不？","高端访谈和幸福感方法过度开发工程基地参观参观考察祸从口出和祸从口出佛教国家规定价格大惊小怪从恐惧"]}
     * fundMap : {"imgList":["/imageServer/5D618e308f049bfc4d7fbae98edf0ca45830.jpg"],"contList":["夺得话费充值100元:五七零一。","夺得话费充值100元:五七零一。","夺得快充10元话费1元得:五七零一。","夺得快充10元话费1元得:五七零一。","夺得快充10元话费1元得:五七零一。"]}
     */

    private BodyBean body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private WishMapBean wishMap;
        private FundMapBean fundMap;
        /**
         * ad_info_id : 29
         * name : sad
         * description : asdf
         * ad_pic : /imageServer/prop/userbackground/woman/2817/1.jpg
         * ad_url : http://www.baidu.com
         * ad_goods_id : 111
         * sort : 7
         * series : 2
         * type : 1
         * create_time : 1446716212000
         * update_time : 1446716212000
         */

        private List<AdListOneBean> adListOne;
        /**
         * ad_info_id : 38
         * name : 情趣众筹
         * description : 情趣众筹
         * ad_pic : /imageServer/22D49389a7b9b4644614a5c46cc09a53d779.jpg
         * ad_url :
         * sort : 1
         * series : 3
         * type : 8
         * create_time : 1463640935000
         * update_time : 1469443072000
         */

        private List<AdListTwoBean> adListTwo;
        /**
         * goods_category_id : 1
         * name : 男性专区
         * ad_pic : /imageServer/prop/temp/1.jpg
         * ad_url : http://www.meipai.com/?f=MzYwbmF2aWZz
         * ad_goods_id : 0
         * sort : 1
         * type : 1
         * leafCategorieList : [{"goods_category_id":84,"name":"充气娃娃","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b5.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/a1.png"},{"goods_category_id":83,"name":"自动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":2,"ad_club_pic":"/imageServer/prop/adinfo/a2.png"},{"goods_category_id":81,"name":"手动飞机杯","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":3,"ad_club_pic":"/imageServer/prop/adinfo/a3.png"},{"goods_category_id":76,"name":"名器倒模","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":4,"type":5,"ad_club_pic":"/imageServer/prop/adinfo/a4.png"},{"goods_category_id":85,"name":"男性按摩","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b4.png","ad_url":"","sort":5,"ad_club_pic":"/imageServer/prop/adinfo/a5.png"}]
         * ad_club_pic : /imageServer/prop/temp/1.jpg
         */

        private List<CategoryListBean> categoryList;
        /**
         * magic_id : 5568
         * title : 点1
         * pic_img : /imageServer/00543a8cdfdb67f44a12be0151645bbb3dc3.jpg
         * hot : 0
         * status : 2
         * magic_index : 1
         * cover_pic_img : /imageServer/63C2e65a3b7652a547e2a66da4c77f44d683.jpg
         * cover_pic_width : 305
         * cover_pic_height : 220
         * cover_title : 我说点几就点几
         * total_magic : 4
         * view_count : 49
         * min_level : 0
         * min_gold : 0
         * create_time : 1470731688000
         * update_time : 1470731719000
         * percent : 0
         * user_magic_id : 888888888
         * goods_id : 159
         * goods_title : 测试图文详情顺序五张图
         * goods_pic_img : /imageServer/55719edb08776f2a42ada86e106e0fde090d.jpg
         * price : 100.00
         * discount_price : 95.00
         * goods_sales : 5
         */

        private List<MagicListBean> magicList;
        /**
         * goods_id : 65
         * title : 共振1
         * pic_img : /imageServer/763Efba5244c4861403f9d54a9aea6ba36a5.jpg
         * store : 100
         * price : 0
         * discount_price : 0.03
         * sales : 11
         * special_edition_id : 13
         * status : 2
         * key_words : 打飞机
         * gold_coin : 1111
         * integral : 1111
         * chat_prop_id : 9
         * update_time : 1443174707000
         * goods_type : 1
         * source : 1
         * postage : 1
         * gold_coin_price : 11111
         * charm_price : 1111
         */

        private List<GoodsListBean> goodsList;

        public WishMapBean getWishMap() {
            return wishMap;
        }

        public void setWishMap(WishMapBean wishMap) {
            this.wishMap = wishMap;
        }

        public FundMapBean getFundMap() {
            return fundMap;
        }

        public void setFundMap(FundMapBean fundMap) {
            this.fundMap = fundMap;
        }

        public List<AdListOneBean> getAdListOne() {
            return adListOne;
        }

        public void setAdListOne(List<AdListOneBean> adListOne) {
            this.adListOne = adListOne;
        }

        public List<AdListTwoBean> getAdListTwo() {
            return adListTwo;
        }

        public void setAdListTwo(List<AdListTwoBean> adListTwo) {
            this.adListTwo = adListTwo;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public List<MagicListBean> getMagicList() {
            return magicList;
        }

        public void setMagicList(List<MagicListBean> magicList) {
            this.magicList = magicList;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class WishMapBean {
            private List<String> imgList;
            private List<String> contList;

            public List<String> getImgList() {
                return imgList;
            }

            public void setImgList(List<String> imgList) {
                this.imgList = imgList;
            }

            public List<String> getContList() {
                return contList;
            }

            public void setContList(List<String> contList) {
                this.contList = contList;
            }
        }

        public static class FundMapBean {
            private List<String> imgList;
            private List<String> contList;

            public List<String> getImgList() {
                return imgList;
            }

            public void setImgList(List<String> imgList) {
                this.imgList = imgList;
            }

            public List<String> getContList() {
                return contList;
            }

            public void setContList(List<String> contList) {
                this.contList = contList;
            }
        }

        public static class AdListOneBean {
            private int ad_info_id;
            private String name;
            private String description;
            private String ad_pic;
            private String ad_url;
            private int ad_goods_id;
            private int sort;
            private int series;
            private int type;
            private long create_time;
            private long update_time;

            public int getAd_info_id() {
                return ad_info_id;
            }

            public void setAd_info_id(int ad_info_id) {
                this.ad_info_id = ad_info_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public int getAd_goods_id() {
                return ad_goods_id;
            }

            public void setAd_goods_id(int ad_goods_id) {
                this.ad_goods_id = ad_goods_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getSeries() {
                return series;
            }

            public void setSeries(int series) {
                this.series = series;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }
        }

        public static class AdListTwoBean {
            private int ad_info_id;
            private String name;
            private String description;
            private String ad_pic;
            private String ad_url;
            private int sort;
            private int series;
            private int type;
            private long create_time;
            private long update_time;

            public int getAd_info_id() {
                return ad_info_id;
            }

            public void setAd_info_id(int ad_info_id) {
                this.ad_info_id = ad_info_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getSeries() {
                return series;
            }

            public void setSeries(int series) {
                this.series = series;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }
        }

        public static class CategoryListBean {
            private int goods_category_id;
            private String name;
            private String ad_pic;
            private String ad_url;
            private String ad_goods_id;
            private int sort;
            private int type;
            private String ad_club_pic;
            /**
             * goods_category_id : 84
             * name : 充气娃娃
             * parient_id : 8
             * ad_pic : /imageServer/prop/adinfo/b5.png
             * ad_url :
             * sort : 1
             * ad_club_pic : /imageServer/prop/adinfo/a1.png
             */

            private List<LeafCategorieListBean> leafCategorieList;

            public int getGoods_category_id() {
                return goods_category_id;
            }

            public void setGoods_category_id(int goods_category_id) {
                this.goods_category_id = goods_category_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_goods_id() {
                return ad_goods_id;
            }

            public void setAd_goods_id(String ad_goods_id) {
                this.ad_goods_id = ad_goods_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAd_club_pic() {
                return ad_club_pic;
            }

            public void setAd_club_pic(String ad_club_pic) {
                this.ad_club_pic = ad_club_pic;
            }

            public List<LeafCategorieListBean> getLeafCategorieList() {
                return leafCategorieList;
            }

            public void setLeafCategorieList(List<LeafCategorieListBean> leafCategorieList) {
                this.leafCategorieList = leafCategorieList;
            }

            public static class LeafCategorieListBean {
                private int goods_category_id;
                private String name;
                private int parient_id;
                private String ad_pic;
                private String ad_url;
                private int sort;
                private String ad_club_pic;

                public int getGoods_category_id() {
                    return goods_category_id;
                }

                public void setGoods_category_id(int goods_category_id) {
                    this.goods_category_id = goods_category_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getParient_id() {
                    return parient_id;
                }

                public void setParient_id(int parient_id) {
                    this.parient_id = parient_id;
                }

                public String getAd_pic() {
                    return ad_pic;
                }

                public void setAd_pic(String ad_pic) {
                    this.ad_pic = ad_pic;
                }

                public String getAd_url() {
                    return ad_url;
                }

                public void setAd_url(String ad_url) {
                    this.ad_url = ad_url;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getAd_club_pic() {
                    return ad_club_pic;
                }

                public void setAd_club_pic(String ad_club_pic) {
                    this.ad_club_pic = ad_club_pic;
                }
            }
        }

        public static class MagicListBean {
            private int magic_id;
            private String title;
            private String pic_img;
            private int hot;
            private int status;
            private int magic_index;
            private String cover_pic_img;
            private int cover_pic_width;
            private int cover_pic_height;
            private String cover_title;
            private int total_magic;
            private int view_count;
            private int min_level;
            private int min_gold;
            private long create_time;
            private long update_time;
            private String percent;
            private int user_magic_id;
            private int goods_id;
            private String goods_title;
            private String goods_pic_img;
            private String price;
            private String discount_price;
            private int goods_sales;

            public int getMagic_id() {
                return magic_id;
            }

            public void setMagic_id(int magic_id) {
                this.magic_id = magic_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic_img() {
                return pic_img;
            }

            public void setPic_img(String pic_img) {
                this.pic_img = pic_img;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getMagic_index() {
                return magic_index;
            }

            public void setMagic_index(int magic_index) {
                this.magic_index = magic_index;
            }

            public String getCover_pic_img() {
                return cover_pic_img;
            }

            public void setCover_pic_img(String cover_pic_img) {
                this.cover_pic_img = cover_pic_img;
            }

            public int getCover_pic_width() {
                return cover_pic_width;
            }

            public void setCover_pic_width(int cover_pic_width) {
                this.cover_pic_width = cover_pic_width;
            }

            public int getCover_pic_height() {
                return cover_pic_height;
            }

            public void setCover_pic_height(int cover_pic_height) {
                this.cover_pic_height = cover_pic_height;
            }

            public String getCover_title() {
                return cover_title;
            }

            public void setCover_title(String cover_title) {
                this.cover_title = cover_title;
            }

            public int getTotal_magic() {
                return total_magic;
            }

            public void setTotal_magic(int total_magic) {
                this.total_magic = total_magic;
            }

            public int getView_count() {
                return view_count;
            }

            public void setView_count(int view_count) {
                this.view_count = view_count;
            }

            public int getMin_level() {
                return min_level;
            }

            public void setMin_level(int min_level) {
                this.min_level = min_level;
            }

            public int getMin_gold() {
                return min_gold;
            }

            public void setMin_gold(int min_gold) {
                this.min_gold = min_gold;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public int getUser_magic_id() {
                return user_magic_id;
            }

            public void setUser_magic_id(int user_magic_id) {
                this.user_magic_id = user_magic_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_title() {
                return goods_title;
            }

            public void setGoods_title(String goods_title) {
                this.goods_title = goods_title;
            }

            public String getGoods_pic_img() {
                return goods_pic_img;
            }

            public void setGoods_pic_img(String goods_pic_img) {
                this.goods_pic_img = goods_pic_img;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public int getGoods_sales() {
                return goods_sales;
            }

            public void setGoods_sales(int goods_sales) {
                this.goods_sales = goods_sales;
            }
        }

        public static class GoodsListBean {
            private int goods_id;
            private String title;
            private String pic_img;
            private String store;
            private double price;
            private double discount_price;
            private int sales;
            private int special_edition_id;
            private int status;
            private String key_words;
            private int gold_coin;
            private int integral;
            private int chat_prop_id;
            private long update_time;
            private int goods_type;
            private int source;
            private int postage;
            private int gold_coin_price;
            private double charm_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic_img() {
                return pic_img;
            }

            public void setPic_img(String pic_img) {
                this.pic_img = pic_img;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(double discount_price) {
                this.discount_price = discount_price;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getSpecial_edition_id() {
                return special_edition_id;
            }

            public void setSpecial_edition_id(int special_edition_id) {
                this.special_edition_id = special_edition_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getKey_words() {
                return key_words;
            }

            public void setKey_words(String key_words) {
                this.key_words = key_words;
            }

            public int getGold_coin() {
                return gold_coin;
            }

            public void setGold_coin(int gold_coin) {
                this.gold_coin = gold_coin;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public int getChat_prop_id() {
                return chat_prop_id;
            }

            public void setChat_prop_id(int chat_prop_id) {
                this.chat_prop_id = chat_prop_id;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public int getPostage() {
                return postage;
            }

            public void setPostage(int postage) {
                this.postage = postage;
            }

            public int getGold_coin_price() {
                return gold_coin_price;
            }

            public void setGold_coin_price(int gold_coin_price) {
                this.gold_coin_price = gold_coin_price;
            }

            public double getCharm_price() {
                return charm_price;
            }

            public void setCharm_price(double charm_price) {
                this.charm_price = charm_price;
            }
        }
    }
}
