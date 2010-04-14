package org.telluriumsource.test.ddt.mapping
/**
 *
 * The field set includes multiple fields and it usually is a record
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Jul 23, 2008
 *
 */
class FieldSet {

	private String name
    
	private String description

    private boolean hasIdentifier

    private boolean hasAction
    
    private LinkedList<Field> fields = new LinkedList<Field>()

    public int getFieldSize(){
        return fields.size()
    }
    
    public void addField(Field df){
        fields.addLast(df)
    }

    public LinkedList<Field> getFields() {
		return fields;
	}

    public void setFields(LinkedList<Field> fields) {
		this.fields = fields;
	}

    public boolean isHasAction(){
        return this.hasAction
    }

    public void setHasAction(boolean hasAction){
        this.hasAction = hasAction    
    }

    public boolean isHasIdentifier(){
        return this.hasIdentifier
    }

    public void setHasIdentifier(boolean hasIdentifier){
        this.hasIdentifier = hasIdentifier
    }

    public IdentifierField getIdentifierField(){
        
        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof IdentifierField){
                    return f
                }
            }
        }
        
        return null
    }

    public TestField getActionField(){
        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof TestField){
                    return f
                }
            }
        }

        return null
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public void checkFields(){

        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof IdentifierField){
                    this.hasIdentifier = true
                }
                if(f instanceof TestField){
                    this.hasAction = true
                }
            }
        }
    }

    //we need to use field set identifier + field set action to find the actual field set so that
    //we can handle more complicated situations.
    //Use case 1: we do not have identifier and action defined for this field set
    //            then we can only handle one Field Set because there is no way to differentiate different
    //            field sets
    //Use case 2: we specify identifier, but not the action
    //            in this case, we can use the identifier to determine the data from the stream belong
    //            to which field set. This also implies that multiple actions can use the same field set as input
    //Use case 3: we specify the action, but not the identifier
    //            in this scenario, we can use the action to determin the data from the stream belong
    //            to which field set.
    //Use case 4: we specify identifier and action
    //            in such situation, we can use them together to determine the data from the stream belong
    //            to which field set. This may imply that you can have different field sets, i.e., different input
    //            data format for the same action. Really make sense??, seems not.
    //
    //As a result, we only need to use the one of them if defined. As a rule, in the data file, user should put
    //the field to identify the fieldset as the first column, no matter it is an identifier or an action. We only
    //need to look at the data in the first column to decide which field set it belongs to.
    //
    //In special case, use will not define any identifier or action as shown in use case 1, there must be ONLY
    //one fieldset defined in the FieldSetRegistry. There is no need to do mapping and make decision here.


    //This is how the internal name comes
    public getInternalName(){
        String intername = ""
        if(!fields.isEmpty()){
            for(Field f : fields){
                //For identifier Field and Action Field, which one defined first will win
                if(f instanceof IdentifierField){
                    return f.getValue()
                }
                if(f instanceof TestField){
                    return f.getValue()
                }
            }
        }
        
        return  intername
    }

}