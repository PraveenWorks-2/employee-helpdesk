package com.example.employee_helpdesk.service;

import com.example.employee_helpdesk.dto.AssetRequestDto;
import com.example.employee_helpdesk.dto.AssetResponseDto;

import java.util.List;

public interface AssetService {

    AssetResponseDto createAsset(AssetRequestDto assetRequestDto);

    List<AssetResponseDto> getAllAssets();

    AssetResponseDto getAssetById(Long id);

    AssetResponseDto updateAsset(Long id, AssetRequestDto assetRequestDto);

    void deleteAsset(Long id);

}
