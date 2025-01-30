package com.teammoeg.frostedheart.content.town.resource.action;

import com.teammoeg.frostedheart.content.town.resource.TownResourceManager;

public interface IResourceAction {
    /**
     * 在传入的TownResourceManager上执行该实例所表示的操作
     * @param resourceManager 执行操作所在的TownResourceManager
     * @return 该操作的结果
     */
    IResourceActionResult apply(TownResourceManager resourceManager);
}
