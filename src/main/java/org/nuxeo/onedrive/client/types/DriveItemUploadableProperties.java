package org.nuxeo.onedrive.client.types;

import com.eclipsesource.json.JsonObject;

public class DriveItemUploadableProperties extends GraphType<DriveItemUploadableProperties> {
    private Long fileSize;
    private FileSystemInfo fileSystemInfo;
    private String name;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(final long fileSize) {
        this.fileSize = fileSize;
    }

    public FileSystemInfo getFileSystemInfo() {
        return fileSystemInfo;
    }

    public void setFileSystemInfo(final FileSystemInfo fileSystemInfo) {
        this.fileSystemInfo = fileSystemInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    protected void populateJsonObject(JsonObject jsonObject) {
        if (name != null) {
            jsonObject.set("name", name);
        }

        if (fileSize != null) {
            jsonObject.set("fileSize", fileSize);
        }

        if (fileSystemInfo != null) {
            jsonObject.set("fileSystemInfo", fileSystemInfo.toJson());
        }
    }
}
