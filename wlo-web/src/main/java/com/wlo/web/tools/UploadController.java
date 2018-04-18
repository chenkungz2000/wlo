package com.wlo.web.tools;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller("uploadController")
@RequestMapping("/prj")
public class UploadController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("view/upload");
		return mv;
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String testRedirect() {
		return "redirect:/view/upload.jsp";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> Upload(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		String msg = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			if (!file.isEmpty()) {
				if (!isImage(file))
					throw new Exception("");
				// 获得物理路径webapp所在路径
				// String pathRoot =
				// request.getSession().getServletContext().getRealPath("");
				String pathRoot = this.getClass().getClassLoader().getResource("/").getPath();
				String path = "";
				if (!file.isEmpty()) {
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					String datename = dateFormat.format(date);
					// 获得文件类型（可以判断如果不是图片，禁止上传）
					String contentType = file.getContentType();
					// 获得文件后缀名称
					String imageName = contentType.substring(contentType.indexOf("/") + 1);
					// path = "/upload/" + uuid + "." + imageName;
					path = "/upload/" + datename + "." + imageName;
					file.transferTo(new File(pathRoot + path));
				}
				flag = true;
				msg = path;
			}
		} catch (Exception e) {
			msg = e.getMessage();
		}
		map.put("msg", msg);
		map.put("flag", flag);
		return map;

	}

	@RequestMapping(value = "/uploadlist", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> fildUploadList(HttpServletRequest request, @RequestParam("files") MultipartFile[] files)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> maplist = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		boolean flag = false;
		String msg = "";
		String path = "";
		try {
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					// 保存文件,输出文件路径
					path = saveFile(request, file);
					if (path != null) {
						maplist.put("path", path);
						list.add(maplist);
					}
				}
				flag = true;
				map.put("pathlist", list);
			}
		} catch (Exception e) {
			msg = e.getMessage();
			map.put("path", null);
			map.put("msg", msg);
		}
		map.put("flag", flag);
		return map;
	}

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private String saveFile(HttpServletRequest request, MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			if (!isImage(file))
				throw new Exception("");
			try {
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String pathRoot = request.getSession().getServletContext().getRealPath("");
				String path = "";
				String datename = dateFormat.format(date);
				String contentType = file.getContentType();
				String imageName = contentType.substring(contentType.indexOf("/") + 1);
				path = "/upload/" + datename + "." + imageName;
				file.transferTo(new File(pathRoot + path));
				return path;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 判断是否为图片类型文件
	 * 
	 * @param imageFile
	 * @return
	 */
	public static boolean isImage(MultipartFile myfile) {
		// 转file
		CommonsMultipartFile cf = (CommonsMultipartFile) myfile;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File imageFile = fi.getStoreLocation();
		if (!imageFile.exists()) {
			return false;
		}
		Image img = null;
		try {
			img = ImageIO.read(imageFile);
			if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			img = null;
		}
	}
}
