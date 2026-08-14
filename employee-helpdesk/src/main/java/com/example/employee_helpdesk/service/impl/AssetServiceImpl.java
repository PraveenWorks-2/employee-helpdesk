package com.example.employee_helpdesk.service.impl;

import com.example.employee_helpdesk.dto.AssetRequestDto;
import com.example.employee_helpdesk.dto.AssetResponseDto;
import com.example.employee_helpdesk.entity.Asset;
import com.example.employee_helpdesk.enums.Statuses;
import com.example.employee_helpdesk.mapper.AssetMapper;
import com.example.employee_helpdesk.repository.AssetRepository;
import com.example.employee_helpdesk.service.AssetService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl  implements AssetService {

    private final AssetRepository assetRepository;


    @Override
    public AssetResponseDto createAsset(AssetRequestDto assetRequestDto) {
        Asset asset= AssetMapper.mapToAsset(assetRequestDto);
        asset.setStatus(Statuses.AVAILABLE);
        asset.setPurchaseDate(LocalDate.now());
        asset.setCreatedAt(LocalDate.now());
        asset.setUpdatedAt(LocalDate.now());
       Asset savedAsset= assetRepository.save(asset);
        return AssetMapper.mapToAssetResponseDto(savedAsset);
    }

    @Override
    public List<AssetResponseDto> getAllAssets() {
    	
        List<Asset> assets = assetRepository.findByIsDeletedFalse();

        return assets.stream()
                .map(AssetMapper::mapToAssetResponseDto)
                .toList();
      
    } 

    @Override
    public AssetResponseDto getAssetById(Long id) {
    	
    	 Asset asset = assetRepository.findByIdAndIsDeletedFalse(id)
    	            .orElseThrow(() -> new RuntimeException("Asset not found..!!"));

    	    return AssetMapper.mapToAssetResponseDto(asset);
    	
    }

    @Override
    public AssetResponseDto updateAsset(Long id,AssetRequestDto assetRequestDto) {

        Asset asset=assetRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Asset not found..!!"));
        asset.setAssetCode(assetRequestDto.getAssetCode());
        asset.setName(assetRequestDto.getName());
        asset.setCategory(assetRequestDto.getCategory());
        asset.setSerialNumber(assetRequestDto.getSerialNumber());
        asset.setPurchaseDate(assetRequestDto.getPurchaseDate());
        asset.setStatus(assetRequestDto.getStatus());
        asset.setUpdatedAt(LocalDate.now());
        Asset updatedAsset=assetRepository.save(asset);
        return AssetMapper.mapToAssetResponseDto(updatedAsset);
    }

    @Override
    public void deleteAsset(Long id) {
        Asset asset=assetRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Asset not found..!!"));
        asset.setIsDeleted(Boolean.TRUE);
        asset.setUpdatedAt(LocalDate.now());
        assetRepository.save(asset);
    }
}
