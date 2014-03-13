package com.the3.base.repository;

import java.util.Map.Entry;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.the3.base.web.SearchFilter;
import com.the3.base.web.SearchFilter.Operator;

/**
 * QueryUtils.java
 *
 * @author suncco
 * @time 2014年3月12日下午5:08:11
 */
public class QueryUtils {
	
	public static Query dynamicGenerateQuery(Set<Entry<String, SearchFilter>> searchFilterSet){
		Query query = new Query();
		for(Entry<String, SearchFilter> entry:searchFilterSet){
			SearchFilter searchFilter = entry.getValue();
			Operator operator = searchFilter.operator;
			switch(operator){
				case EQ:
					query.addCriteria(new Criteria(searchFilter.fieldName).is(searchFilter.value));
					break;
				case NEQ:
					query.addCriteria(new Criteria(searchFilter.fieldName).ne(searchFilter.value));
	                break;
				case LIKE:
					query.addCriteria(new Criteria(searchFilter.fieldName).regex(searchFilter.value.toString()));
					break;
				case GT:
					query.addCriteria(new Criteria(searchFilter.fieldName).gt(searchFilter.value));
					break;
				case LT:
					query.addCriteria(new Criteria(searchFilter.fieldName).lt(searchFilter.value));
					break;
				case GTE:
					query.addCriteria(new Criteria(searchFilter.fieldName).gte(searchFilter.value));
					break;
				case LTE:
					query.addCriteria(new Criteria(searchFilter.fieldName).lte(searchFilter.value));
					break;
			}
		}
		
		return query;
	}

}