package com.epam.esm.dao.queue;

/**
 * SQL tag query repository. Stores sql tag queries.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.entity.Tag
 */
public class TagQueryRepository {

    /**
     * Query selects all tags data.
     */
    public static final String ALL_TAGS_QUEUE = "SELECT id, tag_name FROM tag";

    /**
     * Query selects tag by id.
     */
    public static final String TAG_BY_ID_QUEUE = "SELECT id, tag_name FROM tag WHERE id = ?";

    /**
     * Query saves tag info.
     */
    public static final String CREATE_TAG_QUEUE = "INSERT INTO tag (tag_name) VALUE (?)";

    /**
     * Query deletes tag from certificates tag references and deletes tag info.
     */
    public static final String DELETE_TAG_FROM_TAG_TABLE = "DELETE FROM certificate_tags WHERE id = ?;" +
            "DELETE FROM tag WHERE id = ?";
}
