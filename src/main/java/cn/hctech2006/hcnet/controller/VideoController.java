package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.PageResult;
import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysGroup;
import cn.hctech2006.hcnet.bean.SysVideo;
import cn.hctech2006.hcnet.service.GroupService;
import cn.hctech2006.hcnet.service.VideoService;
import cn.hctech2006.hcnet.util.HtmlSpirit;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = "视频传输接口")
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private GroupService groupService;
    /**
     * 上传视频
     * @param video
     * @param uploadFile
     * @param request
     * @return
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    @ApiOperation(value = "上传视频", notes = "上传视频,必须有视频类型格式的视频路径,否则只能自己写一个表单进行测试")
    @ApiImplicitParams({

            @ApiImplicitParam(type = "query", name = "videoName",value = "视频名",required = true),
            @ApiImplicitParam(type = "query", name = "videoDetail",value = "视频简介",required = true),
            @ApiImplicitParam(type = "query", name = "videoGroup",value = "视频组别",required = true),
            @ApiImplicitParam(type = "query", name = "createTime",value = "视频时间",required = true,dataType = "java.util.Date"),
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadVideo(SysVideo video, @ApiParam(value = "视频文件",required = true) MultipartFile uploadFile, HttpServletRequest request){
        if(uploadFile.isEmpty() || video.getVideoName()==null || video.getVideoDetail()==null||video.getVideoGroup() == null||video.getCreateTime()==null){
            return RespBean.error("上传视频文件,视频名或视频简介为空，请填写完整");
        }
        System.out.println("time"+video.getCreateTime());
        System.out.println("group:"+video.getVideoGroup());
        System.out.println("video:"+video.getVideoDetail());
        SysVideo sysVideo = new SysVideo();
        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/video/");
        String realPath_img = request.getSession().getServletContext().getRealPath("/upload/video/");
        System.out.println("realPath"+realPath);
        String format = sdf.format(new Date());
        File folder = new File(realPath+format);
        //目录不存在就建一个
        if(!(folder.isDirectory())){
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
        try{
            uploadFile.transferTo(new File(folder,newName));
            String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+
                    "/upload/video/"+format+newName;
            //年份
            sysVideo.setCreateTime(video.getCreateTime());
            sysVideo.setYear(video.getCreateTime().toString().substring(video.getCreateTime().toString().length()-4));
            String imgName = UUID.randomUUID().toString()+".jpg";
            //存储绝对路径
            String imgRealPath = folder.getAbsolutePath()+"/"+imgName;
            //访问相对路径
            String imgUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+"upload/video/"+format+"/"+imgName;
            System.out.println("filePath:"+filePath);
            System.out.println("imgUrl:"+imgUrl);
            System.out.println("imgRealPath:"+imgRealPath);
            System.out.println(folder.getAbsolutePath()+"\\"+newName);
            fetchFrame(folder.getAbsolutePath()+"/"+newName,imgRealPath,imgName,request);
            //保存视频
            sysVideo.setVideoImgUrl(imgUrl);
            sysVideo.setVideoName(video.getVideoName());
            sysVideo.setVideoUrl(filePath);
            sysVideo.setVideoDetail(video.getVideoDetail());
            sysVideo.setVideoGroup(video.getVideoGroup());
            //sysVideo.setCreateBy(video.getCreateBy());
            //sysVideo.setCreateTime(new Date());
            //sysVideo.setUpdateBy(video.getUpdateBy());
            //sysVideo.setUpdateTime(new Date());
            System.out.println("group"+sysVideo.getVideoGroup());
            videoService.saveVideo(sysVideo);
            System.out.println("保存成功");
            return RespBean.ok("保存成功",sysVideo);
        }catch(Exception e){
            e.printStackTrace();
            return RespBean.error("保存失败");
        }
    }
    /**
     * 更新视频
     * @param newVideo
     * @param uploadFile
     * @param request
     * @return
     */
    @ApiOperation(value = "更新视频", notes = "更新视频")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "视频序号",required = true),
            @ApiImplicitParam(type = "query", name = "videoName",value = "视频名"),
            @ApiImplicitParam(type = "query", name = "videoDetail",value = "视频简介"),
            @ApiImplicitParam(type = "query", name = "videoGroup",value = "视频组别"),
            @ApiImplicitParam(type = "query", name = "createTime",value = "视频时间"),
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean updateVideoById(SysVideo newVideo,@ApiParam(value = "视频文件") MultipartFile uploadFile, HttpServletRequest request)throws Exception{
        if(newVideo.getId() == null || newVideo.getId().equals("")){
            return RespBean.error("序号为空，请填写完整序号！！");
        }
        //更改前视频
        SysVideo sysVideo = videoService.findVideoById(newVideo.getId());
        //sysVideo.setCreateTime();
        if(sysVideo == null){
            return RespBean.error("该视频序号不存在");
        }
        //更改后视频
        if(uploadFile!= null){
            //视频原始名
            String oldName = uploadFile.getOriginalFilename();
            //视频保存名
            String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
            //以日期分类存储
            String format = sdf.format(new Date());
            //视频物理真实路径
            String videoRealPath = request.getSession().getServletContext().getRealPath("/upload/video/");
            //视频访问相对路径
            String videoRelatePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/upload/video/"+format+newName;
            //视频截图名
            String imgName = UUID.randomUUID().toString()+".jpg";
            //视频截图绝对地址
            String imgRealPath = request.getSession().getServletContext().getRealPath("/upload/fetchImg/")+format+"/"+imgName;
            //视频截图的相对地址
            String imgRelatePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/upload/fetchImg/"+format+imgName;
            //在指定文件目录下新建文件
            File folder = new File(videoRealPath + format);
            //如果文件夹不存在，新建文件夹
            if(!folder.isDirectory()){
                folder.mkdirs();
            }
            try{
                //指定文件转移
                uploadFile.transferTo(new File(folder,newName));
                //视频截图保存
                fetchFrame(videoRealPath+format+newName, imgRealPath,videoRealPath+format,request);
                //保存视频访问地址
                newVideo.setVideoUrl(videoRelatePath);
                //保存视频名称
                // newVideo.setVideoName(oldName);
                //保存视频截图访问URL
                newVideo.setVideoImgUrl(imgRelatePath);
                //最初发布者
                newVideo.setCreateBy(sysVideo.getCreateBy());
                //发布时间
                //newVideo.setCreateTime(sysVideo.getCreateTime());
                //修改时间
                newVideo.setUpdateTime(new Date());
                //保存数据库
                System.out.println("保存成功");
                System.out.println("videoRealPath:"+videoRealPath+format+newName);
                System.out.println("videoRelatePath"+videoRelatePath);
                System.out.println("imgRealUrl:"+imgRealPath);
                System.out.println("imgRelateUrl:"+imgRelatePath);
                newVideo.setId(sysVideo.getId());
                newVideo.setCreateTime(sysVideo.getCreateTime());
                newVideo.setYear(sysVideo.getYear());
                if(newVideo.getVideoName() == null || newVideo.getVideoName().equals("")){
                    newVideo.setVideoName(sysVideo.getVideoName());
                }
                if(uploadFile == null){
                    newVideo.setVideoUrl(sysVideo.getVideoUrl());
                }
                if(newVideo.getVideoDetail() == null || newVideo.getVideoDetail().equals("")){
                    newVideo.setVideoDetail(sysVideo.getVideoDetail());
                    newVideo.setVideoImgUrl(sysVideo.getVideoImgUrl());
                }
                if(newVideo.getVideoGroup() == null || newVideo.getVideoGroup().equals("")){
                    newVideo.setVideoGroup(sysVideo.getVideoGroup());
                }
                if(newVideo.getCreateTime() == null || newVideo.getCreateTime().equals("")){
                    newVideo.setCreateTime(sysVideo.getCreateTime());
                    newVideo.setYear(sysVideo.getYear());
                }else{
                    newVideo.setYear(newVideo.getCreateTime().toString().substring(newVideo.getCreateTime().toString().length()-4));
                }

                videoService.UpdateVideoById(newVideo);
                return RespBean.ok("视频修改成功",newVideo);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("视频修改失败");
            }
        }else{
            //如果需要不需要修改上传文件内容
            newVideo.setId(sysVideo.getId());
            newVideo.setVideoImgUrl(sysVideo.getVideoImgUrl());
            newVideo.setUpdateTime(new Date());
            System.out.println("11");
            if(newVideo.getVideoName() == null || newVideo.getVideoName().equals("")){
                newVideo.setVideoName(sysVideo.getVideoName());
            }
            System.out.println("11");
            System.out.println("11");
            if(uploadFile == null){
                newVideo.setVideoUrl(sysVideo.getVideoUrl());
            }
            if(newVideo.getVideoDetail() == null || newVideo.getVideoDetail().equals("")){
                newVideo.setVideoDetail(sysVideo.getVideoDetail());
            }
            if(newVideo.getCreateTime() == null || newVideo.getCreateTime().equals("")){
                newVideo.setCreateTime(sysVideo.getCreateTime());
                newVideo.setYear(sysVideo.getYear());
            }else{
                newVideo.setYear(newVideo.getCreateTime().toString().substring(newVideo.getCreateTime().toString().length()-4));
            }
            System.out.println("11");
            try{
                videoService.UpdateVideoById(newVideo);
                return RespBean.ok("视频修改成功",newVideo);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("视频修改失败");
            }
        }
    }

    /**
     * 捕捉图片第一帧
     * @param videoFile,视频的绝对地址
     * @param imgRealPath,图片的绝对地址
     * @param request
     * @throws Exception
     */

    public void fetchFrame(String videoFile,String imgRealPath,String imgRealUrl, HttpServletRequest request)throws Exception{
//        //获取开始时间
//        long start = System.currentTimeMillis();
//        //解码器
//        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
//        //开始解码
//        ff.start();
//        //解码长度
//        int length = ff.getLengthInFrames();
//        //时间
//        int i = 0;
//        //
//        Frame f = null;
//        //
//        while(i < length){
//            //过滤前五帧，避免出现全黑的图片
//            f = ff.grabFrame();
//            //
//            if(i > 5 && (f.image != null)){
//                break;
//            }
//            //
//            i ++;
//        }
//        //获取照片
//        opencv_core.IplImage img = f.image;
//
//        //照片原始宽
//        int owidth = img.width();
//        //照片原始长
//        int oheight = img.height();
//        //对截取的帧进行等比例缩放
//        int width = 800;
//        //等比例高
//        int height = (int) (((double)width / owidth)*oheight);
        //照片缓存
//        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
//        //绘制图片
//        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width,height, Image.SCALE_SMOOTH),
//                0,0,null);
//        //照片保存文件夹
//        File targetFile = new File(imgRealPath);
//        //文件夹不存在就新建
//        if(!targetFile.isDirectory()){
//            targetFile.mkdirs();
//        }
//        //写入文件夹,以jpg图片格式
//        ImageIO.write(bi, "jpg", targetFile);
//        //停止转化为帧
//        ff.stop();
//        //转换帧需要的时间
//        System.out.println(System.currentTimeMillis() - start);


        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoFile);
        //frameGrabber.setFrameRate(100);
        //frameGrabber.setFormat("h264");
        //frameGrabber.setVideoBitrate(15);
        // frameGrabber.setVideoOption("preset", "ultrafast");
        // frameGrabber.setNumBuffers(25000000);

        Java2DFrameConverter converter = new Java2DFrameConverter();

        frameGrabber.start();
        //解码长度
        int length = frameGrabber.getLengthInFrames();
        //时间
        int i = 0;
        //
        Frame frame = null;
        //
        while(i < length){
            //过滤前五帧，避免出现全黑的图片
            frame = frameGrabber.grabFrame();
            //
            if(i > 10 && (frame.image != null)){
                break;
            }
            //
            i ++;
        }
        // Frame frame = frameGrabber.grabFrame();
        BufferedImage bufferedImage = converter.convert(frame);
        //照片保存文件夹
        File targetFile = new File(imgRealPath);
        //文件夹不存在就新建
        if(!targetFile.isDirectory()){
            targetFile.mkdirs();
        }
        //写入文件夹,以jpg图片格式
        ImageIO.write(bufferedImage, "jpg", targetFile);

        //停止转化为帧
        frameGrabber.stop();
    }

    /**
     *视频列表分页显示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "视频列表分页显示", notes = "视频列表分页显示")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum",value = "分页页码",required = true),
            @ApiImplicitParam(type = "query", name = "pageSize",value = "分页行数",required = true)
    })
    public RespBean findVideoByPaging(Integer pageNum, Integer pageSize){
        try{
            Page<SysVideo> videoPage = videoService.selectAllByPage(pageNum, pageSize);
            List<SysVideo> videoList = videoPage.getResult();
            for(SysVideo video : videoList){
                String articleIntro = HtmlSpirit.delHTMLTag(video.getVideoDetail());
                video.setVideoIntro((articleIntro.length() < 100)?articleIntro:articleIntro.substring(0,100));
            }
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageNum);
            pageResult.setPageSize(pageSize);
            pageResult.setTotalPages(videoPage.getPages());
            pageResult.setTotalSize(videoPage.getTotal());
            pageResult.setContent(videoList);
            return RespBean.ok("返回分页信息",pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("分页失败");
        }
    }
    /**
     * 单一视频内容显示
     * @param id
     * @return
     */
    @PostMapping("/details")
    @ApiOperation(value = "单一视频内容显示", notes = "单一视频内容显示")
    @ApiImplicitParam(type = "query", name = "id",value = "视频序号",required = true)
    public RespBean findVideoById(Integer id){
        try{
            SysVideo sysVideo = videoService.findVideoById(id);
            String articleIntro = HtmlSpirit.delHTMLTag(sysVideo.getVideoDetail());
            sysVideo.setVideoIntro((articleIntro.length() < 100)?articleIntro:articleIntro.substring(0,100));
            return RespBean.ok("视频详情",sysVideo);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("解析失败");
        }
    }

    /**
     * 根据年份分页获取所有视频
     * @param pageNum
     * @param pageSize
     * @param year
     * @return
     */
    @ApiOperation(value = "根据年份分页获取所有视频", notes = "根据年份分页获取所有视品")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum",value = "分页页码",required = true),
            @ApiImplicitParam(type = "query", name = "pageSize",value = "分页行数",required = true),
            @ApiImplicitParam(type = "query",name = "year",value = "查询年份",required = true)
    })
    @PostMapping("/age")
    public RespBean findVideoByAge(Integer pageNum, Integer pageSize, String year){
        if(year == null || pageNum == null || pageSize == null){
            return RespBean.error("参数不全,请填写完整");
        }
        try{
            Page<SysVideo> sysVideo = videoService.findVideosByAge(pageNum,pageSize,year);
            List<SysVideo> videos = sysVideo.getResult();
            for(SysVideo video : videos){
                String articleIntro = HtmlSpirit.delHTMLTag(video.getVideoDetail());
                video.setVideoIntro((articleIntro.length() < 100)?articleIntro:articleIntro.substring(0,100));
            }
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageNum);
            pageResult.setPageSize(pageSize);
            pageResult.setContent(videos);
            pageResult.setTotalPages(sysVideo.getPages());
            pageResult.setTotalSize(sysVideo.getTotal());
            return RespBean.ok("根据年份分页获取视频列表",pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取视频列表失败");
        }
    }
    /**
     * 根据组别分页获取所有视频
     * @param pageNum
     * @param pageSize
     * @param group
     * @return
     */
    @ApiOperation(value = "根据组别分页获取所有视频", notes = "根据组别分页获取所有视品")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum",value = "分页页码",required = true),
            @ApiImplicitParam(type = "query", name = "pageSize",value = "分页行数",required = true),
            @ApiImplicitParam(type = "query",name = "group",value = "查询组别",required = true)
    })
    @PostMapping("/group")
    public RespBean findVideoByGroup(Integer pageNum, Integer pageSize, String group){
        if(group == null || pageNum == null || pageSize == null){
            return RespBean.error("参数不全,请填写完整");
        }
        try{
            Page<SysVideo> sysVideoPage = videoService.findVideoByGroup(pageNum,pageSize,group);
            List<SysVideo> videos = sysVideoPage.getResult();
            for(SysVideo sysVideo : videos){
                System.out.println("sysVideo.getVideoGroup"+sysVideo.getVideoGroup());
                SysGroup sysGroup = groupService.findGroupById(Integer.parseInt(sysVideo.getVideoGroup()));
                sysVideo.setVideoGroupZh(sysGroup.getGroupName());
                String articleIntro = HtmlSpirit.delHTMLTag(sysVideo.getVideoDetail());
                sysVideo.setVideoIntro((articleIntro.length() < 100)?articleIntro:articleIntro.substring(0,100));
            }
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageNum);
            pageResult.setPageSize(pageSize);
            pageResult.setTotalPages(sysVideoPage.getPages());
            pageResult.setTotalSize(sysVideoPage.getTotal());
            pageResult.setContent(videos);
            return RespBean.ok("分组获取视频成功",pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("分组获取视频失败");
        }
    }

    /**
     * 获取视频所属所有年份
     * @return
     */
    @ApiOperation(value = "获取视频所属的所有年份",notes = "获取视频所属逇所有年份")
    @PostMapping("/ages")
    public RespBean findAllVideoAges(){
        List<String> ages = videoService.findAllAges();
        return RespBean.ok("获取视频全部所属年份",ages);
    }


}
