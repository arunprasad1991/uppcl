package com.xx.uppcl.pojo;

import java.io.Serializable;

public class AttachmentPojo implements Serializable {
    @SuppressWarnings("compatibility:-5333158922665239978")
    private static final long serialVersionUID = 1L;

    public AttachmentPojo() {
        super();
    }

    private String docId;
    private String docContentId;
    private String docIdentificationType;
    private String docContentType;
    private String docName;
    private String docExtension;
    private String docNameWithExt;

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocIdentificationType(String docIdentificationType) {
        this.docIdentificationType = docIdentificationType;
    }

    public String getDocIdentificationType() {
        return docIdentificationType;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocExtension(String docExtension) {
        this.docExtension = docExtension;
    }

    public String getDocExtension() {
        return docExtension;
    }

    public void setDocNameWithExt(String docNameWithExt) {
        this.docNameWithExt = docNameWithExt;
    }

    public String getDocNameWithExt() {
        return docNameWithExt;
    }

    public void setDocContentId(String docContentId) {
        this.docContentId = docContentId;
    }

    public String getDocContentId() {
        return docContentId;
    }
}
