package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/30.
 */

public class GetReportListModel {
    /**
     * result : 1
     * body : [{"report_reason_id":5,"title":"淫秽色情","description":"淫秽色情","create_time":1490171471000,"update_time":1490171473000,"type":2,"is_other":0},{"report_reason_id":6,"title":"剽窃侵权","description":"剽窃侵权","create_time":1490171493000,"update_time":1490171494000,"type":2,"is_other":0},{"report_reason_id":7,"title":"造谣诽谤","description":"造谣诽谤","create_time":1490171512000,"update_time":1490171513000,"type":2,"is_other":0},{"report_reason_id":8,"title":"钓鱼欺诈","description":"钓鱼欺诈","create_time":1490171535000,"update_time":1490171536000,"type":2,"is_other":0},{"report_reason_id":9,"title":"垃圾广告","description":"垃圾广告","create_time":1490171552000,"update_time":1490171553000,"type":2,"is_other":0},{"report_reason_id":10,"title":"反动违法","description":"反动违法","create_time":1490171568000,"update_time":1490171667000,"type":2,"is_other":0},{"report_reason_id":11,"title":"其他说明（自定义）","description":"其他说明（自定义）","create_time":1490171600000,"update_time":1490171602000,"type":2,"is_other":1}]
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
         * report_reason_id : 5
         * title : 淫秽色情
         * description : 淫秽色情
         * create_time : 1490171471000
         * update_time : 1490171473000
         * type : 2
         * is_other : 0
         */

        private int report_reason_id;
        private String title;
        private String description;
        private long create_time;
        private long update_time;
        private int type;
        private int is_other;

        public int getReport_reason_id() {
            return report_reason_id;
        }

        public void setReport_reason_id(int report_reason_id) {
            this.report_reason_id = report_reason_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_other() {
            return is_other;
        }

        public void setIs_other(int is_other) {
            this.is_other = is_other;
        }
    }
}
