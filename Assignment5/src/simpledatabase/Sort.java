package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple temp = child.next();
		while (temp != null){
			tuplesResult.add(temp);
			temp = child.next();
		}
		if (tuplesResult.isEmpty())return null;
		int index = 0;
		while (!tuplesResult.get(0).getAttributeName(index).equals(orderPredicate)) index++; 
		
		int min = 0;
		int count = 0;
		while (count < tuplesResult.size()){
			if (tuplesResult.get(min).getAttributeValue(index).toString().compareTo(
					tuplesResult.get(count).getAttributeValue(index).toString()) > 0){
				min = count;
			}
			count++;
		}
		return tuplesResult.remove(min);
		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}