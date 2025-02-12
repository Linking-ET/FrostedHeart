package com.teammoeg.frostedheart.content.town.resource;

/**
 * 历史遗留类，仅用于TownResourceManager中的部分方法，不推荐使用。
 * 若需要目前没有的ResourceResult，请新建一个类，并实现{@link IResourceActionResult}
 * @param allSuccess actually added/costed == amount to add/cost
 * @param actualAmount actually added/costed
 */
public record SimpleResourceActionResult(boolean allSuccess, double actualAmount) implements IResourceActionResult{
    public static final SimpleResourceActionResult NOT_SUCCESS = new SimpleResourceActionResult(false, 0);

    @Deprecated
    public SimpleResourceActionResult(boolean allSuccess, double actualAmount){
        if(actualAmount<0){
            this.allSuccess = false;
            this.actualAmount = 0;
        }
        else {
            this.allSuccess = allSuccess;
            this.actualAmount = actualAmount;
        }
    }

    /**
     * 不要使用这个方法，这个方法的存在仅为了兼容IResourceActionResult
     */
    @Override
    @Deprecated
    public IResourceAction getAction() {
        return null;
    }

    @Override
    public void applyForce(TownResourceHolder holder) {
    }
}
