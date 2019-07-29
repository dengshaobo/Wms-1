package com.hzx.wms.http;

import com.hzx.wms.bean.AppUpdate;
import com.hzx.wms.bean.BaseBean;
import com.hzx.wms.bean.BaseEntity;
import com.hzx.wms.bean.CheckBean;
import com.hzx.wms.bean.InterceptBean;
import com.hzx.wms.bean.ReviewBean;
import com.hzx.wms.bean.DifferentBean;
import com.hzx.wms.bean.LoginBean;
import com.hzx.wms.bean.RuKuBean;
import com.hzx.wms.bean.SearchBean;
import com.hzx.wms.bean.QueryBean;
import com.hzx.wms.bean.TaskListBean;
import com.hzx.wms.bean.WhoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * @author qinl
 * @package com.hzx.wms.utils
 * @date 2019/4/8 15:05
 * @fileName Api
 * @describe TODO
 */

public interface Api {
    /**
     * 用户通过接口登录
     *
     * @param name 用户名
     * @param pwd  密码
     * @return LoginBean
     */
    @FormUrlEncoded
    @POST("login")
    Observable<BaseBean<LoginBean>> login(@Field("username") String name, @Field("password") String pwd);

    /**
     * 用户通过接口查询当前用户的id
     *
     * @return id
     */
    @POST("who")
    Observable<BaseBean<WhoBean>> getId();

    /**
     * 获取预入库列表
     *
     * @param params 用户id
     * @return List<RuKuBean>>
     */
    @GET("warehouse/prepare-in-order/beforehand")
    Observable<BaseBean<List<RuKuBean>>> getRuKuList(@QueryMap Map<String, String> params);


    /**
     * 入库提交
     *
     * @param userId
     * @param requestBody
     * @return
     */
    @POST("warehouse/prepare-in-order/beforehand/put/{id}")
    Observable<BaseBean> updateRukuData(@Path("id") int userId, @Body RequestBody requestBody);

    /**
     * 复核列表
     *
     * @param params
     * @return
     */
    @GET("warehouse/task-list/check")
    Observable<BaseBean<List<TaskListBean>>> getCheckoutList(@QueryMap Map<String, String> params);

    /**
     * 任务列表
     *
     * @param params
     * @return
     */
    @GET("warehouse/task-list/next-beforehand")
    Observable<BaseBean<List<TaskListBean>>> getTaskList(@QueryMap Map<String, String> params);

    /**
     * 拣货任务分配
     *
     * @param requestBody
     * @return
     */
    @POST("warehouse/task-list/next-beforehand/distribution")
    Observable<BaseBean> distributionTask(@Body RequestBody requestBody);

    /**
     * 拣货列表
     *
     * @param params
     * @return
     */
    @GET("warehouse/task-list/assign")
    Observable<BaseBean<List<TaskListBean>>> getPickTask(@QueryMap Map<String, String> params);

    /**
     * 提交拣货数据
     *
     * @param prepareOutOrderId
     * @return
     */
    @PUT("warehouse/task-list/assign/{prepareOutOrderId}")
    Observable<BaseBean> postPick(@Path("prepareOutOrderId") int prepareOutOrderId);


    /**
     * 复核任务分配
     *
     * @param requestBody
     * @return
     */
    @POST("warehouse/task-list/assign/check")
    Observable<BaseBean> distributionCheckTask(@Body RequestBody requestBody);


    /**
     * 复核
     *
     * @return
     */
    @PUT("warehouse/task-list/check")
    Observable<BaseBean> check(@QueryMap Map<String, String> params);

    /**
     * 查询详情
     *
     * @return
     */
    @GET("warehouse/task-list/check/list")
    Observable<BaseBean<List<ReviewBean>>> query(@QueryMap Map<String, String> params);


    /**
     * @param id
     * @param params
     * @return
     */
    @GET("warehouse/prepare-in-order/prepare-in-ware/search/{id}")
    Observable<BaseBean<SearchBean>> search(@Path("id") int id, @QueryMap Map<String, String> params);


    /**
     * 差异
     *
     * @param id
     * @param params
     * @return
     */
    @GET("warehouse/prepare-in-order/prepare-in-ware/different/{id}")
    Observable<BaseBean<List<DifferentBean>>> getDifferent(@Path("id") int id, @QueryMap Map<String, String> params);

    /**
     * 提交包裹重量
     *
     * @param requestBody
     * @return
     */
    @PUT("warehouse/task-list/pack")
    Observable<BaseBean> putWeight(@Body RequestBody requestBody);


    @POST("/wms/version/get_version")
    Observable<BaseEntity<List<AppUpdate>>> getVersion();

    /**
     * 获取货位或条码详情
     *
     * @param params
     * @return
     */
    @GET("warehouse/stock/normal")
    Observable<BaseBean<List<QueryBean>>> getData(@QueryMap Map<String, String> params);

    /**
     * 获取盘点列表
     *
     * @param params
     * @return
     */
    @GET("warehouse/stock/check")
    Observable<BaseBean<List<CheckBean>>> getCheckData(@QueryMap Map<String, String> params);

    /**
     * 获取盘点列表详情
     *
     * @param params
     * @return
     */
    @GET("warehouse/stock/check")
    Observable<BaseBean> getCheckDetailsData(@Path("id") int id, @QueryMap Map<String, String> params);

    /**
     * 通过物流单号查询状态(拦截)
     *
     * @param params
     * @return
     */
    @GET("warehouse/prepare-out-order/intercept")
    Observable<BaseBean<List<InterceptBean>>> getIntercept(@QueryMap Map<String, String> params);
}
