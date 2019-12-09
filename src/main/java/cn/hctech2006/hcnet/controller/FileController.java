package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysFile;
import cn.hctech2006.hcnet.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(tags = "文件传输接口")
@RequestMapping("/file")
public class FileController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @Autowired
    private FileService fileService;
    /**
     * 上传学习资源
     */
    @ApiOperation(value = "上传学习资源", notes = "上传学习资源")
    //@ApiImplicitParam(type = "query", name = "uploadFile",value = "资源文件",required = true)
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadFile(MultipartFile uploadFile, @ApiParam(value = "资源文件",required = true) HttpServletRequest request){
        if(uploadFile.isEmpty()){
            return RespBean.error("上传资源为空，请重新选择");
        }
        //文件
        SysFile sysFile = new SysFile();
        //图片绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/uploadFile/");
        System.out.println("realPat"+realPath);
        System.out.println("11");
        //格式化时间,以时间分类
        String format = sdf.format(new Date());
        //获得文件所在目录
        File folder = new File(realPath + format);
        //添加目录
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        System.out.println("11");
        //初始图片名
        String oldName = uploadFile.getOriginalFilename();
        //新的图片名
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."));
        System.out.println("11");
        try{
            //转移加载文件
            uploadFile.transferTo(new File(folder, newName));
            //图片相对路径
            String imgRelatePath = request.getScheme()+"://"+request.getServerName() +":"+request.getServerPort()+"/upload/uploadFile/"+format+newName;
            //设置文件名
            sysFile.setFileName(oldName);
            //设置文件URL
            System.out.println("11");
            sysFile.setFileUrl(imgRelatePath);
            fileService.saveFile(sysFile);
            System.out.println("AbsolutePath"+folder.getAbsolutePath()+"/"+newName);
            System.out.println("Path"+folder.getPath()+"/"+newName);
            System.out.println("filePath"+imgRelatePath);
            System.out.println("11");
            return RespBean.ok("文件上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("文件上传失败");
        }

    }
    /**
     * 下载功能仅供学习资源下载使用
     * @param id
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "下载资源", notes = "下载资源")
    @ApiImplicitParam(type = "query", name = "id",value = "资源序号",required = true)
    @PostMapping("/download")
    public RespBean downloadFile(String id, HttpServletResponse response){
        //下载文件这里先暂定SysFile,以后再进行补充
        SysFile sysFile = fileService.getFileById(Integer.parseInt(id));
        //文件相对地址是文件的下载地址
        String fileUrl = sysFile.getFileUrl();
        System.out.println("11");
        try{
            //创建文件对象
            File file = new File(fileUrl);
            if(!file.exists()){
                RespBean.error("文件名不存在");
            }
            System.out.println("11");
            //得到文件名
            String realname = file.getName();
            //下载显示文件名,解决中文乱码问题
            realname = new String(realname.getBytes("UTF-8"),"ISO-8859-1");
            //设置响应头,控制浏览器下载文件
            response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(realname,"UTF-8"));
            //读取要下载的文件,保存到文件输入流
            FileInputStream in = new FileInputStream(fileUrl);
            //创建输出流
            OutputStream out = response.getOutputStream();
            System.out.println("11");
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            while((len = in.read(buffer)) > 0){
                //输出缓冲区的内容到浏览器,实现文件下载
                out.write(buffer,0,len);
            }
            System.out.println("11");
            //关闭文件输入流
            in.close();
            //关闭文件输出流
            out.close();
            return RespBean.ok("下载成功",sysFile);
        }catch (Exception e){

            e.printStackTrace();
            return RespBean.error("下载失败");
        }

    }
}
