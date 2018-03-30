package org.nuxeo.onedrive.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OneDriveItemBuilderTest extends OneDriveTestCase {
    /*
    Test Matrix
    +-----------------------+-------------------------+----------------------------------+
    |                       |       Drive Null        |         Drive Available          |
    +-----------------------+-------------------------+----------------------------------+
    | Folder ID/Path Null   | /drive/root             | /drives/DriveID/root             |
    | Folder Id Available   | /drive/items/FolderID   | /drives/DriveID/items/FolderID   |
    | Folder Path Available | /drive/root:/FolderPath | /drives/DriveID/root:/FolderPath |
    +-----------------------+-------------------------+----------------------------------+
     */
    @Test
    public void testDriveDefault() {
        OneDriveDrive drive = OneDriveDrive.getDefaultDrive(api);
        assertEquals("/v1.0/drive", drive.getMetadataUrl().build(api.getBaseURL()).getPath());
    }

    @Test
    public void testDriveId() {
        OneDriveDrive drive = new OneDriveDrive(api, "DRIVE");
        assertEquals("/v1.0/drives/DRIVE", drive.getMetadataUrl().build(api.getBaseURL()).getPath());
    }

    @Test
    public void testDriveNullFolderNull() {
        OneDriveFolder folder = new OneDriveFolder(api);
        assertEquals("/v1.0/drive/root", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drive/root/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testDriveNullFolderId() {
        OneDriveFolder folder = new OneDriveFolder(api, "FOLDERID");
        assertEquals("/v1.0/drive/items/FOLDERID", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drive/items/FOLDERID/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testDriveNullFolderPath() {
        OneDriveFolder folder = new OneDriveFolder(api, "FOLDERPATH", OneDriveResource.ResourceIdentifierType.Path);
        assertEquals("/v1.0/drive/root:/FOLDERPATH", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drive/root:/FOLDERPATH:/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testDriveFolderNull() {
        OneDriveDrive testDrive = new OneDriveDrive(api, "DRIVEID");
        OneDriveFolder folder = new OneDriveFolder(api, testDrive);
        assertEquals("/v1.0/drives/DRIVEID/root", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drives/DRIVEID/root/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testDriveFolderId() {
        OneDriveDrive testDrive = new OneDriveDrive(api, "DRIVEID");
        OneDriveFolder folder = new OneDriveFolder(api, testDrive, "FOLDERID", OneDriveResource.ResourceIdentifierType.Id);
        assertEquals("/v1.0/drives/DRIVEID/items/FOLDERID", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drives/DRIVEID/items/FOLDERID/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testDriveFolderPath() {
        OneDriveDrive testDrive = new OneDriveDrive(api, "DRIVEID");
        OneDriveFolder folder = new OneDriveFolder(api, testDrive, "FOLDERPATH");
        assertEquals("/v1.0/drives/DRIVEID/root:/FOLDERPATH", folder.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drives/DRIVEID/root:/FOLDERPATH:/children", folder.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }

    @Test
    public void testParent() {
        OneDriveDrive testDrive = new OneDriveDrive(api, "DRIVEID");
        OneDriveFolder folder = new OneDriveFolder(api, testDrive, "FOLDERID", OneDriveResource.ResourceIdentifierType.Id);
        OneDriveFolder child = new OneDriveFolder(api,testDrive, folder, "Test");
        assertEquals("/v1.0/drives/DRIVEID/items/FOLDERID:/Test", child.getMetadataURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
        assertEquals("/v1.0/drives/DRIVEID/items/FOLDERID:/Test:/children", child.getChildrenURL().build(api.getBaseURL(), folder.getResourceIdentifier()).getPath());
    }
}
