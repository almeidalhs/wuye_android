package com.atman.wysq.model.event;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/25 16:07
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GiftEvent {

    public int GiftId;
    public int TypeId;
    public int add_money;

    public GiftEvent(int TypeId, int GiftId, int add_money) {
        this.GiftId = GiftId;
        this.TypeId = TypeId;
        this.add_money = add_money;
    }
}
