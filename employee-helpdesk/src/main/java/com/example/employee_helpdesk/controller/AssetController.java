package com.example.employee_helpdesk.controller;


import com.example.employee_helpdesk.dto.AssetRequestDto;
import com.example.employee_helpdesk.dto.AssetResponseDto;
import com.example.employee_helpdesk.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {


    private final AssetService assetService;

    
    @PostMapping
    public ResponseEntity<AssetResponseDto> createAsset(@RequestBody AssetRequestDto assetRequestDto){
    	System.out.println("Hi");      AssetResponseDto asset = assetService.createAsset(assetRequestDto);
      return new ResponseEntity<>(asset, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAssetById(@PathVariable("id") Long id){
      AssetResponseDto assetResponseDto=  assetService.getAssetById(id);
      return ResponseEntity.ok(assetResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDto>> getAllAssets(){
      List<AssetResponseDto> allAssets=  assetService.getAllAssets();
        return ResponseEntity.ok(allAssets);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<AssetResponseDto> updateAsset(@PathVariable("id") Long id, @RequestBody AssetRequestDto assetRequestDto){
       AssetResponseDto updatedAsset= assetService.updateAsset(id,assetRequestDto);
       return ResponseEntity.ok(updatedAsset);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable("id") Long id){
        assetService.deleteAsset(id);
        return ResponseEntity.ok("Asset deleted successfully..!!");
    }
}
