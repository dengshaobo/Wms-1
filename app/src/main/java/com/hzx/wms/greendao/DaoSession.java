package com.hzx.wms.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hzx.wms.bean.RepeatBean;
import com.hzx.wms.bean.WeightBean;

import com.hzx.wms.greendao.RepeatBeanDao;
import com.hzx.wms.greendao.WeightBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig repeatBeanDaoConfig;
    private final DaoConfig weightBeanDaoConfig;

    private final RepeatBeanDao repeatBeanDao;
    private final WeightBeanDao weightBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        repeatBeanDaoConfig = daoConfigMap.get(RepeatBeanDao.class).clone();
        repeatBeanDaoConfig.initIdentityScope(type);

        weightBeanDaoConfig = daoConfigMap.get(WeightBeanDao.class).clone();
        weightBeanDaoConfig.initIdentityScope(type);

        repeatBeanDao = new RepeatBeanDao(repeatBeanDaoConfig, this);
        weightBeanDao = new WeightBeanDao(weightBeanDaoConfig, this);

        registerDao(RepeatBean.class, repeatBeanDao);
        registerDao(WeightBean.class, weightBeanDao);
    }
    
    public void clear() {
        repeatBeanDaoConfig.clearIdentityScope();
        weightBeanDaoConfig.clearIdentityScope();
    }

    public RepeatBeanDao getRepeatBeanDao() {
        return repeatBeanDao;
    }

    public WeightBeanDao getWeightBeanDao() {
        return weightBeanDao;
    }

}
