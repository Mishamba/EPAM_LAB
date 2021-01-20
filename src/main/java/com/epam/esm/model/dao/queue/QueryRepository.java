package com.epam.esm.model.dao.queue;

public class QueryRepository {
    public static final String ALL_CERTIFICATES_QUEUE = "SELECT id, _name, _description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate";
    public static final String CERTIFICATE_TAGS_ID_QUEUE = "SELECT tag_id FROM tag_tags WHERE certificate_id = ?";
    public static final String CERTIFICATE_BY_ID_QUEUE = "SELECT id, _name, _description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate WHERE id = ?";
    public static final String CREATE_CERTIFICATE_QUEUE = "INSERT INTO gift_certificate " +
            "(_name, _description, price, duration, create_date, last_update_date) value (?,?,?,?,?,?)";
    public static final String CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE = "INSERT INTO certificate_tags " +
            "(certificate_id, tag_id) value (?,?)";
    public static final String DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE = "DELETE FROM certificate_tags " +
            "WHERE certificate_id = ?";
    public static final String DELETE_CERTIFICATE_BY_ID_QUEUE = "DELETE FROM gift_certificate WHERE id = ?";
    public static final String UPDATE_CERTIFICATE_BY_ID_QUEUE = "UPDATE gift_certificate SET _name = ? , _description = ? , " +
            "price = ? , duration = ? , last_update_date = ? WHERE id = ?";
}
