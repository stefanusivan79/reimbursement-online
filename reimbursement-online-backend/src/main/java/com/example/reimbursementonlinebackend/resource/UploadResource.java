package com.example.reimbursementonlinebackend.resource;

import com.example.reimbursementonlinebackend.service.dto.RequestDownloadDTO;
import com.example.reimbursementonlinebackend.service.dto.ResponseUpload;
import com.example.reimbursementonlinebackend.service.dto.TotalReimburseJasperDTO;
import com.example.reimbursementonlinebackend.util.PDFUtil;
import com.example.reimbursementonlinebackend.util.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @GetMapping("/test")
    public void downloadFiles() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("periode", "Jan 2021");
        params.put("logo", "http://localhost:9000/logo/logoExpenses.png");

        List<Object> list = new ArrayList<>();
        TotalReimburseJasperDTO dto = new TotalReimburseJasperDTO();
        dto.setFullName("Stefanus Ivan");
        dto.setTotal(BigDecimal.valueOf(10000000));
        list.add(dto);

        String fileName = "DOC_TRANSAKSI_BUKA_TABUNGAN_EMAS-" + new Date().getTime() + ".pdf";
        pdfUtil.generatePdfFileJasper(params, list, fileName, "ReportReimbursePerMonth.jasper");
    }
}

