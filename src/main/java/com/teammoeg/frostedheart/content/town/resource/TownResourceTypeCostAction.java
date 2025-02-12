package com.teammoeg.frostedheart.content.town.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public record TownResourceTypeCostAction(ITownResourceType resourceToCost, double amount, int minLevel, int maxLevel, ResourceActionMode actionMode, ResourceActionOrder order) implements IResourceAction{
    public TownResourceTypeCostAction{
        if(minLevel>maxLevel)
            throw new IllegalArgumentException("minLevel must be less than maxLevel");
        if(minLevel<0)
            throw new IllegalArgumentException("minLevel must be greater than 0");
        if(maxLevel>resourceToCost.getMaxLevel())
            throw new IllegalArgumentException("minLevel must be less than maxLevel");
    }

    @Override
    public IResourceActionResult apply(TownResourceHolder resourceHolder) {
        double availableAmount = resourceHolder.get(resourceToCost);
        double toCost = amount;
        if(amount > availableAmount){
            if(actionMode==ResourceActionMode.ATTEMPT || availableAmount<=TownResourceHolder.DELTA){
                return new TownResourceTypeCostActionResult(this, false, 0, amount, Collections.emptyList());
            } else if(actionMode==ResourceActionMode.MAXIMIZE){
                toCost = availableAmount;
            }
        }
        int startLevel;
        Predicate<Integer> levelLimit;
        int step;
        if (order == ResourceActionOrder.ASCENDING) {
            startLevel = minLevel;
            levelLimit = level -> level <= maxLevel;
            step = 1;
        } else {
            startLevel = maxLevel;
            levelLimit = level -> level >= minLevel;
            step = -1;
        }
        double toCostCopy = toCost;
        List<ITownResourceKeyActionResult> details = new ArrayList<>();
        for(int level = startLevel ; levelLimit.test(level) ; level += step){
            ITownResourceKeyAction action = ITownResourceKeyAction.create(resourceToCost.generateKey(level), toCost, ResourceActionMode.MAXIMIZE);
            ITownResourceKeyActionResult result = action.apply(resourceHolder);
            toCost = result.residualAmount();
            if(toCost<=TownResourceHolder.DELTA) break;
        }
        return new TownResourceTypeCostActionResult(this, amount <= availableAmount, toCostCopy, amount - toCostCopy, details);
    }
}
