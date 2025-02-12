package com.teammoeg.frostedheart.content.town.resource;

public interface TownResourceUpdateListener {
    /**
     * 当资源发生变化时，调用此方法，用于更新客户端资源
     * @param result 资源发生的变化结果
     */
    void update(IResourceActionResult result);

    /**
     * 将完整的资源信息同步到客户端
     * @param holder 包含完整资源信息的TownResourceHolder
     */
    void fullUpdate(TownResourceHolder holder);
}
