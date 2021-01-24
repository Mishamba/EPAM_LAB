package com.epam.esm.dao.queue;

/**
 * SQL certificate query repository. Stores sql certificate queries.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.entity.Certificate
 */
public class CertificateQueryRepository {

    /**
     * Query selects all certificates data without tags data.
     */
    public static final String ALL_CERTIFICATES_QUEUE = "SELECT id, certificate_name, certificate_description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate";

    /**
     * Query selects certificate tags id using certificate id.
     */
    public static final String CERTIFICATE_TAGS_ID_QUEUE = "SELECT tag_id FROM certificate_tags WHERE certificate_id = ?";

    /**
     * Query selects certificate by id.
     */
    public static final String CERTIFICATE_BY_ID_QUEUE = "SELECT id, certificate_name, certificate_description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate WHERE id = ?";

    /**
     * Query saves certificate data.
     */
    public static final String CREATE_CERTIFICATE_QUEUE = "INSERT INTO gift_certificate " +
            "(certificate_name, certificate_description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Query saves certificate tag references.
     */
    public static final String CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE = "INSERT INTO certificate_tags " +
            "(certificate_id, tag_id) values (?, ?)";

    /**
     * Query deletes certificate tag references data using id.
     */
    public static final String DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE = "DELETE FROM certificate_tags " +
            "WHERE certificate_id = ?";

    /**
     * Query deletes certificate data using id.
     */
    public static final String DELETE_CERTIFICATE_BY_ID_QUEUE = "DELETE FROM gift_certificate WHERE id = ?";

    /**
     * Query updates certificate data.
     */
    public static final String UPDATE_CERTIFICATE_BY_ID_QUEUE = "UPDATE gift_certificate SET certificate_name = ? , certificate_description = ? , " +
            "price = ? , duration = ? , last_update_date = ? WHERE id = ?";
}
