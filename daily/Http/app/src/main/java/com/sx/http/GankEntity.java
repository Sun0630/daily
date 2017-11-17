package com.sx.http;

import java.util.List;

/**
 * @author Administrator
 * @Date 2017/11/10 0010 下午 1:53
 * @Description
 */

public class GankEntity {

    /**
     * error : false
     * results : [{"_id":"5a03b502421aa90fe7253618","createdAt":"2017-11-09T09:53:06.802Z","desc":"11-9","publishedAt":"2017-11-10T08:10:02.838Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171109095254_dOw5qh_bluenamchu_9_11_2017_9_52_47_256.jpeg","used":true,"who":"daimajia"},{"_id":"5a011452421aa90fe7253606","createdAt":"2017-11-07T10:02:58.73Z","desc":"11-7","publishedAt":"2017-11-08T11:00:50.559Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171107100244_0fbENB_yyannwong_7_11_2017_10_2_5_982.jpeg","used":true,"who":"daimajia"},{"_id":"59fa7379421aa90fe50c01cc","createdAt":"2017-11-02T09:23:05.497Z","desc":"11-2","publishedAt":"2017-11-06T12:40:39.976Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171102092251_AY0l4b_alrisaa_2_11_2017_9_22_44_335.jpeg","used":true,"who":"daimajia"},{"_id":"59f9674c421aa90fe50c01c6","createdAt":"2017-11-01T14:18:52.937Z","desc":"11-1","publishedAt":"2017-11-01T14:20:59.209Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171101141835_yQYTXc_enakorin_1_11_2017_14_16_45_351.jpeg","used":true,"who":"daimajia"},{"_id":"59f7e677421aa90fe72535de","createdAt":"2017-10-31T10:56:55.988Z","desc":"10-31","publishedAt":"2017-10-31T12:25:55.217Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-10-31-nozomisasaki_official_31_10_2017_10_49_17_24.jpg","used":true,"who":"代码家"},{"_id":"59f2aabb421aa90fef2034d5","createdAt":"2017-10-27T11:40:43.793Z","desc":"10-27","publishedAt":"2017-10-27T12:02:30.376Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171027114026_v8VFwP_joanne_722_27_10_2017_11_40_17_370.jpeg","used":true,"who":"daimajia"},{"_id":"59f0054a421aa90fe2f02bf4","createdAt":"2017-10-25T11:30:18.697Z","desc":"2017-10-25","publishedAt":"2017-10-25T11:39:10.950Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171025112955_lmesMu_katyteiko_25_10_2017_11_29_43_270.jpeg","used":true,"who":"代码家"},{"_id":"59ee8adf421aa90fe50c019b","createdAt":"2017-10-24T08:35:43.61Z","desc":"10-24","publishedAt":"2017-10-24T11:50:49.1Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171024083526_Hq4gO6_bluenamchu_24_10_2017_8_34_28_246.jpeg","used":true,"who":"代码家"},{"_id":"59ed70a4421aa90fef2034bc","createdAt":"2017-10-23T12:31:32.639Z","desc":"10-23","publishedAt":"2017-10-23T12:44:23.660Z","source":"chrome","type":"福利","url":"https://img.gank.io/anri.kumaki_23_10_2017_12_27_30_151.jpg","used":true,"who":"代码家"},{"_id":"59e6aadf421aa90fef2034a0","createdAt":"2017-10-18T09:14:07.966Z","desc":"10-18","publishedAt":"2017-10-20T10:26:24.673Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171018091347_Z81Beh_nini.nicky_18_10_2017_9_13_35_727.jpeg","used":true,"who":"代码家"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5a03b502421aa90fe7253618
         * createdAt : 2017-11-09T09:53:06.802Z
         * desc : 11-9
         * publishedAt : 2017-11-10T08:10:02.838Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/20171109095254_dOw5qh_bluenamchu_9_11_2017_9_52_47_256.jpeg
         * used : true
         * who : daimajia
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
