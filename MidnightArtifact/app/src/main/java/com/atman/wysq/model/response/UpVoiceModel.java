package com.atman.wysq.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 17/4/7.
 */

public class UpVoiceModel {
    private List<FilesBean> files;

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        /**
         * name : files0_name_8537662815456873900.aac
         * type : aac
         * size : 0
         * successful : true
         * url : /imageServer/78D96aeb4ce9b3bc47d788d137aac939f62c.aac
         */

        private String name;
        private String type;
        private int size;
        private boolean successful;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
