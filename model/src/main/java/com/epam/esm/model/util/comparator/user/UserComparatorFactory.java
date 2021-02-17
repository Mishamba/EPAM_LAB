package com.epam.esm.model.util.comparator.user;

import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.constant.UserSortParametersConstant;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.util.comparator.user.impl.UserIdComparator;
import com.epam.esm.model.util.comparator.user.impl.UserNameComparator;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserComparatorFactory {
    private final Map<String, Map<String, Comparator<User>>> userComparator;
    private final Map<String, Comparator<User>> idUserComparator;
    private final Map<String, Comparator<User>> nameUserComparator;

    public UserComparatorFactory() {
        this.userComparator = new HashMap<>();
        this.idUserComparator = new HashMap<>();
        this.nameUserComparator = new HashMap<>();
        userComparator.put(UserSortParametersConstant.SORT_BY_ID, idUserComparator);
        userComparator.put(UserSortParametersConstant.SORT_BY_NAME, nameUserComparator);
        idUserComparator.put(SortOrderConstant.ASC_SORT_TYPE, new UserIdComparator(SortOrderConstant.ASC_SORT_TYPE));
        idUserComparator.put(SortOrderConstant.DESC_SORT_TYPE, new UserIdComparator(SortOrderConstant.DESC_SORT_TYPE));
        nameUserComparator.put(SortOrderConstant.ASC_SORT_TYPE,
                new UserNameComparator(SortOrderConstant.ASC_SORT_TYPE));
        nameUserComparator.put(SortOrderConstant.DESC_SORT_TYPE,
                new UserNameComparator(SortOrderConstant.DESC_SORT_TYPE));
    }

    public Comparator<User> getComparator(PaginationData paginationData) {
        return userComparator.get(paginationData.getSortBy()).get(paginationData.getSortType());
    }
}
