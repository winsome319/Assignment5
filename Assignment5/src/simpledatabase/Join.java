package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple left = leftChild.next();
		Tuple right = rightChild.next();
		if (right == null) return null;
		while (left != null){
			newAttributeList = new ArrayList<Attribute>();
			for (Attribute x : left.attributeList){
				Attribute temp = new Attribute();
				temp.attributeName = x.attributeName;
				temp.attributeType = x.attributeType;
				temp.attributeValue = x.attributeValue;
				newAttributeList.add(temp);
			}
			tuples1.add(new Tuple(newAttributeList));
			left = leftChild.next();
		}
		
		for (int i = 0; i < rightChild.getAttributeList().size(); i++){
			for (int j = 0; j < tuples1.size(); j++){
				for (int k = 0; k < tuples1.get(0).getAttributeList().size(); k++){
					if (right.getAttributeName(i).equals(tuples1.get(j).getAttributeName(k)) && 
							right.getAttributeValue(i).equals(tuples1.get(j).getAttributeValue(k))) {
						newAttributeList = new ArrayList<Attribute>();
						for (Attribute x : tuples1.get(j).attributeList){
							if (!right.getAttributeName(i).equals(x.getAttributeName()))
								newAttributeList.add(x);
						}
						for (Attribute y : right.attributeList){
							Attribute z = new Attribute();
							z.attributeName = y.attributeName;
							z.attributeType = y.attributeType;
							z.attributeValue = y.attributeValue;
							newAttributeList.add(z);
						}
						return new Tuple(newAttributeList);
					}
				}
			}
		}
		
		return null;
		
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}