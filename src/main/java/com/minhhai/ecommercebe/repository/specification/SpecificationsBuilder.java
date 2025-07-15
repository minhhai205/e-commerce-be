package com.minhhai.ecommercebe.repository.specification;

import com.minhhai.ecommercebe.util.enums.SearchOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import static com.minhhai.ecommercebe.util.enums.SearchOperation.*;

import java.util.ArrayList;
import java.util.List;

public final class SpecificationsBuilder {

    public final List<SpecSearchCriteria> params;

    public SpecificationsBuilder() {
        params = new ArrayList<>();
    }


    public SpecificationsBuilder with(final String key, final String operation, final String prefix,
                                      final Object value, final String suffix) {
        return with(null, key, operation, prefix, value, suffix);
    }

    public SpecificationsBuilder with(final String orPredicate, final String key, final String operation,
                                      final String prefix, final Object value, final String suffix) {

        SearchOperation searchOperation = SearchOperation.getSimpleOperation(operation.charAt(0));

        /*
         * Nếu cả startWithAsterisk và endWithAsterisk thì cho tìm kiếm theo LIKE
         * Cũng có thể truyền vào ~ để tìm kiếm theo LIKE
         */
        if (searchOperation != null) {
            if (searchOperation == EQUALITY) {
                final boolean startWithAsterisk = prefix != null && prefix.contains(ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    searchOperation = LIKE;
                } else if (startWithAsterisk) {
                    searchOperation = ENDS_WITH;
                } else if (endWithAsterisk) {
                    searchOperation = STARTS_WITH;
                }
            }

            // Xét nếu có cờ ' thì là tìm kiếm theo OR
            boolean orPre = StringUtils.hasLength(orPredicate) && orPredicate.equals(OR_PREDICATE_FLAG);

            params.add(new SpecSearchCriteria(orPre, key, searchOperation, value));
        }
        return this;
    }

    public <T> Specification<T> build() {
        if (params.isEmpty())
            return null;

        Specification<T> result = new CustomSpecification<>(params.getFirst());

        // Nếu isOrPredicate() là true thì xét điều kiện nối Specification là OR, không thì mặc định là AND.
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new CustomSpecification<>(params.get(i)))
                    : Specification.where(result).and(new CustomSpecification<>(params.get(i)));
        }

        return result;
    }

    /**
     * Add thêm vào List<SpecSearchCriteria> params
     */
    public SpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}