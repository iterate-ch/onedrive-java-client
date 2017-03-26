package org.nuxeo.onedrive.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OneDriveBuilderTest extends OneDriveTestCase {
    /*
    Test Matrix
    +-----------------------+-------------------------+----------------------------------+
    |                       |       Drive Null        |         Drive Available          |
    +-----------------------+-------------------------+----------------------------------+
    | Folder ID/Path Null   | /drive                  | /drives/DriveID/root             |
    | Folder Id Available   | /drive/items/FolderID   | /drives/DriveID/items/FolderID   |
    | Folder Path Available | /drive/root:/FolderPath | /drives/DriveID/root:/FolderPath |
    +-----------------------+-------------------------+----------------------------------+
     */

    @Test
    public void testDriveNullFolderNull() {
        assertEquals("/drive", OneDriveBuilder.createRequest().root().resourcePath());
        assertEquals("/drive/root", OneDriveBuilder.createRequest().root().rootPath());
        assertEquals("/drive/root/children", OneDriveBuilder.createRequest().root().childrenPath());
    }

    @Test
    public void testDriveNullFolderId() {
        assertEquals("/drive/items/FOLDERID", OneDriveBuilder.createRequest().root().navigateId("FOLDERID").resourcePath());
        assertEquals("/drive/items/FOLDERID/children", OneDriveBuilder.createRequest().root().navigateId("FOLDERID").childrenPath());
    }

    @Test
    public void testDriveNullFolderPath() {
        assertEquals("/drive/root:/FOLDERPATH", OneDriveBuilder.createRequest().root().navigatePath("FOLDERPATH").resourcePath());
        assertEquals("/drive/root:/FOLDERPATH:/children", OneDriveBuilder.createRequest().root().navigatePath("FOLDERPATH").childrenPath());
    }

    @Test
    public void testDriveFolderNull() {
        assertEquals("/drives/DRIVEID", OneDriveBuilder.createRequest().setDrive("DRIVEID").resourcePath());
        assertEquals("/drives/DRIVEID/root", OneDriveBuilder.createRequest().setDrive("DRIVEID").rootPath());
        assertEquals("/drives/DRIVEID/root/children", OneDriveBuilder.createRequest().setDrive("DRIVEID").childrenPath());
    }

    @Test
    public void testDriveFolderId() {
        assertEquals("/drives/DRIVEID/items/FOLDERID", OneDriveBuilder.createRequest().setDrive("DRIVEID").navigateId("FOLDERID").resourcePath());
        assertEquals("/drives/DRIVEID/items/FOLDERID/children", OneDriveBuilder.createRequest().setDrive("DRIVEID").navigateId("FOLDERID").childrenPath());
    }

    @Test
    public void testDriveFolderPath() {
        assertEquals("/drives/DRIVEID/root:/FOLDERPATH", OneDriveBuilder.createRequest().setDrive("DRIVEID").navigatePath("FOLDERPATH").resourcePath());
        assertEquals("/drives/DRIVEID/root:/FOLDERPATH:/children", OneDriveBuilder.createRequest().setDrive("DRIVEID").navigatePath("FOLDERPATH").childrenPath());
    }
}
