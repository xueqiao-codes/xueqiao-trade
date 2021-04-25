package xueqiao.trade.hosting.storage.compose;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;

/**
 *  用于计算组合图的相关参数
 *
 */
public class HostingComposeGraphCalculator {
	private static final int SPEC_VALUE_COUNT = 8;
	private static final int CONSTANTS[][] = {
			{1, 2, 3, 4, 5, 6, 7, 8},
			{1, 4, 9, 16, 25, 36, 49, 64},
			{1, 8, 27, 64, 125, 216, 343, 512},
			{1, 16, 81, 256, 625, 1296, 2401, 4096},
			{1, 32, 243, 1024, 3125, 7776, 16807, 32768},
			{1, 64, 729, 4096, 15625, 46656, 117649, 262144},
			{1, 128, 2187, 16384, 78125, 279936, 823543, 2097152},
			{1, 256, 6561, 65536, 390625, 1679616, 5764801, 16777216},
	};
	
	private HostingComposeGraph mCalGraph;
	private Expression mFormularExp;
	
	private List<Long> mOutputLegSledContractIds = new ArrayList<Long>();
	private List<Integer> mOutputLegTradeRatios = new ArrayList<Integer>();
	private List<Double> mOuputSpecValues = new ArrayList<Double>();
	private Map<Long, String> mOutputSledContractId2LegVar = new TreeMap<Long, String>();
	
	public HostingComposeGraphCalculator(HostingComposeGraph calGraph) {
		Preconditions.checkNotNull(calGraph);
		Preconditions.checkArgument(StringUtils.isNotEmpty(calGraph.getFormular()));
		Preconditions.checkArgument(!calGraph.getLegs().isEmpty());
		
		mCalGraph = calGraph;
		mFormularExp = new ExpressionBuilder(calGraph.getFormular()).variables(mCalGraph.getLegs().keySet()).build();
	}
	
	public void calculate() {
		for (Entry<String, HostingComposeLeg> legEntry : mCalGraph.getLegs().entrySet()) {
			Preconditions.checkArgument(legEntry.getKey().equals(legEntry.getValue().getVariableName()));
			mOutputSledContractId2LegVar.put(legEntry.getValue().getSledContractId(), legEntry.getKey());
		}
		
		for (Entry<Long, String> sledContractId2LegVar : mOutputSledContractId2LegVar.entrySet()) {
			HostingComposeLeg composeLeg =  mCalGraph.getLegs().get(sledContractId2LegVar.getValue());
			Preconditions.checkNotNull(composeLeg);
			
			Preconditions.checkArgument(composeLeg.getQuantity() >= 0);
			if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
				mOutputLegTradeRatios.add(composeLeg.getQuantity());
			} else {
				mOutputLegTradeRatios.add(-composeLeg.getQuantity());
			}
			
			mOutputLegSledContractIds.add(sledContractId2LegVar.getKey());
		}
		
		for (int specIndex = 0; specIndex < SPEC_VALUE_COUNT; ++specIndex) {
			int legIndex = 0;
			for (Entry<Long, String> sledContractId2LegVarEntry : mOutputSledContractId2LegVar.entrySet()) {
				mFormularExp.setVariable(sledContractId2LegVarEntry.getValue(), CONSTANTS[specIndex][legIndex]);
				++legIndex;
			}
			
			mOuputSpecValues.add(mFormularExp.evaluate());
		}
	}
	
	public List<Long> getLegSledContractIds() {
		return mOutputLegSledContractIds;
	}
	
	public List<Integer> getLegTradeRatios() {
		return mOutputLegTradeRatios;
	}
	
	public List<Double> getSepcValues() {
		return mOuputSpecValues;
	}
	
	public Map<Long, String> getSledContractId2LegVarMap() {
		return mOutputSledContractId2LegVar;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(128);
		builder.append("HostingComposeGraphCalculator{");
		builder.append("mOutputLegSledContractIds=[");
		for (int index = 0; index < mOutputLegSledContractIds.size(); ++index) {
			if (index > 0) {
				builder.append(",");
			}
			builder.append(mOutputLegSledContractIds.get(index));
		}
		builder.append("], mOutputLegTradeRatios=[");
		for (int index = 0; index < mOutputLegTradeRatios.size(); ++index) {
			if (index > 0) {
				builder.append(",");
			}
			builder.append(mOutputLegTradeRatios.get(index));
		}
		builder.append("], mOuputSpecValues=[");
		for (int index = 0; index < mOuputSpecValues.size(); ++index) {
			if (index > 0) {
				builder.append(",");
			}
			builder.append(mOuputSpecValues.get(index));
		}
		builder.append("], mOutputSledContractId2LegVar={");
		for (Entry<Long, String> e : mOutputSledContractId2LegVar.entrySet()) {
			builder.append(e.getKey()).append(":").append(e.getValue()).append(",");
		}
		builder.append("}");
		return builder.toString();
	}
	
	
//	public static void main(String[] args) throws ErrorInfo {
//		HostingComposeGraph testGraph = new HostingComposeGraph();
//		testGraph.setFormular("A-B");
//		
//		HostingComposeLeg composeLegA = new HostingComposeLeg();
//		composeLegA.setLegTradeDirection(HostingComposeLegTradeDirection.COMPOSE_LEG_BUY);
//		composeLegA.setQuantity(1);
//		composeLegA.setSledContractId(2);
//		composeLegA.setVariableName("A");
//		
//		HostingComposeLeg composeLegB = new HostingComposeLeg();
//		composeLegB.setLegTradeDirection(HostingComposeLegTradeDirection.COMPOSE_LEG_SELL);
//		composeLegB.setQuantity(1);
//		composeLegB.setSledContractId(1);
//		composeLegB.setVariableName("B");
//		
//		Map<String, HostingComposeLeg> legs = new HashMap<String, HostingComposeLeg>();
//		legs.put("A", composeLegA);
//		legs.put("B", composeLegB);
//		
//		testGraph.setLegs(legs);
//		
//		HostingComposeGraphCalculator calculator = new HostingComposeGraphCalculator(testGraph);
//		calculator.calculate();
//		
//		System.out.println(calculator);
//	}
}
