package com.example.reimbursementonlinebackend.util;

import com.example.reimbursementonlinebackend.service.dto.RequestDownloadDTO;
import com.example.reimbursementonlinebackend.service.dto.ResponseUpload;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class UploadUtil {

    private String minioHost;
    private String minioPort;
    private String minioSecure;
    private String minioAccessKey;
    private String minioSecretKey;

    private VaultTemplate vaultTemplate;

    public UploadUtil(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;

        VaultResponse vault = this.vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .get("eReimbursement");
        if (null != vault && null != vault.getData()) {
            if (null != vault.getData().get("minio.host"))
                this.minioHost = String.valueOf(vault.getData().get("minio.host"));
            if (null != vault.getData().get("minio.port"))
                this.minioPort = String.valueOf(vault.getData().get("minio.port"));
            if (null != vault.getData().get("minio.secure"))
                this.minioSecure = String.valueOf(vault.getData().get("minio.secure"));
            if (null != vault.getData().get("minio.access.key"))
                this.minioAccessKey = String.valueOf(vault.getData().get("minio.access.key"));
            if (null != vault.getData().get("minio.secret.key"))
                this.minioSecretKey = String.valueOf(vault.getData().get("minio.secret.key"));
        }
    }

    public ResponseUpload uploadFile(MultipartFile file, String type) {

        ResponseUpload response = new ResponseUpload();

        try {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            log.info("START MINIO");
            // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioHost, Integer.parseInt(minioPort), Boolean.parseBoolean(minioSecure))
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();
            log.info("INITIALIZED MINIO");

            UUID uuid = UUID.randomUUID();
            String newFileName = uuid.toString() + "." + ext;
            String resultUrl = (null == type || type.equals("")) ? "reimbursement" : "reimbursement" + "." + type;

            log.info("START UPLOAD MINIO");
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(resultUrl)
                            .object(newFileName)
                            .stream(file.getInputStream(), -1, 10485760)
                            .contentType(file.getContentType())
                            .build());

            response.setStatus("OK");
            response.setUrl(minioClient.getObjectUrl(resultUrl, newFileName));
            response.setBucket(resultUrl);
            response.setNameOriginal(file.getOriginalFilename());
            response.setFileName(newFileName);

            log.info("DONE UPLOAD MINIO");
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setUrl(null);
            log.error(e.getLocalizedMessage());
        }

        return response;
    }

    public void downloadFile(HttpServletResponse response, RequestDownloadDTO dto) {

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioHost, Integer.parseInt(minioPort), Boolean.parseBoolean(minioSecure))
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(dto.getBucket())
                            .object(dto.getFileName())
                            .build()
            );

            response.setHeader("Content-Disposition", "attachment;filename=" + dto.getNameOriginal());
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
