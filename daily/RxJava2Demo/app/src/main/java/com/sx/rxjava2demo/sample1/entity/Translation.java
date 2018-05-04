package com.sx.rxjava2demo.sample1.entity;

import android.util.Log;

/**
 * @Author Administrator
 * @Date 2018/1/12 0012 上午 9:41
 * @Description
 */

public class Translation {

    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","out":"示例","vendor":"ciba","err_no":0}
     */

    private int status;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * from : en-EU
         * to : zh-CN
         * out : 示例
         * vendor : ciba
         * err_no : 0
         */

        private String from;
        private String to;
        private String out;
        private String vendor;
        private int err_no;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }
    }

    public void show() {
//        System.out.println( "Rxjava翻译结果：" + status);
//        System.out.println("Rxjava翻译结果：" + content.from);
//        System.out.println("Rxjava翻译结果：" + content.to);
//        System.out.println("Rxjava翻译结果：" + content.vendor);
//        System.out.println("Rxjava翻译结果：" + content.out);
        Log.e("MainActivity", "show: "+content.out );
//        System.out.println("Rxjava翻译结果：" + content.err_no);
    }
}
