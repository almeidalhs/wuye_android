package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/1.
 */

public class MallCategoryTwoModel {
    /**
     * result : 1
     * body : [{"goods_category_id":8,"name":"火热销售","parient_id":1,"sort":1,"leafCategorieList":[{"goods_category_id":84,"name":"充气娃娃","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b5.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/a1.png"}]}]
     */

    private String result;
    private List<BodyBean> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * goods_category_id : 8
         * name : 火热销售
         * parient_id : 1
         * sort : 1
         * leafCategorieList : [{"goods_category_id":84,"name":"充气娃娃","parient_id":8,"ad_pic":"/imageServer/prop/adinfo/b5.png","ad_url":"","sort":1,"ad_club_pic":"/imageServer/prop/adinfo/a1.png"}]
         */

        private int goods_category_id;
        private String name;
        private int parient_id;
        private int sort;
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

        public int getParient_id() {
            return parient_id;
        }

        public void setParient_id(int parient_id) {
            this.parient_id = parient_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<LeafCategorieListBean> getLeafCategorieList() {
            return leafCategorieList;
        }

        public void setLeafCategorieList(List<LeafCategorieListBean> leafCategorieList) {
            this.leafCategorieList = leafCategorieList;
        }

        public static class LeafCategorieListBean {
            /**
             * goods_category_id : 84
             * name : 充气娃娃
             * parient_id : 8
             * ad_pic : /imageServer/prop/adinfo/b5.png
             * ad_url :
             * sort : 1
             * ad_club_pic : /imageServer/prop/adinfo/a1.png
             */

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
}
