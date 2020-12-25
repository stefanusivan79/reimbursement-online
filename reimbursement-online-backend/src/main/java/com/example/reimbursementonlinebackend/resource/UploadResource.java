package com.example.reimbursementonlinebackend.resource;

import com.example.reimbursementonlinebackend.service.dto.RequestDownloadDTO;
import com.example.reimbursementonlinebackend.service.dto.ResponseUpload;
import com.example.reimbursementonlinebackend.util.PDFUtil;
import com.example.reimbursementonlinebackend.util.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
public class UploadResource {

    private final UploadUtil uploadUtil;
    private final PDFUtil pdfUtil;

    public UploadResource(UploadUtil uploadUtil, PDFUtil pdfUtil) {
        this.uploadUtil = uploadUtil;
        this.pdfUtil = pdfUtil;
    }

    @PostMapping("/upload")
    public ResponseUpload uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {

        return uploadUtil.uploadFile(file, type);
    }

    @PostMapping("/download")
    public void downloadFile(HttpServletResponse response, @RequestBody RequestDownloadDTO dto) {
        uploadUtil.downloadFile(response, dto);
    }
}

