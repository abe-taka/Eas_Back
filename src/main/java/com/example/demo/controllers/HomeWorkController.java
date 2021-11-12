package com.example.demo.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
import com.example.demo.entities.HomeWorkAnswerEntity;
import com.example.demo.entities.HomeWorkManageEntity;
import com.example.demo.entities.HomeWorkSubmissionEntity;
import com.example.demo.entities.StudentEntity;
import com.example.demo.entities.TeacherEntity;
import com.example.demo.forms.ClassForm;
import com.example.demo.forms.HomeworkAnswerForm;
import com.example.demo.forms.HomeworkForm;
import com.example.demo.forms.HomeworkSubmiForm;
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
	 * 問題アップロード画面
	 * @param model
	 * @return　問題アップロード画面
	 */
	// 問題作成
	@GetMapping(value = "/homework")
	public String Get_Homework(Model model) {
		// セッションがあるかをチェック
		if (!(session_manage.Check_SessionId(session_id))) {
			return "redirect:login/login";
		} else {
			// メールドレスを取得
			String mailaddress = null;
			mailaddress = session_manage.getSession_mail();
			model.addAttribute("session_mail", mailaddress);
			//名前を取得
			String session_name = session_manage.getSession_name();
			model.addAttribute("session_name", session_name);
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
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
			
			System.out.println("カラム数" + homeworkform.getAnswercolumn_num());
			
			homeworkmanage.setAnswercolumnnum(homeworkform.getAnswercolumn_num());
			homeworkmanage.setHomeworkfilename(fileNames.toString());
			//選択した科目取得
			String getHomeworkSubject = String.valueOf(homeworkform.getHomework_subject());
			homeworkmanage.setHomeworksubject(getHomeworkSubject);
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
			List bookAll = jdbcTestRepository.findAll();
			model.addAttribute("bookAll",bookAll);
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homeworklist";
		}
	}
	
	// 宿題提示テーブルへ移動
	@GetMapping(value = "/homeworkmovesubmission/{homework_id}")
	public String Get_HomeworkMoveSubmission(Model model,Integer homework_id) {
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);

			//一覧を取得
			List bookAll = jdbcTestRepository.findAll();
			model.addAttribute("bookAll",bookAll);
			model.addAttribute("HomeworkForm", new HomeworkForm());

			return "homework/homeworklist";
		}
	}
	
	@PostMapping(value = "/homeworkmovesubmission")
	public String Post_HomeworkMoveSubmission(
			Model model, HomeworkSubmiForm homeworkSubmiForm,Integer homework_id,@ModelAttribute("HomeworkSubmiForm") HomeworkSubmiForm form
			) {
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
			//学校コード取得
			String session_schoolcode = String.valueOf(session_manage.getSession_schoolcode());
			
			//selectタグから取得した学年　組
			String getSchoolYear = String.valueOf(homeworkSubmiForm.getSchool_year());
			String getSchoolClass = String.valueOf(homeworkSubmiForm.getSchool_class());
			
			//宿題提示テーブルに追加するためのクラスIDを生成
			String schoolID = session_schoolcode + getSchoolYear + getSchoolClass;
			
			System.out.println("学校ID" + schoolID);
			
			System.out.println("学年組" + getSchoolYear + getSchoolClass);
			
			System.out.println("宿題ID" + homework_id);

			//一覧を取得
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			
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
			//学校コードを取得
			int session_schoolCode = session_manage.getSession_schoolcode();
			model.addAttribute("session_schoolcode",session_schoolCode);
			System.out.println("session_schoolCode" + session_schoolCode);
			
			//selectタグから取得した学年　組
			String getSchoolYear = String.valueOf(classform.getSchool_year());
			String getSchoolClass = String.valueOf(classform.getSchool_class());
			model.addAttribute("SchoolYear ", getSchoolYear);
			model.addAttribute("SchoolClass ", getSchoolClass);

			//学校コード生成
			int schoolCode = Integer.parseInt(session_schoolCode + getSchoolYear.format("%2s", getSchoolYear).replace(" ", "0") + getSchoolClass.format("%2s", getSchoolClass).replace(" ", "0"));
			System.out.println("schoolCode" + schoolCode);
			
			//セッションに追加
			session.setAttribute("classID", schoolCode);
			
			//学校コードから学生情報テーブル(student_table)の一覧を取得
			List studentList = jdbcTestRepository.studentListfindAll(schoolCode);
			model.addAttribute("studentList",studentList);
			
			return "homework/homework_submistatus";
			
		}
	}
	
	// 生徒個別提出状況確認
	@GetMapping(value = "/homeworkdetails/{student_name}/{class_no}")
	public String Get_HomeworkDetails(@PathVariable String student_name,@PathVariable Integer class_no, Model model) {
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
			List homeworkDetailsList = jdbcTestRepository.homeworkListfindAll(schoolCode,class_no);
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
	
	// 宿題提出
	@GetMapping(value = "/homeworksubmi")
	public String Get_HomeworkSubmission(Model model) {
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
			//クラスIDを取得
			int session_classid = Integer.parseInt(session_manage.getSession_classid());
			model.addAttribute("session_classid", session_classid);
			//出席番号
			int session_classno = session_manage.getSession_calss_no();
			model.addAttribute("session_classno", session_classno);
			
			//学校コードからクラス別の宿題一覧を取得
			List homeworkDetailsList = jdbcTestRepository.homeworkListfindAll(session_classid,session_classno);
			model.addAttribute("homeworkDetailsList",homeworkDetailsList);
			
			return "homework/homeworksubmi";
		}
	}
	
	// 宿題提出
	@PostMapping(value = "/homeworksubmi")
	public String Post_HomeworkSubmission(Model model,HomeworkAnswerForm homeworkAnswerForm,@RequestParam("content") String content) {
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
			//クラスIDを取得
			int session_classid = Integer.parseInt(session_manage.getSession_classid());
			model.addAttribute("session_classid", session_classid);
			//出席番号
			int session_classno = session_manage.getSession_calss_no();
			model.addAttribute("session_classno", session_classno);
			
			System.out.println("session_classid:" + session_classid + "session_classno" +session_classno);
			
			int submission_id = (int) session.getAttribute("submission_id");
			int homework_id = (int) session.getAttribute("homework_id");
			
			System.out.println("submission_id" + submission_id);
			
			HomeWorkSubmissionEntity homeWorkSubmissionEntity = new HomeWorkSubmissionEntity();
			homeWorkSubmissionEntity.setHomeworksubmissionid(submission_id);
			
			jdbcTestRepository.insertHomework(session_classno, session_classid,submission_id);
			
			HomeWorkAnswerEntity homeWorkAnswerEntity = new HomeWorkAnswerEntity();
			homeWorkAnswerEntity.setAnswer_content(content);
			homeWorkAnswerEntity.setClassno(session_classno);
			
			System.out.println("解答内容" + content);
			System.out.println("学校ID" + session_classid);
			System.out.println("出席番号" + session_classno);
			System.out.println("課題番号" + homework_id);
			
			jdbcTestRepository.insertAnswerContent(homeWorkAnswerEntity,homework_id);
			
			//学校コードからクラス別の宿題一覧を取得
			List homeworkDetailsList = jdbcTestRepository.homeworkListfindAll(session_classid,session_classno);
			model.addAttribute("homeworkDetailsList",homeworkDetailsList);
			
			return "homework/homeworksubmi";
		}
	}
	
	// 宿題する画面
	@GetMapping(value = "/homeworkstudent/{homework_id}/{submission_id}")
	public String Get_HomeworkStudent(@PathVariable Integer homework_id,@PathVariable Integer submission_id,Model model,HttpServletResponse response) throws IOException {
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
			//クラスIDを取得
			int session_classid = Integer.parseInt(session_manage.getSession_classid());
			model.addAttribute("session_classid", session_classid);
			//出席番号
			int session_classno = session_manage.getSession_calss_no();
			model.addAttribute("session_classno", session_classno);
			
			//クラスID(session_classid),出席番号(session_classno),宿題確認ID(submission_id)から解答欄の数を取得
			HomeworkForm homeworkForm = jdbcTestRepository.selectInputText(session_classid,session_classno,homework_id);
			System.out.println("解答欄の数は" + homeworkForm.getAnswercolumn_num());
			System.out.println("課題名は" + homeworkForm.getHomework_filename());

			//宿題確認ID(submission_id)をセッションに追加
			session.setAttribute("submission_id", submission_id);
			
			//宿題確認ID(homework_id)をセッションに追加
			session.setAttribute("homework_id", homework_id);
			
			model.addAttribute("answercolumn_num",homeworkForm.getAnswercolumn_num());
			model.addAttribute("homework_filename",homeworkForm.getHomework_filename());
			
			return "homework/homework_student";
		}
	}
}