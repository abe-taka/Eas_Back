package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.entities.TeacherEntity;
import com.example.demo.forms.HomeworkForm;
import com.example.demo.forms.HomeworkuploadForm;
import com.example.demo.repositories.ClassRepository;
import com.example.demo.repositories.HomeworkRepository;
import com.example.demo.repositories.TeacherRepository;

//宿題
@Controller
public class HomeWorkController {
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/pdf";

	@Autowired
	SessionManage session_manage;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	HomeworkRepository<?> jdbcTestRepository;

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
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_mail", session_manage.getSession_mail());
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homework";
		}
	}
	
	/**
	 * アップロードしたファイル名と解答欄の数をmysqlに保存
	 * @param model
	 * @param files
	 * @param homeworkform
	 * @return アップロード確認画面
	 */
	// 問題のアップロード確認
	@PostMapping(value="/homeworkupload")
	public String upload(Model model,@RequestParam("files") MultipartFile[] files,HomeworkForm homeworkform) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_mail", session_manage.getSession_mail());
			StringBuilder fileNames = new StringBuilder();
			for(MultipartFile file : files) {
				Path fileNamePath = Paths.get(uploadDirectory,file.getOriginalFilename());
				fileNames.append(file.getOriginalFilename());
				try {
					Files.write(fileNamePath, file.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			model.addAttribute("msg","Successfully uploaded files:" + fileNames.toString());
			HomeWorkManageEntity homeworkmanage = new HomeWorkManageEntity();
			homeworkmanage.setAnswercolumnnum(homeworkform.getAnswercolumn_num());
			homeworkmanage.setHomeworkfilename(fileNames.toString());
			jdbcTestRepository.insertPDF(homeworkmanage);
			return "homework/homeworkupload";
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
			model.addAttribute("session_mail", "あなたのメールアドレス:" + session_manage.getSession_mail());
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
			model.addAttribute("session_mail", session_manage.getSession_mail());
			
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
			model.addAttribute("session_mail", session_manage.getSession_mail());
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homework_submistatus";
		}
	}
	
	// 提出状況確認
	@PostMapping(value = "/homework_submistatus")
	public String Post_HomeworkSubmiStatus(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			model.addAttribute("session_mail", session_manage.getSession_mail());
			model.addAttribute("HomeworkForm", new HomeworkForm());
			return "homework/homework_submistatus";
		}
	}

	// 宿題提出
	@GetMapping(value = "/homeworksubmi")
	public String Get_HomeworkSubmission(Model model) {
		// セッションがあるかをチェック
		if (!session_manage.Check_SessionId(session_id)) {
			return "redirect:login/login";
		} else {
			Boolean teacher_flag;
			// 教師か生徒かを判別
			if (!(teacherRepository.SearchMailAddress(session_manage.getSession_mail()))) {
				//生徒
				teacher_flag = false;
				System.out.println("生徒");
			} else {
				//教師
				teacher_flag = true;
				System.out.println("教師");
				// 所属学校コード取得
				TeacherEntity teacherEntity = new TeacherEntity();
				teacherEntity = teacherRepository.SearchSchoolCode(session_manage.getSession_mail());
				int school_code = teacherEntity.getSchool().getSchoolcode();
				// 1年のクラスデータを取得
				List<ClassEntity> list_classEntity = classRepository.SearchSchoolYear(school_code, 1);
				model.addAttribute("list_classEntity", list_classEntity);
				model.addAttribute("session_mail", session_manage.getSession_mail());
				model.addAttribute("school_code", school_code);
			}
			model.addAttribute("flag", teacher_flag);
			model.addAttribute("session_id", session_manage.Get_SessionId(session_id));
			return "homework/homeworksubmi";
		}
	}

//	// 提出状況
//	@GetMapping(value = "/homework_submistatus")
//	public String Get_HomeworkSubmissionStatus() {
//		// セッションがあるかをチェック
//		if (!session_manage.Check_SessionId(session_id)) {
//			return "redirect:login/login";
//		} else {
//			return "homework/homework_submistatus";
//		}
//	}
}