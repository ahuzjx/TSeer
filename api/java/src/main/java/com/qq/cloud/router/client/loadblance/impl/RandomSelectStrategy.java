/**
 * Tencent is pleased to support the open source community by making Tseer available.
 *
 * Copyright (C) 2018 THL A29 Limited, a Tencent company. All rights reserved.
 * 
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.qq.cloud.router.client.loadblance.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.qq.cloud.router.client.Result;
import com.qq.cloud.router.client.RouterResponse;
import com.qq.cloud.router.client.ServerNode;
import com.qq.cloud.router.client.loadblance.LoadBlanceContext;
import com.qq.cloud.router.client.loadblance.LoadBlanceStrategy;
import com.qq.cloud.router.client.util.AssemblyRouteResponseUtil;

public class RandomSelectStrategy implements LoadBlanceStrategy {

	private static final Random seed = new Random(System.nanoTime());
	
	
	private static RandomSelectStrategy instance;
	
	private RandomSelectStrategy() {
	}
	
	public static RandomSelectStrategy getInstance() {
		if (instance == null) {
			synchronized (RandomSelectStrategy.class) {
				if (instance == null) {
					instance = new RandomSelectStrategy();
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result<RouterResponse> selectRouteNode(Collection<? extends ServerNode> nodes,
			LoadBlanceContext context) {
        ServerNode targetNodeInfo = null;
		if (nodes == null || nodes.isEmpty()){
			return AssemblyRouteResponseUtil.assemblyRouteResponse(-1, AssemblyRouteResponseUtil.NODE_NULL, null);
		}
		List<ServerNode> node_list;
	    if (!(nodes instanceof List)) {
		    node_list = new ArrayList<ServerNode>();
		    node_list.addAll(nodes);
	    } else {
	    	node_list = (List<ServerNode>)(nodes);
	    }
		int index = seed.nextInt(nodes.size());
        targetNodeInfo = node_list.get(index);
		return AssemblyRouteResponseUtil.assemblyRouteResponse(0, AssemblyRouteResponseUtil.CALL_SUCCESS, targetNodeInfo);
	}
}
