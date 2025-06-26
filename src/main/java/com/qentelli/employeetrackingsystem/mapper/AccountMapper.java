//package com.qentelli.employeetrackingsystem.mapper;
//
//import java.util.stream.Collectors;
//
//import com.qentelli.employeetrackingsystem.entity.Account;
//import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
//
//public class AccountMapper {
//
//	private AccountMapper() {
//        throw new UnsupportedOperationException("Utility class");
//    }
//	
//	// DTO → Account Entity
//    public static Account toEntity(AccountDetailsDto dto) {
//        Account account = new Account();
//        account.setAccountName(dto.getAccountName());
//        account.setAccountStartDate(dto.getAccountStartDate());
//        account.setAccountEndDate(dto.getAccountEndDate());
//        account.setCreatedAt(dto.getCreatedAt());
//        account.setCreatedBy(dto.getCreatedBy());
//        account.setUpdatedAt(dto.getUpdatedAt());
//        account.setUpdatedBy(dto.getUpdatedBy());
//        account.setProjects(dto.getProjects());
//       
//        
//        return account;
//    }
//
//    // Account Entity → DTO
//    public static AccountDetailsDto toDto(Account entity) {
//        AccountDetailsDto dto = new AccountDetailsDto();
//        dto.setAccountName(entity.getAccountName());
//        dto.setAccountStartDate(entity.getAccountStartDate());
//        dto.setAccountEndDate(entity.getAccountEndDate());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setCreatedBy(entity.getCreatedBy());
//        dto.setUpdatedAt(entity.getUpdatedAt());
//        dto.setUpdatedBy(entity.getUpdatedBy());
//        dto.setProjects(entity.getProjects());
////        if (entity.getProjects() != null) {
////			dto.setProjects(entity.getProjects().stream()
////					.map(ModelMappers::toDto)
////					.collect(Collectors.toList()));
////		}
//        
//        return dto;
//    }
//}
//
