package com.minhhai.ecommercebe.repository.specification;

import com.minhhai.ecommercebe.util.enums.SearchOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SpecSearchCriteria(final boolean orPredicate, final String key, final SearchOperation operation,
                              final Object value) {
        super();
        this.orPredicate = orPredicate;
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}