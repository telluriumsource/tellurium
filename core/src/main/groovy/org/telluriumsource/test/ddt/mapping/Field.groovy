package org.telluriumsource.test.ddt.mapping
/**
 *  Describe a field for a record
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Field {
	//Field name
	private String name

    //Field type, default is String
    private String type = "String"

    //optional description of the Field
	private String description

	//If the value can be null, default is true
	private boolean nullable = true

	//optional null value if the value is null or not specified
	private String nullValue

	//If the length is not specified, it is -1
	private int length = -1

	//optional String pattern for the value
	//if specified, we must use it for String validation
	private String pattern
    
  // Java Style
  //Still useString setters and getters so that it is obvious to see if method is not defined in IDE
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNullValue() {
		return nullValue;
	}

	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}