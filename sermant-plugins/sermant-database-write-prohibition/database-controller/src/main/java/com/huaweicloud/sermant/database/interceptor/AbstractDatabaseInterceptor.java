/*
 *  Copyright (C) 2024-2024 Huawei Technologies Co., Ltd. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.huaweicloud.sermant.database.interceptor;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;
import com.huaweicloud.sermant.database.entity.DatabaseInfo;
import com.huaweicloud.sermant.database.handler.DatabaseHandler;

/**
 * 数据库抽象interceptor
 *
 * @author daizhenyu
 * @since 2024-01-22
 **/
public abstract class AbstractDatabaseInterceptor extends AbstractInterceptor {
    /**
     * 数据库信息存储的key
     */
    protected static final String DATABASE_INFO = "databaseInfo";

    /**
     * 自定义数据库处理handle
     */
    protected DatabaseHandler handler;

    @Override
    public ExecuteContext before(ExecuteContext context) throws Exception {
        createAndCacheDatabaseInfo(context);
        if (handler != null) {
            handler.doBefore(context);
            return context;
        }
        return doBefore(context);
    }

    @Override
    public ExecuteContext after(ExecuteContext context) throws Exception {
        if (handler != null) {
            handler.doAfter(context);
            return context;
        }
        return context;
    }

    @Override
    public ExecuteContext onThrow(ExecuteContext context) throws Exception {
        if (handler != null) {
            handler.doOnThrow(context);
            return context;
        }
        return context;
    }

    /**
     * 方法执行前
     *
     * @param context 上下文
     * @return ExecuteContext 上下文
     */
    protected abstract ExecuteContext doBefore(ExecuteContext context);

    /**
     * 创建数据库实例对象并缓存在上下文的本地局部属性集中
     *
     * @param context 上下文
     */
    protected abstract void createAndCacheDatabaseInfo(ExecuteContext context);

    /**
     * 从context局部属性集中获取数据库实例信息
     *
     * @param context 上下文
     * @return DatabaseInfo 数据库实例信息
     */
    protected DatabaseInfo getDataBaseInfo(ExecuteContext context) {
        return (DatabaseInfo) context.getLocalFieldValue(DATABASE_INFO);
    }
}
