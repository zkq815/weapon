package com.zkq.weapon.base;

import java.util.List;

/**
 * @author zkq
 * time: 2019/11/7 1:50 PM
 * email: zkq815@126.com
 * desc:
 */
public class LevelViewBean {

    /**
     * plateType : {"plateCode":"2020880715","status":"","name":"华为产业链","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"3","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":"","img":"","parent":"","childList":"","plateChainName":"","plateRelaList":""}
     * children :
     * chain : [{"plateType":{"plateCode":"2020880753","status":"","name":"测试中间层3","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"12","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"","plateRelaList":""},"children":[{"plateCode":"2020880752","status":"","name":"测试三级","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"6","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"华为产业链,测试中间层3,测试三级","plateRelaList":""}],"chain":"","showH5":"","skipUrl":""},{"plateType":{"plateCode":"2020880755","status":"","name":"测试中间层4","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"12","startDate":"","isusable":"1","digest":"33","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"","plateRelaList":""},"children":[{"plateCode":"2020880754","status":"","name":"测试三级3","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"6","startDate":"","isusable":"1","digest":"3333","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":3,"img":"","parent":"","childList":"","plateChainName":"华为产业链,测试中间层4,测试三级3","plateRelaList":""}],"chain":"","showH5":"","skipUrl":""},{"plateType":{"plateCode":"2020880758","status":"","name":"洗衣机中间层","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"12","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"","plateRelaList":""},"children":[{"plateCode":"2020880757","status":"","name":"洗衣机三级节点","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"6","startDate":"","isusable":"1","digest":"ghgj gjf ","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"华为产业链,洗衣机中间层,洗衣机三级节点","plateRelaList":""}],"chain":"","showH5":"","skipUrl":""}]
     * showH5 : false
     * skipUrl :
     */

    private PlateTypeBean plateType;
    private String children;
    private boolean showH5;
    private String skipUrl;
    private List<ChainBean> chain;

    public PlateTypeBean getPlateType() {
        return plateType;
    }

    public void setPlateType(PlateTypeBean plateType) {
        this.plateType = plateType;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public boolean isShowH5() {
        return showH5;
    }

    public void setShowH5(boolean showH5) {
        this.showH5 = showH5;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public List<ChainBean> getChain() {
        return chain;
    }

    public void setChain(List<ChainBean> chain) {
        this.chain = chain;
    }

    public static class PlateTypeBean {
        /**
         * plateCode : 2020880715
         * status :
         * name : 华为产业链
         * thsCode :
         * thsName :
         * dfcfCode :
         * dfcfName :
         * xgbCode :
         * xgbName :
         * type : 3
         * startDate :
         * isusable : 1
         * digest :
         * securityTypeCode : 058001001
         * hypeList :
         * industryList :
         * parentName :
         * parentId :
         * editor :
         * depth :
         * isDfcfConfig : 0
         * isXgbConfig : 0
         * location :
         * img :
         * parent :
         * childList :
         * plateChainName :
         * plateRelaList :
         */

        private String plateCode;
        private String status;
        private String name;
        private String thsCode;
        private String thsName;
        private String dfcfCode;
        private String dfcfName;
        private String xgbCode;
        private String xgbName;
        private String type;
        private String startDate;
        private String isusable;
        private String digest;
        private String securityTypeCode;
        private String hypeList;
        private String industryList;
        private String parentName;
        private String parentId;
        private String editor;
        private String depth;
        private String isDfcfConfig;
        private String isXgbConfig;
        private String location;
        private String img;
        private String parent;
        private String childList;
        private String plateChainName;
        private String plateRelaList;

        public String getPlateCode() {
            return plateCode;
        }

        public void setPlateCode(String plateCode) {
            this.plateCode = plateCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThsCode() {
            return thsCode;
        }

        public void setThsCode(String thsCode) {
            this.thsCode = thsCode;
        }

        public String getThsName() {
            return thsName;
        }

        public void setThsName(String thsName) {
            this.thsName = thsName;
        }

        public String getDfcfCode() {
            return dfcfCode;
        }

        public void setDfcfCode(String dfcfCode) {
            this.dfcfCode = dfcfCode;
        }

        public String getDfcfName() {
            return dfcfName;
        }

        public void setDfcfName(String dfcfName) {
            this.dfcfName = dfcfName;
        }

        public String getXgbCode() {
            return xgbCode;
        }

        public void setXgbCode(String xgbCode) {
            this.xgbCode = xgbCode;
        }

        public String getXgbName() {
            return xgbName;
        }

        public void setXgbName(String xgbName) {
            this.xgbName = xgbName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getIsusable() {
            return isusable;
        }

        public void setIsusable(String isusable) {
            this.isusable = isusable;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getSecurityTypeCode() {
            return securityTypeCode;
        }

        public void setSecurityTypeCode(String securityTypeCode) {
            this.securityTypeCode = securityTypeCode;
        }

        public String getHypeList() {
            return hypeList;
        }

        public void setHypeList(String hypeList) {
            this.hypeList = hypeList;
        }

        public String getIndustryList() {
            return industryList;
        }

        public void setIndustryList(String industryList) {
            this.industryList = industryList;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getIsDfcfConfig() {
            return isDfcfConfig;
        }

        public void setIsDfcfConfig(String isDfcfConfig) {
            this.isDfcfConfig = isDfcfConfig;
        }

        public String getIsXgbConfig() {
            return isXgbConfig;
        }

        public void setIsXgbConfig(String isXgbConfig) {
            this.isXgbConfig = isXgbConfig;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getChildList() {
            return childList;
        }

        public void setChildList(String childList) {
            this.childList = childList;
        }

        public String getPlateChainName() {
            return plateChainName;
        }

        public void setPlateChainName(String plateChainName) {
            this.plateChainName = plateChainName;
        }

        public String getPlateRelaList() {
            return plateRelaList;
        }

        public void setPlateRelaList(String plateRelaList) {
            this.plateRelaList = plateRelaList;
        }
    }

    public static class ChainBean {
        /**
         * plateType : {"plateCode":"2020880753","status":"","name":"测试中间层3","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"12","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"","plateRelaList":""}
         * children : [{"plateCode":"2020880752","status":"","name":"测试三级","thsCode":"","thsName":"","dfcfCode":"","dfcfName":"","xgbCode":"","xgbName":"","type":"6","startDate":"","isusable":"1","digest":"","securityTypeCode":"058001001","hypeList":"","industryList":"","parentName":"","parentId":"","editor":"1","depth":"","isDfcfConfig":"0","isXgbConfig":"0","location":1,"img":"","parent":"","childList":"","plateChainName":"华为产业链,测试中间层3,测试三级","plateRelaList":""}]
         * chain :
         * showH5 :
         * skipUrl :
         */

        private PlateTypeBeanX plateType;
        private String chain;
        private String showH5;
        private String skipUrl;
        private List<ChildrenBean> children;

        public PlateTypeBeanX getPlateType() {
            return plateType;
        }

        public void setPlateType(PlateTypeBeanX plateType) {
            this.plateType = plateType;
        }

        public String getChain() {
            return chain;
        }

        public void setChain(String chain) {
            this.chain = chain;
        }

        public String getShowH5() {
            return showH5;
        }

        public void setShowH5(String showH5) {
            this.showH5 = showH5;
        }

        public String getSkipUrl() {
            return skipUrl;
        }

        public void setSkipUrl(String skipUrl) {
            this.skipUrl = skipUrl;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class PlateTypeBeanX {
            /**
             * plateCode : 2020880753
             * status :
             * name : 测试中间层3
             * thsCode :
             * thsName :
             * dfcfCode :
             * dfcfName :
             * xgbCode :
             * xgbName :
             * type : 12
             * startDate :
             * isusable : 1
             * digest :
             * securityTypeCode : 058001001
             * hypeList :
             * industryList :
             * parentName :
             * parentId :
             * editor : 1
             * depth :
             * isDfcfConfig : 0
             * isXgbConfig : 0
             * location : 1
             * img :
             * parent :
             * childList :
             * plateChainName :
             * plateRelaList :
             */

            private String plateCode;
            private String status;
            private String name;
            private String thsCode;
            private String thsName;
            private String dfcfCode;
            private String dfcfName;
            private String xgbCode;
            private String xgbName;
            private String type;
            private String startDate;
            private String isusable;
            private String digest;
            private String securityTypeCode;
            private String hypeList;
            private String industryList;
            private String parentName;
            private String parentId;
            private String editor;
            private String depth;
            private String isDfcfConfig;
            private String isXgbConfig;
            private int location;
            private String img;
            private String parent;
            private String childList;
            private String plateChainName;
            private String plateRelaList;

            public String getPlateCode() {
                return plateCode;
            }

            public void setPlateCode(String plateCode) {
                this.plateCode = plateCode;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThsCode() {
                return thsCode;
            }

            public void setThsCode(String thsCode) {
                this.thsCode = thsCode;
            }

            public String getThsName() {
                return thsName;
            }

            public void setThsName(String thsName) {
                this.thsName = thsName;
            }

            public String getDfcfCode() {
                return dfcfCode;
            }

            public void setDfcfCode(String dfcfCode) {
                this.dfcfCode = dfcfCode;
            }

            public String getDfcfName() {
                return dfcfName;
            }

            public void setDfcfName(String dfcfName) {
                this.dfcfName = dfcfName;
            }

            public String getXgbCode() {
                return xgbCode;
            }

            public void setXgbCode(String xgbCode) {
                this.xgbCode = xgbCode;
            }

            public String getXgbName() {
                return xgbName;
            }

            public void setXgbName(String xgbName) {
                this.xgbName = xgbName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getIsusable() {
                return isusable;
            }

            public void setIsusable(String isusable) {
                this.isusable = isusable;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getSecurityTypeCode() {
                return securityTypeCode;
            }

            public void setSecurityTypeCode(String securityTypeCode) {
                this.securityTypeCode = securityTypeCode;
            }

            public String getHypeList() {
                return hypeList;
            }

            public void setHypeList(String hypeList) {
                this.hypeList = hypeList;
            }

            public String getIndustryList() {
                return industryList;
            }

            public void setIndustryList(String industryList) {
                this.industryList = industryList;
            }

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getEditor() {
                return editor;
            }

            public void setEditor(String editor) {
                this.editor = editor;
            }

            public String getDepth() {
                return depth;
            }

            public void setDepth(String depth) {
                this.depth = depth;
            }

            public String getIsDfcfConfig() {
                return isDfcfConfig;
            }

            public void setIsDfcfConfig(String isDfcfConfig) {
                this.isDfcfConfig = isDfcfConfig;
            }

            public String getIsXgbConfig() {
                return isXgbConfig;
            }

            public void setIsXgbConfig(String isXgbConfig) {
                this.isXgbConfig = isXgbConfig;
            }

            public int getLocation() {
                return location;
            }

            public void setLocation(int location) {
                this.location = location;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getParent() {
                return parent;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public String getChildList() {
                return childList;
            }

            public void setChildList(String childList) {
                this.childList = childList;
            }

            public String getPlateChainName() {
                return plateChainName;
            }

            public void setPlateChainName(String plateChainName) {
                this.plateChainName = plateChainName;
            }

            public String getPlateRelaList() {
                return plateRelaList;
            }

            public void setPlateRelaList(String plateRelaList) {
                this.plateRelaList = plateRelaList;
            }
        }

        public static class ChildrenBean {
            /**
             * plateCode : 2020880752
             * status :
             * name : 测试三级
             * thsCode :
             * thsName :
             * dfcfCode :
             * dfcfName :
             * xgbCode :
             * xgbName :
             * type : 6
             * startDate :
             * isusable : 1
             * digest :
             * securityTypeCode : 058001001
             * hypeList :
             * industryList :
             * parentName :
             * parentId :
             * editor : 1
             * depth :
             * isDfcfConfig : 0
             * isXgbConfig : 0
             * location : 1
             * img :
             * parent :
             * childList :
             * plateChainName : 华为产业链,测试中间层3,测试三级
             * plateRelaList :
             */

            private String plateCode;
            private String status;
            private String name;
            private String thsCode;
            private String thsName;
            private String dfcfCode;
            private String dfcfName;
            private String xgbCode;
            private String xgbName;
            private String type;
            private String startDate;
            private String isusable;
            private String digest;
            private String securityTypeCode;
            private String hypeList;
            private String industryList;
            private String parentName;
            private String parentId;
            private String editor;
            private String depth;
            private String isDfcfConfig;
            private String isXgbConfig;
            private int location;
            private String img;
            private String parent;
            private String childList;
            private String plateChainName;
            private String plateRelaList;

            public String getPlateCode() {
                return plateCode;
            }

            public void setPlateCode(String plateCode) {
                this.plateCode = plateCode;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThsCode() {
                return thsCode;
            }

            public void setThsCode(String thsCode) {
                this.thsCode = thsCode;
            }

            public String getThsName() {
                return thsName;
            }

            public void setThsName(String thsName) {
                this.thsName = thsName;
            }

            public String getDfcfCode() {
                return dfcfCode;
            }

            public void setDfcfCode(String dfcfCode) {
                this.dfcfCode = dfcfCode;
            }

            public String getDfcfName() {
                return dfcfName;
            }

            public void setDfcfName(String dfcfName) {
                this.dfcfName = dfcfName;
            }

            public String getXgbCode() {
                return xgbCode;
            }

            public void setXgbCode(String xgbCode) {
                this.xgbCode = xgbCode;
            }

            public String getXgbName() {
                return xgbName;
            }

            public void setXgbName(String xgbName) {
                this.xgbName = xgbName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getIsusable() {
                return isusable;
            }

            public void setIsusable(String isusable) {
                this.isusable = isusable;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getSecurityTypeCode() {
                return securityTypeCode;
            }

            public void setSecurityTypeCode(String securityTypeCode) {
                this.securityTypeCode = securityTypeCode;
            }

            public String getHypeList() {
                return hypeList;
            }

            public void setHypeList(String hypeList) {
                this.hypeList = hypeList;
            }

            public String getIndustryList() {
                return industryList;
            }

            public void setIndustryList(String industryList) {
                this.industryList = industryList;
            }

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getEditor() {
                return editor;
            }

            public void setEditor(String editor) {
                this.editor = editor;
            }

            public String getDepth() {
                return depth;
            }

            public void setDepth(String depth) {
                this.depth = depth;
            }

            public String getIsDfcfConfig() {
                return isDfcfConfig;
            }

            public void setIsDfcfConfig(String isDfcfConfig) {
                this.isDfcfConfig = isDfcfConfig;
            }

            public String getIsXgbConfig() {
                return isXgbConfig;
            }

            public void setIsXgbConfig(String isXgbConfig) {
                this.isXgbConfig = isXgbConfig;
            }

            public int getLocation() {
                return location;
            }

            public void setLocation(int location) {
                this.location = location;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getParent() {
                return parent;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public String getChildList() {
                return childList;
            }

            public void setChildList(String childList) {
                this.childList = childList;
            }

            public String getPlateChainName() {
                return plateChainName;
            }

            public void setPlateChainName(String plateChainName) {
                this.plateChainName = plateChainName;
            }

            public String getPlateRelaList() {
                return plateRelaList;
            }

            public void setPlateRelaList(String plateRelaList) {
                this.plateRelaList = plateRelaList;
            }
        }
    }
}
