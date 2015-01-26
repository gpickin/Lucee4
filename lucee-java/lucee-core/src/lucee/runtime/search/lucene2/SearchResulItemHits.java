/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.search.lucene2;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;

import lucee.runtime.search.SearchException;
import lucee.runtime.search.SearchResulItem;
import lucee.runtime.search.lucene2.highlight.Highlight;

public class SearchResulItemHits implements SearchResulItem {

	
	
	private Hits hits;
	private int index;
	private Object highlighter;
	private Analyzer analyzer;
	private String id;
	private String categoryTree;
	private String category;
	private int maxNumFragments;
	private int maxLength;
	private Document doc;

	public SearchResulItemHits(Hits hits, int index, Object highlighter,Analyzer analyzer,
			String id, String categoryTree, String category,int maxNumFragments, int maxLength) {
		this.hits=hits;
		this.index=index;
		this.highlighter=highlighter;
		this.analyzer=analyzer;
		this.id=id;
		this.categoryTree=categoryTree;
		this.category=category;
		this.maxNumFragments=maxNumFragments;
		this.maxLength=maxLength;
	}

	@Override
	public String getAuthor() {
		return doc("author");
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getCategoryTree() {
		return categoryTree;
	}

	@Override
	public String getCustom1() {
		return doc("custom1");
	}

	@Override
	public String getCustom2() {
		return doc("custom2");
	}

	@Override
	public String getCustom3() {
		return doc("custom3");
	}

	@Override
	public String getCustom4() {
		return doc("custom4");
	}
    
    public String getCustom(int index) throws SearchException {
    	if(index==1) return doc("custom1");
    	if(index==2) return doc("custom2");
    	if(index==3) return doc("custom3");
    	if(index==4) return doc("custom4");
    	
        throw new SearchException("invalid index ["+index+"], valid index is [1,2,3,4]");
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getKey() {
		return doc("key");
	}

	@Override
	public String getMimeType() {
		return doc("mime-type");
	}

	public int getRecordsSearched() {
		// TODO Auto-generated method stub
		return 0;
	}
    

	@Override
	public float getScore() {
		try {
			return hits.score(index);
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public String getSize() {
		return doc("size");
	}

	@Override
	public String getSummary() {
		return doc("summary");
	}

	@Override
	public String getTitle() {
		return doc("title");
	}

	@Override
	public String getUrl() {
		return doc("url");
	}
	
	/** FUTURE add to interface
	 * @return the contextSummary
	 */
	public String getContextSummary() {
		String contextSummary="";
		if(maxNumFragments>0){
			contextSummary=Highlight.createContextSummary(highlighter,analyzer,doc("contents"),maxNumFragments,maxLength,"");
		}
		return contextSummary;
	}

	private String doc(String field) {
		if(doc==null){
			try {
				doc=hits.doc(index);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return doc.get(field);
	}
}
