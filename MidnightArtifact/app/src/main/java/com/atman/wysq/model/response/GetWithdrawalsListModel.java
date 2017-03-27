package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/3/27.
 */

public class GetWithdrawalsListModel {
    /**
     * result : 1
     * body : [{"wallet_channel_id":2,"user_id":279307,"account":"15879101702","account_name":"支付宝","flag":1,"channel":1,"status":1,"create_time":1490344583000,"update_time":1490344583000},{"wallet_channel_id":4,"user_id":279307,"account":"15879101703","account_name":"支付宝","flag":0,"channel":1,"status":1,"create_time":1490346570000,"update_time":1490346570000}]
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
         * wallet_channel_id : 2
         * user_id : 279307
         * account : 15879101702
         * account_name : 支付宝
         * flag : 1
         * channel : 1
         * status : 1
         * create_time : 1490344583000
         * update_time : 1490344583000
         */

        private long wallet_channel_id;
        private long user_id;
        private String account;
        private String account_name;
        private int flag;
        private int channel;
        private int status;
        private long create_time;
        private long update_time;

        public long getWallet_channel_id() {
            return wallet_channel_id;
        }

        public void setWallet_channel_id(long wallet_channel_id) {
            this.wallet_channel_id = wallet_channel_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
}
