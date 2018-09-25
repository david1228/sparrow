package com.zoo.sparrow.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by David.Liu on 17/9/8.
 */
public class MultiMapTest {

    public static void main(String[] args) {
        Multimap<String, PayMode> listMultimap = ArrayListMultimap.create();

        for (int i = 0; i < 2; i++) {
            List<PayMode> payModes = Lists.newArrayList();
            listMultimap.put("微信", new PayMode("payType" + i, "wx" + i, "desc" + i));
        }

        for (int i = 0; i < 2; i++) {
            List<PayMode> payModes = Lists.newArrayList();
            listMultimap.put("支付宝", new PayMode("alipay" + i, "ali" + i, "alidesc" + i));
        }

        System.out.println(listMultimap.get("微信").getClass());
        System.out.println(listMultimap.get("支付宝"));
        // key不存在返回空集合[]
        System.out.println(listMultimap.get("keynull"));

        Map<String, Collection<PayMode>> map = listMultimap.asMap();
        // key不存在返回null
        System.out.println(map.get("keynull"));

        Object obj = listMultimap;

        System.out.println(obj.getClass());

    }

    private static class PayMode {
        String payType;
        String name;
        String desc;

        public PayMode(String payType, String name, String desc) {
            this.payType = payType;
            this.name = name;
            this.desc = desc;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override public String toString() {
            return "PayMode{" + "payType='" + payType + '\'' + ", name='" + name + '\'' + ", desc='" + desc + '\'' + '}';
        }
    }
}
