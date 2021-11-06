package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.components.SessionManage;
import com.example.demo.entities.ClassEntity;
import com.example.demo.entities.HomeWorkManageEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.forms.ClassForm;
import com.example.demo.forms.HomeworkForm;
import com.example.demo.forms.HomeworkuploadForm;
import com.example.demo.forms.StudentForm;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.HomeworkRepository;
import com.example.demo.repositories.TeacherRepository;

//宿題
@Controller
public class HomeWorkController<SelectYearCode> {
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/pdf";

	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;
	
	 @Autowired
	 HttpSession session;  
	
	@Autowired
	HomeworkRepository jdbcTestRepository;

	// セッションid
	private String session_id = null;
	
	/**
	 * HomeControllで後日変更
	 * 後で消す
	 */
	// クラスID
    private Integer session_classId = 100020;

	/**
	 * 問題アップロード画面
	 * @param model
	 * @return　問題アップロード画面
	 */
	// 問題作成
	@GetMapping(value = "/homework")
	public String Get_Homework(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homework";
		}
	}
	
	// 問題作成
	@PostMapping(value = "/homework")
	public String Post_Homework(Model model,@RequestParam("files") MultipartFile[] files,HomeworkForm homeworkform) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			model.addAttribute("HomeworkForm", new HomeworkForm());
			
			StringBuilder fileNames = new StringBuilder();
			for(MultipartFile file : files) {
				
				//ファイルの選択の有無
				if(file.getOriginalFilename() == null | file.getOriginalFilename().isEmpty()) {
					System.out.println("ファイルが選択されてないよ");
					model.addAttribute("msg","ファイルが選択されていません");
					return "homework/homework";
				}
				
				Path fileNamePath = Paths.get(uploadDirectory,file.getOriginalFilename());
				//ファイル名取得
				fileNames.append(file.getOriginalFilename());
				//拡張子を取得
				String extension = fileNames.substring(fileNames.lastIndexOf("."));
				if(!file.getOriginalFilename().isEmpty() && extension.equals(".pdf")) {
					System.out.println("出来たよ");
					try {
						Files.write(fileNamePath, file.getBytes());
						model.addAttribute("msg",fileNames.toString() + "のアップロードが完了しました");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!extension.equals(".pdf")) {
					model.addAttribute("msg","pdfファイルのみアップロード出来ます");
					return "homework/homework";
				}else {
					System.out.println("あれ?");
					return "homework/homework";
				}
			}
			
			//宿題管理テーブル(homework_management_tbl)に追加
			HomeWorkManageEntity homeworkmanage = new HomeWorkManageEntity();
			homeworkmanage.setAnswercolumnnum(homeworkform.getAnswercolumn_num());
			homeworkmanage.setHomeworkfilename(fileNames.toString());
			jdbcTestRepository.insertPDF(homeworkmanage);
			
			return "homework/homework";
		}
	}
	
	/**
	 * アップロードした問題一覧表示
	 * @param model
	 * @return 問題管理画面
	 */
	// 問題管理
	@GetMapping(value = "/homeworklist")
	public String Get_HomeworkList(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			List bookAll = jdbcTestRepository.findAll();
			model.addAttribute("bookAll",bookAll);
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homeworklist";
		}
	}

	/**
	　* 問題情報削除
	　* @param homework_id 宿題ID
	　*　@param model Model
	　*　@return 問題管理画面
	 */
	// 問題削除
	@PostMapping(value = "/homeworkdelete")
	public String Get_HomeworkDelete(Model model,Integer homework_id) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//homework_idからhomework_filenameを取得
			HomeworkForm homeworkForm = jdbcTestRepository.selectFileName(homework_id);
			//指定したローカルファイルの削除
			File file = new File(uploadDirectory + "/" + homeworkForm.getHomework_filename());
			file.delete();
			
			//一覧を取得
			jdbcTestRepository.deleteHomeworkList(homework_id);
			List bookAll = jdbcTestRepository.findAll();
			model.addAttribute("bookAll",bookAll);
			model.addAttribute("HomeworkForm", new HomeworkForm());
			
			model.addAttribute("msg", homeworkForm.getHomework_filename() + "の削除完了しました");

			return "homework/homeworklist";
		}
	}
	
	// 提出状況確認
	@GetMapping(value = "/homework_submistatus")
	public String Get_HomeworkSubmiStatus(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			model.addAttribute("HomeworkForm", new HomeworkForm());
			
			model.addAttribute("ClassForm",new ClassForm());
			
			return "homework/homework_submistatus";
		}
	}
	
	// 提出状況確認
	@PostMapping(value = "/homework_submistatus")
	public String Post_HomeworkSubmiStatus(Model model,ClassForm classform) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			//selectタグから取得した学年　組
			String getSchoolYear = String.valueOf(classform.getSchool_year());
			String getSchoolClass = String.valueOf(classform.getSchool_class());
			model.addAttribute("SchoolYear ", getSchoolYear);
			model.addAttribute("SchoolClass ", getSchoolClass);
			/**
			 * 後で消す
			 */
			//学校コード生成
			int schoolCode = Integer.parseInt(session_classId.toString() + getSchoolYear.format("%2s", getSchoolYear).replace(" ", "0") + getSchoolClass.format("%2s", getSchoolClass).replace(" ", "0"));
			
			//セッションに追加
			session.setAttribute("classID", schoolCode);
			
			//学校コードから学生情報テーブル(student_table)の一覧を取得
			List studentList = jdbcTestRepository.studentListfindAll(schoolCode);
			model.addAttribute("studentList",studentList);
			
			return "homework/homework_submistatus";
			
		}
	}
	
	// 生徒個別提出状況確認
	@GetMapping(value = "/homeworkdetails/{student_name}")
	public String Get_HomeworkDetails(@PathVariable String student_name, Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			
			int schoolCode = (int) session.getAttribute("classID");
			
			//学校コードからクラス別の宿題一覧を取得
			List homeworkDetailsList = jdbcTestRepository.homeworkListfindAll(schoolCode);
			model.addAttribute("homeworkDetailsList",homeworkDetailsList);
			
			model.addAttribute("student_name",student_name);
			
			 // 現在日時情報で初期化されたインスタンスの生成
			 Date dateObj = new Date();
			 SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
			 // 日時情報を指定フォーマットの文字列で取得
			 String display = format.format( dateObj );
			 model.addAttribute("display",display);
			 
			
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homeworkdetails";
		}
	}
}