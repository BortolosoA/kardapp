-- Row locking serializes new purchases with concurrent product archival.
-- Historical order items remain readable, even when their product is archived.
CREATE FUNCTION check_order_item_product_available()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        IF NEW.menu_item_id IS NOT DISTINCT FROM OLD.menu_item_id THEN
            RETURN NEW;
        END IF;
    END IF;

    PERFORM 1
    FROM menu_items
    WHERE id = NEW.menu_item_id
      AND NOT archived
      AND is_available
    FOR SHARE;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Produto inexistente'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER order_items_check_product_available
BEFORE INSERT OR UPDATE OF menu_item_id ON order_items
FOR EACH ROW EXECUTE FUNCTION check_order_item_product_available();
