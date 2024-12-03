package com.lightlogistics.warehouse.service.scanner;


import com.lightlogistics.warehouse.model.scanner.Scanner;
import com.lightlogistics.warehouse.repository.scanner.ScannerRepository;
import org.springframework.stereotype.Service;

@Service
public class ScannerService {

    public final ScannerRepository scannerRepository;

    public ScannerService(ScannerRepository scannerRepository) {
        this.scannerRepository = scannerRepository;
    }

    public Scanner scan (Scanner scanner) {
        return scannerRepository.
    }

}
