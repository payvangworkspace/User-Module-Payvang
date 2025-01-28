package com.Payvang.Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Payvang.Login.Constants.DocumentNameConstants;
import com.Payvang.Login.Util.FileStorageUtility;

@Service
public class FileStorageService {

	@Autowired
	private FileStorageUtility fileStorageUtility;

	public void validateAndSaveFile(String businessType, String documentType, MultipartFile file, String fileName,
			String appId) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File is empty or not provided");
		}

		String fileExtension = getFileExtension(fileName);
		if (!isValidFileType(fileExtension)) {
			throw new IllegalArgumentException("Invalid file type. Allowed types are: .pdf, .jpg, .png");
		}

		switch (businessType) {
		case "PL":
			validateAndSavePLDocument(documentType, file, fileName, appId);
			break;
		case "PF":
			validateAndSavePFDocument(documentType, file, fileName, appId);
			break;
		case "PR":
			validateAndSavePRDocument(documentType, file, fileName, appId);
			break;
		case "CSA":
			validateAndSaveCSADocument(documentType, file, fileName, appId);
			break;
		case "LLL":
			validateAndSaveLLLDocument(documentType, file, fileName, appId);
			break;
		case "RI":
			validateAndSaveRIDocument(documentType, file, fileName, appId);
			break;
		case "AP":
			validateAndSaveAPDocument(documentType, file, fileName, appId);
			break;
		case "T":
			validateAndSaveTDocument(documentType, file, fileName, appId);
			break;
		default:
			throw new IllegalArgumentException("Invalid business type");
		}
	}

	private String getFileExtension(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	private boolean isValidFileType(String extension) {
		return extension.equals("pdf") || extension.equals("jpg") || extension.equals("png");
	}

	private void validateAndSavePLDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "ARTICLE_OF_ASSOCIATION":
			saveFile(DocumentNameConstants.ARTICLE_OF_ASSOCIATION.getName(), fileName, file, appId);
			break;
		case "MEMORANDUM_OF_ASSOCIATION":
			saveFile(DocumentNameConstants.MEMORANDUM_OF_ASSOCIATION.getName(), fileName, file, appId);
			break;
		case "CERTIFICATION_OF_INCORPORATION":
			saveFile(DocumentNameConstants.CERTIFICATION_OF_INCORPORATION.getName(), fileName, file, appId);
			break;

		case "CERTIFICATION_OF_COMMENCEMENT":
			saveFile(DocumentNameConstants.CERTIFICATION_OF_COMMENCEMENT.getName(), fileName, file, appId);
			break;
		case "PAN_OF_THE_COMPANY":
			saveFile(DocumentNameConstants.PAN_OF_THE_COMPANY.getName(), fileName, file, appId);
			break;
		case "IDENTIFICATION_DOCUMNETS":
			saveFile(DocumentNameConstants.IDENTIFICATION_DOCUMNETS.getName(), fileName, file, appId);
			break;
		case "BOARD_RESOLUTION":
			saveFile(DocumentNameConstants.BOARD_RESOLUTION.getName(), fileName, file, appId);
			break;
		case "LIST_PERSONAL_DETAILS":
			saveFile(DocumentNameConstants.LIST_PERSONAL_DETAILS.getName(), fileName, file, appId);
			break;
		case "ADDRESS_PROOFS":
			saveFile(DocumentNameConstants.ADDRESS_PROOFS.getName(), fileName, file, appId);
			break;
		case "BANK_STATEMENT":
			saveFile(DocumentNameConstants.BANK_STATEMENT.getName(), fileName, file, appId);
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for PL");
		}
	}

	private void validateAndSavePFDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "IDENTIFICATION_DOCUMNETS_ALLPARTNER":
			saveFile(DocumentNameConstants.IDENTIFICATION_DOCUMNETS_ALLPARTNER.getName(), fileName, file, appId);
			break;
		case "CERTIFIED_TRUE_COPY":
			saveFile(DocumentNameConstants.CERTIFIED_TRUE_COPY.getName(), fileName, file, appId);
			break;
		case "LIST_OF_PARTNER":
			saveFile(DocumentNameConstants.LIST_OF_PARTNER.getName(), fileName, file, appId);
			break;
		case "PARTNERSHIP_LETTER_SIGNED":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.PARTNERSHIP_LETTER_SIGNED.getName(), fileName, file, appId);
			}
			break;
		case "PAN_CARD":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.PAN_CARD.getName(), fileName, file, appId);
			}
			break;
		case "ADDRESS_PROOFS_PARTNERSHIP":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.ADDRESS_PROOFS_PARTNERSHIP.getName(), fileName, file, appId);
			}
			break;
		case "IDENTIFICATION_DOCUMNETS_AUTHORIZED":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.IDENTIFICATION_DOCUMNETS_AUTHORIZED.getName(), fileName, file, appId);
			}
			break;
		case "BANK_STATEMENT_PARTNERSHIP":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.BANK_STATEMENT_PARTNERSHIP.getName(), fileName, file, appId);
			}
			break;
		case "LICENSE_UNDER_SHOP":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.LICENSE_UNDER_SHOP.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for PF");
		}
	}

	private void validateAndSavePRDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "IDENTIFICATION_DOCUMNETS_PROPRIETOR":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.IDENTIFICATION_DOCUMNETS_PROPRIETOR.getName(), fileName, file, appId);
			}
			break;
		case "PAN_CARD_PROPRIETOR":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.PAN_CARD_PROPRIETOR.getName(), fileName, file, appId);
			}
			break;
		case "PROOF_OF_ENTITY":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.PROOF_OF_ENTITY.getName(), fileName, file, appId);
			}
			break;
		case "ADDRESS_PROOFS_PROPRIETOR":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.ADDRESS_PROOFS_PROPRIETOR.getName(), fileName, file, appId);
			}
			break;
		case "BANK_STATEMENT_PROPRIETOR":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.BANK_STATEMENT_PROPRIETOR.getName(), fileName, file, appId);
			}
			break;
		case "LICENSE_UNDER_SHOP_PROPRIETOR":
			if (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png")) {
				saveFile(DocumentNameConstants.LICENSE_UNDER_SHOP_PROPRIETOR.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for PR");
		}

	}

	private void validateAndSaveCSADocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "LAWS":
			saveFile(DocumentNameConstants.LAWS.getName(), fileName, file, appId);
			break;
		case "GENERAL_BODY_RESOLUTION":
			saveFile(DocumentNameConstants.GENERAL_BODY_RESOLUTION.getName(), fileName, file, appId);
			break;
		case "PAN_CARD_CLUB":
			saveFile(DocumentNameConstants.PAN_CARD_CLUB.getName(), fileName, file, appId);
			break;
		// Add other document types for "CSA" as necessary
		default:
			throw new IllegalArgumentException("Invalid document type for CSA");
		}
	}

	private void validateAndSaveLLLDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "REGISTRATION_CERTIFICATE":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.REGISTRATION_CERTIFICATE.getName(), fileName, file, appId);
			}
			break;
		case "LLPA_AGREEMENT":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.LLPA_AGREEMENT.getName(), fileName, file, appId);
			}
			break;
		
		case "LIST_OF_PARTNERS":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.LIST_OF_PARTNERS.getName(), fileName, file, appId);
			}
			break;
		case "IDENTIFICATION_DOCUMNETS_LLP":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.IDENTIFICATION_DOCUMNETS_LLP.getName(), fileName, file, appId);
			}
			break;
		case "AUTHORIZATION_LETTER":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.AUTHORIZATION_LETTER.getName(), fileName, file, appId);
			}
			break;
		case "PAN_CARD_COMPANY":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.PAN_CARD_COMPANY.getName(), fileName, file, appId);
			}
			break;
		case "DIN_REGISTRATION":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.DIN_REGISTRATION.getName(), fileName, file, appId);
			}
			break;
		case "ADDRESS_PROOFS_LLP":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.ADDRESS_PROOFS_LLP.getName(), fileName, file, appId);
			}
			break;
		case "BANK_STATEMENT_LLP":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.BANK_STATEMENT_LLP.getName(), fileName, file, appId);
			}
			break;
		case "PAN_OF_LLP":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.PAN_OF_LLP.getName(), fileName, file, appId);
			}
			break;
		case "LICENSE_UNDER_SHOP_LLP":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.LICENSE_UNDER_SHOP_LLP.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for LLL");
		}
	}

	private void validateAndSaveRIDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "PASSPORT":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.PASSPORT.getName(), fileName, file, appId);
			}
			break;
		case "PAN_CARD_RESIDENT":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.PAN_CARD_RESIDENT.getName(), fileName, file, appId);
			}
			break;
		case "DRIVING_LICENSE":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.DRIVING_LICENSE.getName(), fileName, file, appId);
			}
			break;
		case "BANKERS_VERIFICATION":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.BANKERS_VERIFICATION.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for RI");
		}
	}

	private void validateAndSaveAPDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "PAN_INTIMATION":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.PAN_INTIMATION.getName(), fileName, file, appId);
			}
			break;
		case "CURRENT_UTILITY_BILL":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.CURRENT_UTILITY_BILL.getName(), fileName, file, appId);
			}
			break;
		case "MUNICIPAL_TAX":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.MUNICIPAL_TAX.getName(), fileName, file, appId);
			}
			break;
		case "EXISTING_BANKSSTATEMENT":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.EXISTING_BANKSSTATEMENT.getName(), fileName, file, appId);
			}
			break;
		case "EXISTING_BANKS_CERTIFICATE":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.EXISTING_BANKS_CERTIFICATE.getName(), fileName, file, appId);
			}
			break;
		case "INSURANCE_POLICY":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.INSURANCE_POLICY.getName(), fileName, file, appId);
			}
			break;
		case "ANYOTHER_DOCUMENT":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.ANYOTHER_DOCUMENT.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for AP");
		}
	}

	private void validateAndSaveTDocument(String documentType, MultipartFile file, String fileName, String appId) {
		switch (documentType) {
		case "RESOLUTION_FROM_BOARD":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.RESOLUTION_FROM_BOARD.getName(), fileName, file, appId);
			}
			break;
		case "SIGNATURE_AND_PHOTO":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.SIGNATURE_AND_PHOTO.getName(), fileName, file, appId);
			}
			break;
		case "CERTIFICATE_ISSUED":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.CERTIFICATE_ISSUED.getName(), fileName, file, appId);
			}
			break;
		case "ATTESTED_COPY_DEED":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.ATTESTED_COPY_DEED.getName(), fileName, file, appId);
			}
			break;
		case "DUTY_ATTESTED_PAN":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.DUTY_ATTESTED_PAN.getName(), fileName, file, appId);
			}
			break;
		case "AUDITED_BALANCE_SHEET":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.AUDITED_BALANCE_SHEET.getName(), fileName, file, appId);
			}
			break;
		case "SALES_TAX":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.SALES_TAX.getName(), fileName, file, appId);
			}
			break;
		case "VOIDED_CHECK":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.VOIDED_CHECK.getName(), fileName, file, appId);
			}
			break;
		case "CREDIT_CARD_TURNOVER":
			if (file != null && (fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".jpg")
					|| fileName.toLowerCase().endsWith(".png"))) {
				saveFile(DocumentNameConstants.CREDIT_CARD_TURNOVER.getName(), fileName, file, appId);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid document type for T");
		}
	}

	private void saveFile(String documentName, String fileName, MultipartFile file, String appId) {

		try {
			fileStorageUtility.saveFile(documentName, fileName, file, appId);
		} catch (Exception e) {
			throw new RuntimeException("Could not save file: " + fileName, e);
		}
	}
}
