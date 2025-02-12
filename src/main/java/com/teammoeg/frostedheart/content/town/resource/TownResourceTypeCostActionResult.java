package com.teammoeg.frostedheart.content.town.resource;

import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

/**
 *
 * @param action 对应的TownResourceTypeCostAction
 * @param allCosted 应消耗量是否等于消耗量
 * @param totalModifiedAmount 实际消耗总量
 * @param residualAmount 应消耗但未消耗量
 * @param details 具体每个等级的TownResourceKey的消耗情况。若消耗的是物品，在这些result里还可以找到具体消耗的物品数量
 */
public record TownResourceTypeCostActionResult(TownResourceTypeCostAction action, boolean allCosted, double totalModifiedAmount, double residualAmount, List<ITownResourceKeyActionResult> details) implements IResourceActionResult {

    @Override
    public IResourceAction getAction() {
        return action;
    }

    @Override
    public void applyForce(TownResourceHolder resourceHolder) {
        details.forEach(detailResult -> detailResult.applyForce(resourceHolder));
    }

    public double getMinLevel(){
        return details.stream()
                .map(ITownResourceKeyActionResult::getLevel)
                .min(Double::compare)
                .orElse(0);
    }

    /**
     * @return 消耗资源的平均等级，按消耗量加权平均。
     */
    public double getAverageLevel(){
        DoubleAdder levelAdder = new DoubleAdder();
        details.forEach(result -> levelAdder.add(result.getLevel() * result.totalModifiedAmount()));
        return levelAdder.doubleValue() / totalModifiedAmount;
    }
}
