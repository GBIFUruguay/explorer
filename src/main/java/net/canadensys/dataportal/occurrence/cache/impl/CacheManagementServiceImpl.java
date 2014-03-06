package net.canadensys.dataportal.occurrence.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.canadensys.dataportal.occurrence.cache.CacheManagementService;
import net.canadensys.dataportal.occurrence.search.OccurrenceSearchService;
import net.canadensys.dataportal.occurrence.search.OccurrenceSearchableField;
import net.canadensys.dataportal.occurrence.search.config.SearchServiceConfig;
import net.canadensys.dataportal.occurrence.search.config.SearchServiceConfig.SearchableFieldEnum;
import net.canadensys.dataportal.occurrence.search.config.SearchServiceConfig.SearchableFieldGroupEnum;
import net.canadensys.query.SearchQueryPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * CacheManagementService implementation.
 * @author cgendreau
 *
 */
@Service
public class CacheManagementServiceImpl implements CacheManagementService{
	
	@Autowired
	private OccurrenceSearchService occurrenceSearchService;
	
	//Configuration
	@Autowired
	@Qualifier("searchServiceConfig")
	private SearchServiceConfig searchServiceConfig;
	
	@Async
	public void preLoadCache(){
		//to avoid any issue with the cache, simply call the service directly.
		Map<String,List<SearchQueryPart>> emptySearchCriteria = new HashMap<String, List<SearchQueryPart>>();
		OccurrenceSearchableField osf = null;
		for(SearchableFieldEnum  currSf : SearchableFieldGroupEnum.CLASSIFICATION.getContent()){
			osf = searchServiceConfig.getSearchableField(currSf);
			occurrenceSearchService.getDistinctValuesCount(emptySearchCriteria, osf);
		}
		for(SearchableFieldEnum  currSf : SearchableFieldGroupEnum.LOCATION.getContent()){
			osf = searchServiceConfig.getSearchableField(currSf);
			occurrenceSearchService.getDistinctValuesCount(emptySearchCriteria, osf);
		}
	}
}
