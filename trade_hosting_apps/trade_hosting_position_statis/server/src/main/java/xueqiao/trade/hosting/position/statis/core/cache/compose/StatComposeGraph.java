package xueqiao.trade.hosting.position.statis.core.cache.compose;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class StatComposeGraph {
    private String targetKey;
    private String formular;
    private Set<String> legKeySet;
    private Map<Long, StatComposeLeg> composeLegQuantityMap;

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getFormular() {
        return formular;
    }

    public void setFormular(String formular) {
        this.formular = formular;
    }

    public Set<String> getLegKeySet() {
        return legKeySet;
    }

    public void setLegKeySet(Set<String> legKeySet) {
        this.legKeySet = legKeySet;
    }

    public Map<Long, StatComposeLeg> getComposeLegQuantityMap() {
        return composeLegQuantityMap;
    }

    public void setComposeLegQuantityMap(Map<Long, StatComposeLeg> composeLegQuantityMap) {
        this.composeLegQuantityMap = composeLegQuantityMap;
    }

    public Collection<StatComposeLeg> getStatComposeLegCollection() {
        return composeLegQuantityMap.values();
    }

    public Set<Long> getComposeSledContractIdSet() {
        return composeLegQuantityMap.keySet();
    }
}
