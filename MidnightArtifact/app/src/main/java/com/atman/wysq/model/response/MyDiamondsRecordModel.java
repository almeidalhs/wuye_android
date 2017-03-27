package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class MyDiamondsRecordModel {
    /**
     * result : 1
     * body : [{"user_gold_log_id":545439,"user_id":450214604,"old_count":9945482,"new_count":9945484,"change_count":2,"type":59,"relate_id":888888,"create_time":1490324686000,"update_time":1490324686000,"consume_gold_coin":0,"consume_convert_coin":2}]
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
         * user_gold_log_id : 545439
         * user_id : 450214604
         * old_count : 9945482
         * new_count : 9945484
         * change_count : 2
         * type : 59
         * relate_id : 888888
         * create_time : 1490324686000
         * update_time : 1490324686000
         * consume_gold_coin : 0
         * consume_convert_coin : 2
         *
         *
         "pay_id": 1,
         "user_id": 450214604,
         "old_num": 8028,
         "num": 6028,
         "pay_num": 2000,
         "wallet_id": 9945484,
         "channel": 1,
         "money": 50,
         "status": 1,
         "create_time": 1490343680000,
         "update_time": 1490343680000

         */

        private int user_gold_log_id;
        private long user_id;
        private int old_count;
        private int new_count;
        private int change_count;
        private int type;
        private long relate_id;
        private long create_time;
        private long update_time;
        private int consume_gold_coin;
        private int consume_convert_coin;

        private int pay_id;
        private int old_num;
        private int num;
        private int pay_num;
        private long wallet_id;
        private int channel;
        private String money;
        private int status;

        public int getPay_id() {
            return pay_id;
        }

        public void setPay_id(int pay_id) {
            this.pay_id = pay_id;
        }

        public int getOld_num() {
            return old_num;
        }

        public void setOld_num(int old_num) {
            this.old_num = old_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getPay_num() {
            return pay_num;
        }

        public void setPay_num(int pay_num) {
            this.pay_num = pay_num;
        }

        public long getWallet_id() {
            return wallet_id;
        }

        public void setWallet_id(long wallet_id) {
            this.wallet_id = wallet_id;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_gold_log_id() {
            return user_gold_log_id;
        }

        public void setUser_gold_log_id(int user_gold_log_id) {
            this.user_gold_log_id = user_gold_log_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getOld_count() {
            return old_count;
        }

        public void setOld_count(int old_count) {
            this.old_count = old_count;
        }

        public int getNew_count() {
            return new_count;
        }

        public void setNew_count(int new_count) {
            this.new_count = new_count;
        }

        public int getChange_count() {
            return change_count;
        }

        public void setChange_count(int change_count) {
            this.change_count = change_count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getRelate_id() {
            return relate_id;
        }

        public void setRelate_id(long relate_id) {
            this.relate_id = relate_id;
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

        public int getConsume_gold_coin() {
            return consume_gold_coin;
        }

        public void setConsume_gold_coin(int consume_gold_coin) {
            this.consume_gold_coin = consume_gold_coin;
        }

        public int getConsume_convert_coin() {
            return consume_convert_coin;
        }

        public void setConsume_convert_coin(int consume_convert_coin) {
            this.consume_convert_coin = consume_convert_coin;
        }
    }
}
