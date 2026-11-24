package com.intr.svcImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.intr.dao.CoreDao;
import com.intr.dao.EmpDao;
import com.intr.dao.FileDao;
import com.intr.dao.QueryDao;
import com.intr.svc.FileService;
import com.intr.svc.UtilService;
import com.intr.utils.Const;

@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {
	//
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	FileDao fileDao;

	@Autowired
	CoreDao coreDao;
	
	@Autowired
	EmpDao empDao;
	
	@Autowired
	QueryDao queryDao;
	
    private final Logger logger = LoggerFactory.getLogger(getClass());

	// 파일 경로 조회
	public String getFilePath(HashMap<String, Object> paramMap) throws Exception {
		//--------------------------------------------------------------------------------------------
		// 경로 검색
		//--------------------------------------------------------------------------------------------
		String fullPath = "";
		String filetypeCd = utilService.nvlProc((String)paramMap.get("filetypeCd"));
		String filePath = getBasePath(Const.FILE_PATH, filetypeCd);
		fullPath = filePath + getDatePath();
		//
		return fullPath;
	}
	
	// (임시) 파일 경로 조회
	public String getTempPath(HashMap<String, Object> paramMap) throws Exception {
		//--------------------------------------------------------------------------------------------
		// 경로 검색
		//--------------------------------------------------------------------------------------------
		String fullPath = "";
		String filetypeCd = utilService.nvlProc((String)paramMap.get("filetypeCd"));
		String filePath = getBasePath(Const.TEMP_PATH, filetypeCd);
		fullPath = filePath + getDatePath();
		//
		return fullPath;
	}
	
	// 운영 체제 조회
	public String getBasePath(String basePath, String filetypeCd) throws Exception {
		//--------------------------------------------------------------------------------------------
		// 경로 생성
		//--------------------------------------------------------------------------------------------
		String os = System.getProperty("os.name").toLowerCase();
		String filePath = "";

		// 운영 체제 구분
		if(os.contains("win")) {
			filePath = "C:\\" + basePath + File.separator;
		} else {
			filePath = "/" + basePath.replace("\\", "/") + File.separator;
		}
		
		// 파일 종류
		if(!utilService.isNull(filetypeCd)) {
			filePath += filetypeCd + File.separator;
		}
		//
		return filePath;
	}
	
	// 날짜별 경로 생성
	private String getDatePath() {
	    LocalDate now = LocalDate.now();

	    return now.getYear()
	            + File.separator
	            + String.format("%02d", now.getMonthValue())
	            + File.separator;
	}
	
	// 파일 업로드, 수정
	public String fileUpload(Model model, HashMap<String, Object> paramMap, MultipartHttpServletRequest request) throws Exception {
	    //
	    HashMap<String, Object> tempMap = null;
	    String fileId = utilService.nvlProc((String) paramMap.get("fileId"));
	    String filePath = "";
	    String isUploadImg = utilService.nvlProc((String) paramMap.get("isUploadImg"));
	    MultipartFile file = null;
	    int num = 0;
	    //
	    try {
	        // 파일 목록
	        List<MultipartFile> fileList = request.getFiles("fileList");

	        // 신규 파일 여부 확인
	        boolean hasNewFile = false;

	        if ("Y".equals(isUploadImg)) {
	            hasNewFile = !fileList.isEmpty() && !fileList.get(0).isEmpty();
	            
	        } else if (isUploadImg.isEmpty()) {
	            for (int i = 0; i < fileList.size(); i++) {
	                if (paramMap.containsKey("insert" + i)
	                        && !fileList.get(i).isEmpty()) {
	                    hasNewFile = true;
	                    break;
	                }
	            }
	        }

	        // FILE_ID 채번
	        if (fileId.isEmpty() && hasNewFile) {
	            tempMap = fileDao.fileInqyDao1010(paramMap);
	            fileId = (String) tempMap.get("fileId");
	        }
	        //
	        if (fileId.isEmpty()) {
	            return null;
	        }
	        paramMap.put("fileId", fileId);

	        // 경로 조회
	        filePath = this.getFilePath(paramMap);
	        paramMap.put("filePath", filePath);

	        // 일반 첨부 등록 — 0, 1, 2 순서대로 저장
	        if (isUploadImg.isEmpty()) {
	            for (int i = 0; i < fileList.size(); i++) {
	                if (!paramMap.containsKey("insert" + i)) {
	                    continue;
	                }
	                file = fileList.get(i);
	                //
	                if (!file.isEmpty()) {
	                    String saveFileNm = this.fileSave(paramMap, file);
	                    this.transferTo(filePath, saveFileNm, file);
	                }
	            }
	        }

	        // 일반 첨부 삭제
	        for (String key : paramMap.keySet()) {
	            if (key.matches("delete\\d+")) {
	                num = Integer.parseInt(key.replace("delete", ""));
	                this.fileDelete(paramMap, num);
	            }
	        }

	        // 프로필·배너 이미지 교체 또는 삭제
	        if ("Y".equals(isUploadImg) || "N".equals(isUploadImg)) {
	            tempMap = new HashMap<String, Object>();
	            tempMap.put("fileId", fileId);
	            tempMap.put("fileSno", "");
	            //
	            fileDao.fileProcDao1020(tempMap);
	            //
	            if ("Y".equals(isUploadImg)) {
	                if (fileList.isEmpty() || fileList.get(0).isEmpty()) {
	                    throw new IllegalArgumentException("Exception : 업로드할 이미지가 없습니다.");
	                }
	                //
	                file = fileList.get(0);
	                String saveFileNm = this.fileSave(paramMap, file);
	                this.transferTo(filePath, saveFileNm, file);
	            }
	        }

	    } catch (Exception e) {
	        throw e;
	    }

	    return fileId;
	}

	// 파일 DB 저장
	public String fileSave(HashMap<String, Object> paramMap, MultipartFile file)  throws Exception {
		//
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		String fileNm = file.getOriginalFilename();
		String fileExt = fileNm.substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
		String saveFileNm = getSaveFileNm(fileExt);
		//
		try {
			//
			tempMap.put("fileId", 				utilService.nvlProc((String)paramMap.get("fileId")));
			tempMap.put("fileNm", 				file.getOriginalFilename());
			tempMap.put("saveFileNm",  	saveFileNm);
			tempMap.put("filePath", 			utilService.nvlProc((String)paramMap.get("filePath")));
			tempMap.put("filetypeCd", 		utilService.nvlProc((String)paramMap.get("filetypeCd")));
			tempMap.put("fileExt", 				fileExt);
			tempMap.put("fileSize", 			file.getSize());
			//
			fileDao.fileProcDao1010(tempMap);
			
		} catch (Exception e) {
			throw e;
		}
		//
		return saveFileNm ;
	}
	
	// 저장 파일명 생성
	private String getSaveFileNm(String fileExt) {
		//
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		//
		for (int i = 0; i < 16; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		//
		return sb.toString() + "." + fileExt;
	}
	
	// 파일 DB 삭제
	public void fileDelete(HashMap<String, Object> paramMap, int num)  throws Exception {
		//
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		//
		try {
			//
			tempMap.put("fileId", 		utilService.nvlProc((String)paramMap.get("fileId")));
			tempMap.put("fileSno", 	paramMap.get("delete" + num));
			//
			fileDao.fileProcDao1020(tempMap);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	// 파일 저장
	public void transferTo(String filePath, String saveFileNm, MultipartFile file) throws Exception {
		File dir = new File(filePath);
		//
		try {
			//
			if (!dir.exists()) {
	            dir.mkdirs();
	        }
			//
			File transferFile = new File(dir, saveFileNm);
		    file.transferTo(transferFile);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	// 파일 다운로드
	public void fileDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		HashMap<String, Object> defaultInfo = null;
		String fileNm = "";
		String saveFileNm = "";
		String filePath = "";
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 파일 정보 조회
			//--------------------------------------------------------------------------------------------
			defaultInfo = fileDao.fileInqyDao1020(paramMap);
			
			fileNm = utilService.nvlProc((String)defaultInfo.get("fileNm"));
			filePath = utilService.nvlProc((String)defaultInfo.get("filePath"));
			saveFileNm = utilService.nvlProc((String)defaultInfo.get("saveFileNm"));
			
			//--------------------------------------------------------------------------------------------
			// 파일 다운로드
			//--------------------------------------------------------------------------------------------
			downProc(filePath, fileNm, saveFileNm, request, response);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
	
	// 파일 다운로드
	public void downProc(String filePath, String fileNm, String saveFileNm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		String value = "";
		String fullPath = filePath + saveFileNm;
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 파일 다운로드
			//--------------------------------------------------------------------------------------------
			File f = new File(fullPath);
			byte [] b = new byte[1024*1024*4]; 
			//
			String strAgent = request.getHeader("User-Agent");
			String userCharSet = request.getCharacterEncoding();
			//
			if(userCharSet==null) {
				userCharSet="utf-8";
			}
			
	        // IE인 경우
	        if(strAgent.indexOf("MSIE") > -1){
	            // IE5.5인 경우
	            if(strAgent.indexOf("MSIE 5.5") > -1){
	                value = "=" + fileNm;
	            }
	            // 나머지의 경우
	            else if(strAgent.indexOf("MSIE 7.0") > -1){
	            	//
	                if(userCharSet.equalsIgnoreCase("UTF-8")){
	                	//
	                	fileNm = URLEncoder.encode(fileNm, userCharSet);
	                	fileNm = fileNm.replaceAll("\\+", "%20");
	                    value = "attachment; filename=" + new String(fileNm.getBytes(userCharSet), "ISO-8859-1");
	                }else{
	                	//
	                    value = "attachment; filename=" + new String(fileNm.getBytes(userCharSet), "ISO-8859-1");
	                }
	            }else{
	            	// IE8.0 이상의 경우
	            	if(userCharSet.equalsIgnoreCase("UTF-8")){
	            		//
	            		fileNm = URLEncoder.encode(fileNm,"utf-8");
	                	fileNm = fileNm.replaceAll("\\+", "%20");
	                    value = "attachment; filename=" + new String(fileNm.getBytes(userCharSet), "ISO-8859-1");
	                }else{
	                	//
	                    value = "attachment; filename=" + new String(fileNm.getBytes(userCharSet), "ISO-8859-1");
	                }
	            }
	            
	        }else if(strAgent.indexOf("Firefox") > -1){
	        	// 파이어폭스 (공백문자 이후 인식이 안되는 현상)
	        	value = "attachment; filename=" + new String(fileNm.getBytes(), "ISO-8859-1");
	        }else{
	            // IE 제외 (브라우저)
                value = "attachment; filename=" + new String(fileNm.getBytes(userCharSet), "ISO-8859-1");
	        }
	        //
	        response.setContentType("Pragma: no-cache"); 
			response.setContentType("application/octet-stream;charset=8859_1;");
			//
			response.setHeader("Content-Disposition", value);
			response.setHeader("Content-Transfer-Encoding", "binary;");
	
			// 파일 읽어오기
			if(f.isFile()){
				//
				int i=0;
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				//
				try	{
					//
					while((i=bis.read(b))!=-1){
						bos.write(b,0,i);
					}
						
				}catch(Exception ex){
					//
					throw new Exception(ex.getMessage());
					
				} finally {
					//
					if(bos!=null)bos.close();
					if(bis!=null)bis.close();
				}
			}

		} catch (Exception e) {
			//
			throw e;
		}
	}
	
	// 매뉴얼 다운로드
	public void manualDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		String docCd = utilService.nvlProc((String)paramMap.get("docCd"));
		String fileNm = "";
		String saveFileNm = "";
		String filePath = "";
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 명칭 및 경로 조회
			//--------------------------------------------------------------------------------------------
			fileNm = (docCd.equals("ppt")) ? Const.INTR_PPT : (docCd.equals("word")) ? Const.INTR_WORD : Const.INTR_EXCEL;
			saveFileNm = fileNm;

			//--------------------------------------------------------------------------------------------
			// 경로 생성
			//--------------------------------------------------------------------------------------------
			filePath = getBasePath(Const.MANU_PATH, null);

			//--------------------------------------------------------------------------------------------
			// 파일 다운로드
			//--------------------------------------------------------------------------------------------
			downProc(filePath, fileNm, saveFileNm, request, response);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
	
	// 전체 다운로드
	public void zipDown(Model model, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		String tempPath = "";
		String fileNm = (String)paramMap.get("fileId") + ".zip";
		String saveFileNm = fileNm;
		fileNm = fileNm.replaceAll(" ", "");
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 압축 파일 생성
			//--------------------------------------------------------------------------------------------
			tempPath = zipProc(paramMap);
			
			//--------------------------------------------------------------------------------------------
			// 파일 다운로드
			//--------------------------------------------------------------------------------------------
			downProc(tempPath, fileNm, saveFileNm, request, response);

			//--------------------------------------------------------------------------------------------
			// 임시 경로 삭제
			//--------------------------------------------------------------------------------------------
			delTempPath(tempPath);
			
		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 압축 파일 생성
	public String zipProc(HashMap<String, Object> paramMap) throws Exception {
		//
		List<HashMap<String, Object>> defaultList = null;
		//
		String tempPath = "";
		String filePath = "";
		String fileId = (String)paramMap.get("fileId");
		String contentNm = utilService.nvlProc(fileId).replaceAll(" ", "");
		//
		FileOutputStream fos = null;
		FileInputStream fis = null;
		ZipOutputStream zos = null;
		//
		try {
			//--------------------------------------------------------------------------------------------
			// 임시파일 경로 생성
			//--------------------------------------------------------------------------------------------
			tempPath = this.getTempPath(paramMap);

			//--------------------------------------------------------------------------------------------
			// 압축 파일 조회
			//--------------------------------------------------------------------------------------------
			defaultList = fileDao.fileInqyDao1020(fileId);
					
			if(defaultList!=null && !defaultList.isEmpty()) {
				filePath = utilService.nvlProc((String)defaultList.get(0).get("filePath"));
				//--------------------------------------------------------------------------------------------
				// 임시(압축) 파일
				//--------------------------------------------------------------------------------------------
				File zip = new File(tempPath);
				//
				if(!zip.exists()) {
					zip.mkdirs();
				}

				// 압축 파일 생성 스트림 오픈
				fos = new FileOutputStream(tempPath + contentNm + ".zip");
				zos = new ZipOutputStream(fos);
				
				// 압축할 파일을 임시 경로에 생성
				for(int i=0;i<defaultList.size();i++) {
					//
					File f = new File(filePath + (String)defaultList.get(i).get("saveFileNm"));
					fis = new FileInputStream(f); // 압축 대상 스트림 오픈
					zos.putNextEntry(new ZipEntry(f.getName())); // 압축 리스트 추가
					// 
					byte [] bytes = new byte[1024];
					int length;
					// 압축 대상 파일 읽음 (마지막 도달 시 -1 리턴)
					while((length=fis.read(bytes))>=0) {
						zos.write(bytes,0,length);
					}
					
					// 스트림 종료
					fis.close();
					zos.closeEntry();
				}
				//
				zos.close();
				fos.close();

			} else {
				throw new Exception("다운로드할 파일이 없습니다.");
			}
			
		} catch (Exception e) {
			//
			throw e;
			
		} finally {
			//
			try { if(fis != null)fis.close(); } catch (IOException ex1) {logger.error(ex1.getMessage());}
		    try { if(zos != null)zos.closeEntry();} catch (IOException ex2) {logger.error(ex2.getMessage());}
		    try { if(zos != null)zos.close();} catch (IOException ex3) {logger.error(ex3.getMessage());}
		    try { if(fos != null)fos.close(); } catch (IOException ex4) {logger.error(ex4.getMessage());}
		}
		//
		return tempPath;
	}
	
	// 임시 경로 삭제
	public void delTempPath(String path) throws Exception {
		//
		try {
			//
			File file = new File(path);
			//
		    if (!file.exists())
		        return;

		    if (file.isDirectory()) {
		        File[] children = file.listFiles();
		        //
		        if (children != null) {
		            for (File child : children) {
		                delTempPath(child.getAbsolutePath());
		            }
		        }
		    }
		    //
		    file.delete();
			
		} catch (Exception e) {
			//
			throw e;
		}
	}

	// 엑셀 다운로드
	@SuppressWarnings("resource")
	public void excelDown(List<Map<String, Object>> defaultList, HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		Set<String> allKeys = new HashSet<String>();
		//
		try {
			 // 컬럼 목록
			 for (Map<String, Object> map : defaultList) {
			     allKeys.addAll(map.keySet());
			 }
			 //
			 List<String> columList = new ArrayList<String>(allKeys);
			  
			 //---------------------------------------------------------------------------
			 // 엑셀 생성
			 //---------------------------------------------------------------------------
			 XSSFWorkbook wb = new XSSFWorkbook(); Sheet sheet = wb.createSheet("query");

			 // 서식 생성
	        CellStyle headerStyle = wb.createCellStyle();
	        headerStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        
	        XSSFFont headerFont = wb.createFont();
	        headerFont.setBold(true);
	        headerStyle.setFont(headerFont);
	        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerStyle.setBorderTop(BorderStyle.THIN);
	        headerStyle.setBorderBottom(BorderStyle.THIN);
	        headerStyle.setBorderLeft(BorderStyle.THIN);
	        headerStyle.setBorderRight(BorderStyle.THIN);
	        
	        CellStyle bodyStyle = wb.createCellStyle();
	        XSSFFont bodyFont = wb.createFont();
	        bodyStyle.setFont(bodyFont);
	        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
	        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        bodyStyle.setBorderTop(BorderStyle.THIN);
	        bodyStyle.setBorderBottom(BorderStyle.THIN);
	        bodyStyle.setBorderLeft(BorderStyle.THIN);
	        bodyStyle.setBorderRight(BorderStyle.THIN);

			//--------------------------------------------------------------------------------------------
			// 헤더 생성
			//--------------------------------------------------------------------------------------------
			 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columList.size() - 1));
			 Row row = sheet.createRow(0); 
			 Cell cell = row.createCell(0);
			 cell.setCellValue((String)paramMap.get("headTit"));
			 cell.setCellStyle(headerStyle);
	        
			//--------------------------------------------------------------------------------------------
			// 컬럼 생성
			//--------------------------------------------------------------------------------------------
	        Row sr = sheet.createRow(1);
	        //
	        for(int i=0;i<columList.size();i++) {
	        	Cell cc = sr.createCell(i);
	        	cc.setCellValue(columList.get(i));
	            cc.setCellStyle(headerStyle);
	        }

			//--------------------------------------------------------------------------------------------
			// 내용 생성
			//--------------------------------------------------------------------------------------------
	        for(int i=0;i<defaultList.size();i++) {
		        Row tr = sheet.createRow(i + 2);
		        //
	        	for(int j=0;j<columList.size();j++) {
	        		String colNm = columList.get(j);	// MENU_CD, MENU_NM...
	        		String colData = utilService.nvlProc((String)defaultList.get(i).get(colNm)); // defaultList.get(i).get("MENU_CD")...
	        		//
		        	Cell rc = tr.createCell(j);
		        	rc.setCellValue(colData);
		        	rc.setCellStyle(bodyStyle);
		        	
		        	// 컬럼 사이즈 자동 조정
		        	sheet.autoSizeColumn(j);
		        	
		        	// 추가로 넉넉하게 한글 보정
		            int currentWidth = sheet.getColumnWidth(j);
		            sheet.setColumnWidth(j, Math.min(currentWidth + 1024, 255 * 256)); // 보정치 추가
	        	}
	        }

			//--------------------------------------------------------------------------------------------
			// 엑셀 다운로드
			//--------------------------------------------------------------------------------------------
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    response.setHeader("Content-Disposition", "attachment; filename=\"data.xlsx\"");

		     wb.write(response.getOutputStream());
		     response.getOutputStream().flush();
			
		} catch (Exception e) {
			//
			throw e;
		}
	}
}
