package io.jpress.core.addon;

import com.jfinal.kit.PathKit;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 插件工具类
 */
public class AddonUtil {

    /**
     * 解压 zip 或者 jar 的资源文件
     *
     * @param addonFile
     * @throws IOException
     */
    public static void unzipResources(File addonFile) throws IOException {
        ZipFile zipFile = new ZipFile(addonFile);
        try {
            Enumeration<?> entryEnum = zipFile.entries();
            if (null != entryEnum) {
                while (entryEnum.hasMoreElements()) {
                    OutputStream os = null;
                    InputStream is = null;
                    try {
                        ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
                        if (!zipEntry.isDirectory() && zipEntry.getName().startsWith("addons")) {
                            File targetFile = new File(PathKit.getWebRootPath() + File.separator + zipEntry.getName());
                            if (!targetFile.getParentFile().exists()) {
                                targetFile.getParentFile().mkdirs();
                            }
                            os = new BufferedOutputStream(new FileOutputStream(targetFile));
                            is = zipFile.getInputStream(zipEntry);
                            byte[] buffer = new byte[4096];
                            int readLen = 0;
                            while ((readLen = is.read(buffer, 0, 4096)) > 0) {
                                os.write(buffer, 0, readLen);
                            }
                        }
                    } finally {
                        if (is != null)
                            is.close();
                        if (os != null)
                            os.close();
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }

    public static AddonInfo readAddonInfo(File addonFile) {


        return null;
    }

    public static String readAddonId(File addonFile) {
        ZipFile zipFile = null;
        try {
            new ZipFile(addonFile);
            Enumeration<?> entryEnum = zipFile.entries();
            if (null != entryEnum) {
                while (entryEnum.hasMoreElements()) {
                    InputStream is = null;
                    try {
                        ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
                        if (StringUtils.equalsAnyIgnoreCase(zipEntry.getName(), "addon.txt", "addon.properties")) {
                            is = zipFile.getInputStream(zipEntry);
                            Properties properties = new Properties();
                            properties.load(new InputStreamReader(is, "utf-8"));
                            return (String) properties.get("id");
                        }
                    } finally {
                        if (is != null)
                            is.close();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zipFile.close();
            } catch (IOException e) {
            }
        }

        return null;
    }


}