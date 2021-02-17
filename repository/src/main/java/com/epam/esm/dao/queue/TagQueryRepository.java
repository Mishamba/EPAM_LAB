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
    public static final String ALL_TAGS_QUERY = "SELECT id, tag_name FROM tag";

    /**
     * Query selects tag by id.
     */
    public static final String TAG_BY_ID_QUERY = "SELECT id, tag_name FROM tag WHERE id = ?";

    /**
     * Query saves tag info.
     */
    public static final String CREATE_TAG_QUERY = "INSERT INTO tag (tag_name) VALUE (?)";

    /**
     * Query deletes tag from certificates tag references.
     */
    public static final String DELETE_TAG_FROM_CERTIFICATE_TAG_TABLE = "DELETE FROM certificate_tags WHERE tag_id = ?";

    /**
     * Query deleted tag from tag table.
     */
    public static final String DELETE_TAG_FROM_TAG_TABLE = "DELETE FROM tag WHERE id = ?";

    /**
     * Query selects tag by name.
     */
    public static final String TAG_BY_NAME_QUERY = "SELECT id, tag_name FROM tag WHERE name = ?";
}
