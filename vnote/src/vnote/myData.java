package vnote;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vnote")
public class myData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	// 储存文本内容
	@ElementCollection(targetClass=String.class)
	@CollectionTable(name="contents")
	@MapKeyColumn(name="keycol")
	@MapKeyClass(String.class)
	@Column(name="valuecol")
	private Map<String, String> contents = new HashMap<String, String>();
	
	
	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}

	// 储存正在编辑的文本的id
	@Transient
	private HashMap<String, Long> edited = new HashMap<String, Long>();
	public HashMap<String, Long> getEdited() {
		return edited;
	}

	public void setEdited(HashMap<String, Long> edited) {
		this.edited = edited;
	}

}

