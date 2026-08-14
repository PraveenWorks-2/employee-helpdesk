package com.example.employee_helpdesk.mapper;

import com.example.employee_helpdesk.dto.AssetRequestDto;
import com.example.employee_helpdesk.dto.AssetResponseDto;
import com.example.employee_helpdesk.entity.Asset;

import java.time.LocalDate;



public class AssetMapper {

    public  static AssetResponseDto mapToAssetResponseDto(Asset asset){
        return  new AssetResponseDto(
                asset.getId(),
                asset.getAssetCode(),
                asset.getName(),
                asset.getCategory(),
                asset.getSerialNumber(),
                asset.getStatus(),
                asset.getPurchaseDate(),
                asset.getCreatedAt(),
                asset.getUpdatedAt()
        );
    }

    public  static Asset mapToAsset(AssetRequestDto assetRequestDto){
        return new Asset(
                null,
                assetRequestDto.getAssetCode(),
                assetRequestDto.getName(),
                assetRequestDto.getCategory(),
                assetRequestDto.getSerialNumber(),
                assetRequestDto.getPurchaseDate(),
                assetRequestDto.getStatus(),
                LocalDate.now(),
                LocalDate.now(),
                false
        );
    }
}
